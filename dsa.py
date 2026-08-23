#!/usr/bin/env python3
"""
dsa.py -- local DSA practice engine.

No dependencies. Run it as `dsa <command>` from any directory (the launcher in
~/.local/bin points here; set DSA_HOME if this folder ever moves).

Deterministic commands -- picking, tracking, testing, scheduling. No AI:

    dsa                     progress overview (same as `dsa status`)
    dsa init                rebuild the tracker from the cheat sheet
    dsa next [pattern]      next unsolved problem, in pattern order
    dsa random [pattern]    random UNSOLVED problem
    dsa random -p --both    ...or any problem, weighted by weakness
    dsa review [--start]    everything due today
    dsa open <id>           open a problem in VS Code
    dsa test [id]           compile + run the local judge
    dsa done [id] -c 4      record a solved attempt (confidence 1-5)
    dsa fail [id]           record a failed attempt
    dsa cold [id]           archive your solution, reset for a blind re-solve
    dsa list [pattern]      list problems (--unsolved, --missing)
    dsa dashboard           write "DSA Dashboard.md" for Obsidian
    dsa where | dsa code    print the folder / open it in VS Code

These launch Claude Code in the DSA folder, so the /dsa-* project commands
resolve wherever you invoked from:

    dsa nudge               one escalating interviewer hint
    dsa teach               full first-principles walkthrough
    dsa critique            post-solve interviewer review
    dsa scaffold <pattern>  build local judges
    dsa variant             twist a solved problem into a new one
    dsa drill <pattern>     invent a brand-new problem in a pattern
"""
from __future__ import annotations

import argparse
import json
import random
import re
import shutil
import subprocess
import sys
from datetime import date, datetime, timedelta
from pathlib import Path

ROOT = Path(__file__).resolve().parent
SHEET = ROOT / "DSA Patterns Cheat Sheet.md"
TRACKER = ROOT / "tracker.json"
PROBLEMS = ROOT / "problems"
LIB = ROOT / "lib"
BUILD = ROOT / ".build"
ARCHIVE = ROOT / ".attempts"
DASHBOARD = ROOT / "DSA Dashboard.md"

# --- pattern names in the sheet are inconsistent; canonicalise them ----------
PATTERN_CANON = {
    "two pointer": "Two Pointers",
    "two pointers": "Two Pointers",
    "fast & slow pointers": "Fast & Slow Pointers",
    "sliding window": "Sliding Window",
    "kadane pattern": "Kadane",
    "prefix sum": "Prefix Sum",
    "merge intervals": "Merge Intervals",
    "in-place reversal of a linkedlist": "LinkedList Reversal",
    "stack": "Stack",
    "hash maps": "Hash Maps",
    "binary search": "Binary Search",
    "heap pattern": "Heap",
    "recursion and backtracking": "Backtracking",
    "tree pattern": "Trees",
    "graphs": "Graphs",
    "dp (dynamic programming)": "DP",
}

# confidence 1..5 -> first review interval in days
BASE_INTERVAL = {1: 1, 2: 3, 3: 7, 4: 16, 5: 35}
GROWTH = 1.8
MAX_INTERVAL = 180

C = {
    "reset": "\033[0m", "bold": "\033[1m", "dim": "\033[2m",
    "red": "\033[31m", "green": "\033[32m", "yellow": "\033[33m",
    "blue": "\033[36m", "mag": "\033[35m",
}
if sys.platform == "win32":
    try:
        import ctypes
        ctypes.windll.kernel32.SetConsoleMode(
            ctypes.windll.kernel32.GetStdHandle(-11), 7)
    except Exception:
        C = {k: "" for k in C}


def col(s, c):
    return f"{C.get(c, '')}{s}{C['reset']}" if C.get(c) else str(s)


# ---------------------------------------------------------------- sheet parsing
CELL_SPLIT = re.compile(r"(?<!\\)\|")
WIKILINK = re.compile(r"\[\[([^\]|]+?)(?:\\?\|([^\]]+?))?\]\]")


def strip_wikilinks(text):
    return WIKILINK.sub(lambda m: (m.group(2) or m.group(1)).strip(), text)


def slugify(text):
    return re.sub(r"[^a-z0-9]+", "-", text.lower()).strip("-")


TITLE_NOISE = [
    re.compile(r"^\s*\[[^\]]+\]\s*"),                    # [Traversal]
    re.compile(r"^\s*problem\s+challenge\s*\d*\s*:?\s*", re.I),
    re.compile(r"^\s*episode\s+\d+\s*:?\s*", re.I),
    re.compile(r"\s*\((easy|medium|hard)\)\s*$", re.I),
    re.compile(r"\s*\(problem\s+challenge\)\s*$", re.I),
]


def clean_title_for_slug(title):
    s = title
    for _ in range(4):
        for rx in TITLE_NOISE:
            s = rx.sub(" ", s)
    return re.sub(r"\s+", " ", s).strip() or title


def difficulty_of(title):
    m = re.search(r"\((easy|medium|hard)\)", title, re.I)
    return m.group(1).lower() if m else "unknown"


def parse_sheet():
    if not SHEET.exists():
        die("cheat sheet not found: " + str(SHEET))
    rows, order, seen = [], 0, set()
    for line in SHEET.read_text(encoding="utf-8").splitlines():
        line = line.strip()
        if not line.startswith("|"):
            continue
        cells = [c.strip() for c in CELL_SPLIT.split(line)[1:-1]]
        if len(cells) < 3:
            continue
        pattern_raw = strip_wikilinks(cells[0]).strip()
        if pattern_raw.lower() in ("pattern", "") or set(pattern_raw) <= set("-: "):
            continue
        pattern = PATTERN_CANON.get(pattern_raw.lower(), pattern_raw)
        title = strip_wikilinks(cells[1]).replace("\\|", "|").strip()
        if not title:
            continue
        links = [u.strip().rstrip(",")
                 for u in re.split(r"[,\s]+", cells[2]) if u.startswith("http")]
        comment = strip_wikilinks(cells[3]).strip() if len(cells) > 3 else ""

        pslug = slugify(pattern)
        slug = slugify(clean_title_for_slug(title))
        pid, n = pslug + "/" + slug, 2
        while pid in seen:                       # duplicated problems in the sheet
            pid, n = "%s/%s-%d" % (pslug, slug, n), n + 1
        seen.add(pid)
        order += 1
        rows.append({
            "id": pid, "pattern": pattern, "pattern_slug": pslug,
            "slug": pid.split("/", 1)[1], "title": title,
            "difficulty": difficulty_of(title), "links": links,
            "comment": comment, "order": order,
        })
    return rows


# -------------------------------------------------------------------- tracker
def blank_entry(row):
    e = dict(row)
    e.update({
        "status": "pending",          # pending | attempted | solved
        "attempts": [],
        "confidence": 0,
        "solved_count": 0,
        "fail_count": 0,
        "last_practiced": None,
        "next_review": None,
        "nudge_level": 0,
        "variants": [],
    })
    return e


def load():
    if not TRACKER.exists():
        die("no tracker.json -- run: dsa init")
    return json.loads(TRACKER.read_text(encoding="utf-8"))


def save(t):
    t["updated"] = date.today().isoformat()
    TRACKER.write_text(json.dumps(t, indent=2, ensure_ascii=False), encoding="utf-8")


def die(msg, code=1):
    print(col("error: ", "red") + msg)
    sys.exit(code)


def cmd_init(args):
    rows = parse_sheet()
    if TRACKER.exists():
        t = json.loads(TRACKER.read_text(encoding="utf-8"))
        old = {p["id"]: p for p in t["problems"]}
    else:
        t, old = {"version": 1, "current": None}, {}
    merged, added = [], 0
    live = {r["id"] for r in rows}
    for row in rows:
        if row["id"] in old:
            e = old[row["id"]]
            e.update({k: row[k] for k in ("pattern", "pattern_slug", "title",
                                          "difficulty", "links", "comment",
                                          "order")})
            merged.append(e)
        else:
            merged.append(blank_entry(row))
            added += 1
    dropped = [i for i in old if i not in live]
    t["problems"] = merged
    t.setdefault("current", None)
    save(t)
    print("%s  %d problems across %d patterns (%s new)" % (
        col("tracker", "bold"), len(merged),
        len({p["pattern"] for p in merged}), col("+" + str(added), "green")))
    if dropped:
        print(col("  note: %d tracked problems no longer in the sheet (kept)"
                  % len(dropped), "dim"))


# ------------------------------------------------------------------- helpers
def by_id(t, pid):
    for p in t["problems"]:
        if p["id"] == pid:
            return p
    return None


def resolve(t, token):
    """Accept a full id, a slug, a fuzzy title, or None -> current problem."""
    if not token:
        cur = t.get("current")
        if not cur:
            die("no current problem -- run: dsa next")
        p = by_id(t, cur)
        if not p:
            die("current problem %s missing from tracker" % cur)
        return p
    token = token.strip().strip("/")
    p = by_id(t, token)
    if p:
        return p
    hits = [q for q in t["problems"] if q["slug"] == token]
    if len(hits) == 1:
        return hits[0]
    needle = slugify(token)
    hits = [q for q in t["problems"]
            if needle and (needle in slugify(q["title"]) or needle in q["slug"])]
    if len(hits) == 1:
        return hits[0]
    if not hits:
        die("no problem matches %r" % token)
    print(col("ambiguous -- did you mean:", "yellow"))
    for q in hits[:12]:
        print("  %s   %s" % (q["id"], col(q["title"], "dim")))
    sys.exit(1)


def match_pattern(t, token):
    if not token:
        return None
    want = slugify(token)
    slugs = {p["pattern_slug"] for p in t["problems"]}
    if want in slugs:
        return want
    hits = sorted(s for s in slugs if want in s)
    if len(hits) == 1:
        return hits[0]
    if not hits:
        die("no pattern matches %r. known: %s" % (token, ", ".join(sorted(slugs))))
    die("ambiguous pattern %r: %s" % (token, ", ".join(hits)))


def match_patterns(t, tokens):
    """Resolve several pattern tokens at once. Accepts them space separated or
    comma separated, keeps the order given, drops duplicates, and reports every
    bad token in one go instead of dying on the first."""
    if not tokens:
        return []
    if isinstance(tokens, str):
        tokens = [tokens]
    flat = [tok for raw in tokens for tok in str(raw).split(",") if tok.strip()]
    slugs = {p["pattern_slug"] for p in t["problems"]}
    out, bad, ambig = [], [], []
    for tok in flat:
        want = slugify(tok)
        if want in slugs:
            hit = want
        else:
            hits = sorted(s for s in slugs if want in s)
            if len(hits) == 1:
                hit = hits[0]
            elif not hits:
                bad.append(tok)
                continue
            else:
                ambig.append("%r matches %s" % (tok, ", ".join(hits)))
                continue
        if hit not in out:
            out.append(hit)
    if bad or ambig:
        msg = []
        if bad:
            msg.append("no pattern matches: " + ", ".join(repr(b) for b in bad))
        msg.extend(ambig)
        msg.append("known: " + ", ".join(sorted(slugs)))
        die("\n  ".join(msg))
    return out


def pdir(p):
    return PROBLEMS / p["pattern_slug"] / p["slug"]


def is_scaffolded(p):
    d = pdir(p)
    return (d / "Solution.java").exists() and (d / "Runner.java").exists()


def parse_d(s):
    return datetime.strptime(s, "%Y-%m-%d").date() if s else None


def days_overdue(p):
    nr = parse_d(p.get("next_review"))
    return None if nr is None else (date.today() - nr).days


def is_due(p):
    ov = days_overdue(p)
    return ov is not None and ov >= 0


# ---------------------------------------------------------------- presentation
def show(p, verbose=True):
    d = pdir(p)
    badge = {"solved": ("solved", "green"), "attempted": ("attempted", "yellow"),
             "pending": ("pending", "dim")}[p["status"]]
    print()
    print(col(p["title"], "bold"))
    line = "  %s  .  %s  .  %s" % (col(p["pattern"], "mag"), p["difficulty"],
                                   col(*badge))
    if p["solved_count"]:
        line += "  .  conf %d/5  .  solved x%d" % (p["confidence"],
                                                   p["solved_count"])
    print(line)
    if not verbose:
        return
    ov = days_overdue(p)
    if ov is not None:
        print("  " + (col("due %dd ago" % ov, "yellow") if ov > 0
                      else "due today" if ov == 0
                      else "next review in %dd" % -ov))
    if is_scaffolded(p):
        print("  %s  %s" % (col("code", "blue"), d / "Solution.java"))
        print("  %s  %s" % (col("spec", "blue"), d / "problem.md"))
        print("  %s  dsa test" % col("test", "blue"))
    else:
        print(col("  not scaffolded yet -- in Claude Code run:  /dsa-scaffold "
                  + p["id"], "yellow"))
    for u in p["links"][:3]:
        print(col("  ref   " + u, "dim"))


def has_own_solution(p):
    """True if Solution.java has diverged from the pristine stub."""
    d = pdir(p)
    sol, stub = d / "Solution.java", d / "Solution.stub.java"
    if not sol.exists() or not stub.exists():
        return False
    return (sol.read_text(encoding="utf-8")
            != stub.read_text(encoding="utf-8"))


def archive_solution(p, reason=""):
    """Snapshot Solution.java into .attempts/ unless it is the stub or is
    byte-identical to the newest snapshot already there. Cheap insurance: a
    clobbered file is only a disaster if nothing else has a copy."""
    if not has_own_solution(p):
        return None
    sol = pdir(p) / "Solution.java"
    body = sol.read_text(encoding="utf-8")
    dest = ARCHIVE / p["pattern_slug"] / p["slug"]
    dest.mkdir(parents=True, exist_ok=True)
    existing = sorted(dest.glob("Solution-*.java"))
    if existing and existing[-1].read_text(encoding="utf-8") == body:
        return None                       # nothing changed since last snapshot
    stamp = datetime.now().strftime("%Y%m%d-%H%M%S")
    keep, n = dest / ("Solution-%s.java" % stamp), 2
    while keep.exists():
        keep, n = dest / ("Solution-%s-%d.java" % (stamp, n)), n + 1
    keep.write_text(body, encoding="utf-8")
    return keep


def reset_to_stub(p):
    """Archive Solution.java into .attempts/ and restore the blank stub."""
    d = pdir(p)
    sol, stub = d / "Solution.java", d / "Solution.stub.java"
    if not stub.exists():
        print(col("  no Solution.stub.java -- leaving your file alone", "yellow"))
        return False
    dest = ARCHIVE / p["pattern_slug"] / p["slug"]
    dest.mkdir(parents=True, exist_ok=True)
    stamp = datetime.now().strftime("%Y%m%d-%H%M%S")
    keep, n = dest / ("Solution-%s.java" % stamp), 2
    while keep.exists():           # two resets in the same second must not collide
        keep, n = dest / ("Solution-%s-%d.java" % (stamp, n)), n + 1
    shutil.copy2(sol, keep)
    shutil.copy2(stub, sol)
    p["nudge_level"] = 0                    # a cold re-solve starts hint-free
    print(col("  blank slate ready", "green")
          + col("   previous attempt archived to %s" % keep, "dim"))
    return True


def offer_fresh_slate(t, p, force=None):
    """Handing back a solved problem with the answer still sitting in the file
    defeats the point, so offer to wipe it. force=True/False skips the prompt."""
    if p["status"] != "solved" or not has_own_solution(p):
        return
    if force is True:
        reset_to_stub(p)
        save(t)
        return
    if force is False:
        return
    if not sys.stdin.isatty():              # piped or scripted -- never destroy
        print(col("  your previous solution is still in Solution.java "
                  "(`dsa cold` for a blank slate)", "dim"))
        return
    print(col("  already solved on %s at confidence %d/5 -- the answer is still "
              "in Solution.java" % (p["last_practiced"] or "?", p["confidence"]),
              "yellow"))
    try:
        ans = input("  archive it and re-solve from scratch? [Y/n] ").strip().lower()
    except (EOFError, KeyboardInterrupt):
        print()
        ans = "n"
    if ans in ("", "y", "yes"):
        reset_to_stub(p)
        save(t)
    else:
        print(col("  keeping it -- open as is", "dim"))


def set_current(t, p, open_editor=True, fresh="ignore"):
    t["current"] = p["id"]
    save(t)
    show(p)
    if fresh != "ignore":
        offer_fresh_slate(t, p, force=fresh)
    if open_editor and is_scaffolded(p):
        code = shutil.which("code") or shutil.which("code.cmd")
        if code:
            subprocess.run([code, str(pdir(p) / "problem.md"),
                            str(pdir(p) / "Solution.java")], check=False)


# -------------------------------------------------------------------- commands
def cmd_next(args):
    t = load()
    ps = t["problems"]
    if args.pattern:
        want = match_pattern(t, args.pattern)
        ps = [p for p in ps if p["pattern_slug"] == want]
    todo = sorted((p for p in ps if p["status"] != "solved"),
                  key=lambda p: p["order"])
    if not todo:
        print(col("nothing pending here -- try: dsa review", "green"))
        return
    set_current(t, todo[0], not args.no_open)


RECENT_CAP = 25          # how many past picks we remember
RECENT_WINDOW = 10       # most we will ever exclude as "just served"


def weight(p, mode):
    """Unsolved problems are all equally new, so pick uniformly. In the modes
    that include solved problems, bias toward the ones you are shaky on."""
    if mode == "unsolved":
        return 1.0
    w = 1.0
    w += (5 - p["confidence"]) * 1.5 if p["confidence"] else 3.0
    w += p["fail_count"] * 2.0
    ov = days_overdue(p)
    if ov is not None and ov > 0:
        w += min(ov, 30) * 0.4
    return max(w, 0.1)


def fresh_choice(args):
    """--cold always wipes, --keep never does, neither means ask."""
    if getattr(args, "cold", False):
        return True
    if getattr(args, "keep", False):
        return False
    return None


def cmd_random(args):
    t = load()
    wanted = match_patterns(t, getattr(args, "patterns", None))
    where = " in " + " + ".join(wanted) if wanted else ""

    pool = [p for p in t["problems"] if is_scaffolded(p)]
    if wanted:
        pool = [p for p in pool if p["pattern_slug"] in wanted]
    if not pool:
        die("no scaffolded problems%s -- scaffold some first with "
            "dsa scaffold <pattern>" % where)

    if args.solved_only:
        mode, label = "solved", "solved"
        pool = [p for p in pool if p["status"] == "solved"]
    elif args.both:
        mode, label = "both", "solved + unsolved"
    else:
        mode, label = "unsolved", "unsolved"
        pool = [p for p in pool if p["status"] != "solved"]

    if not pool:
        retry = " ".join(wanted)
        die("no %s problems left%s.\n  try:  dsa random %s--both"
            "   (or `dsa review` for what is due)"
            % (label, where, (retry + " ") if retry else ""))

    # Anti-clustering: independent draws repeat far more than people expect, so
    # skip whatever was served most recently. Never shrink the pool below half,
    # so a small pattern still works.
    recent = t.get("recent_picks", [])
    window = min(len(pool) // 2, RECENT_WINDOW)
    blocked = set(recent[:window]) if window else set()
    fresh = [p for p in pool if p["id"] not in blocked]
    skipped = len(pool) - len(fresh)
    if fresh:
        pool = fresh

    rng = random.SystemRandom()   # OS entropy, not a seeded Mersenne Twister
    spread = sorted({p["pattern_slug"] for p in pool})

    if args.even and len(spread) > 1:
        # Draw the pattern first so a 23-problem topic cannot drown a 4-problem
        # one, then draw inside it as usual.
        chosen = rng.choice(spread)
        bucket = [p for p in pool if p["pattern_slug"] == chosen]
        pick = rng.choices(bucket, weights=[weight(p, mode) for p in bucket],
                           k=1)[0]
    else:
        pick = rng.choices(pool, weights=[weight(p, mode) for p in pool],
                           k=1)[0]

    t["recent_picks"] = ([pick["id"]]
                         + [i for i in recent if i != pick["id"]])[:RECENT_CAP]

    note = "%d %s candidate%s" % (len(pool), label, "" if len(pool) == 1 else "s")
    if len(spread) > 1:
        note += " across %d patterns" % len(spread)
    if skipped:
        note += ", %d just-served skipped" % skipped
    if args.even and len(spread) > 1:
        note += ", even per pattern"
    if mode != "unsolved":
        note += ", weighted by weakness"
    print(col("random pick from " + note, "dim"))
    set_current(t, pick, not args.no_open, fresh=fresh_choice(args))


def cmd_review(args):
    t = load()
    due = [p for p in t["problems"] if is_due(p)]
    due.sort(key=lambda p: (-(days_overdue(p) or 0), p["confidence"]))
    if not due:
        print(col("nothing due today.", "green"))
        upcoming = sorted((p for p in t["problems"] if p["next_review"]),
                          key=lambda p: p["next_review"])
        if upcoming:
            print(col("  next up %s  %s" % (upcoming[0]["next_review"],
                                            upcoming[0]["title"]), "dim"))
        return
    print(col("%d due for review" % len(due), "bold"))
    for p in due:
        ov = days_overdue(p)
        tag = col("+%dd" % ov, "yellow") if ov > 0 else col("today", "blue")
        print("  %14s  conf %d/5  %s %s" % (tag, p["confidence"], p["title"],
                                            col("(" + p["pattern"] + ")", "dim")))
    if args.start:
        print()
        set_current(t, due[0], True, fresh=fresh_choice(args))
    else:
        print(col("\n  start the top one:  dsa review --start", "dim"))


def cmd_open(args):
    t = load()
    set_current(t, resolve(t, args.id), True)


def cmd_test(args):
    t = load()
    p = resolve(t, args.id)
    d = pdir(p)
    if not is_scaffolded(p):
        die("%s is not scaffolded -- in Claude Code: /dsa-scaffold %s"
            % (p["id"], p["id"]))
    archive_solution(p, "test")        # your code is safe before anything runs
    srcs = sorted(str(f) for f in d.glob("*.java") if not f.name.endswith(".stub.java"))
    out = BUILD / p["pattern_slug"] / p["slug"]
    if out.exists():
        shutil.rmtree(out, ignore_errors=True)
    out.mkdir(parents=True, exist_ok=True)
    print(col("compiling %d file(s)..." % len(srcs), "dim"))
    sys.stdout.flush()
    r = subprocess.run(["javac", "-nowarn", "-d", str(out),
                        str(LIB / "Lib.java")] + srcs,
                       capture_output=True, text=True)
    if r.returncode:
        print(col("COMPILE ERROR", "red"))
        print(r.stdout + r.stderr)
        sys.exit(1)
    print(col("running judge for " + p["title"], "dim"))
    print("-" * 66)
    sys.stdout.flush()          # or our prints land after the child's output
    rc = subprocess.run(["java", "-ea", "-cp", str(out), "Runner"]).returncode
    print("-" * 66)
    if rc == 0:
        print(col("ALL TESTS PASSED", "green")
              + col("   record it:  dsa done -c <1-5>", "dim"))
    else:
        print(col("TESTS FAILED", "red")
              + col("   stuck?  dsa nudge", "dim"))
    sys.exit(rc)


NUDGE_LADDER = [
    "L1 SOCRATIC -- ask them what they notice about the input (order? bounds? "
    "duplicates?) and what a brute force would cost. Name NO technique. End with "
    "one pointed question.",
    "L2 FAMILY -- confirm/correct their brute force, then steer to the *kind* of "
    "tool that removes the waste ('you are recomputing something you already "
    "knew -- what could you carry along?'). Still do not name the mechanism.",
    "L3 INVARIANT -- state the single key observation or invariant plainly. Do "
    "not say what to do with it; ask them what it lets them skip.",
    "L4 ALGORITHM -- give the algorithm in prose/pseudocode: what the pointers or "
    "state mean, the loop, the update rule, the exit condition. Absolutely no "
    "Java code.",
    "L5 SKELETON -- give a commented Java skeleton with the 2-3 crucial lines "
    "left as TODO, plus the complexity. This is the last nudge; point them at "
    "/dsa-teach for the full derivation.",
]


def cmd_nudge(args):
    """Track how deep the interviewer has already hinted on this problem."""
    t = load()
    p = resolve(t, args.id)
    if args.reset:
        p["nudge_level"] = 0
        save(t)
        print(col("nudge level reset to 0 for " + p["title"], "green"))
        return
    lvl = p.get("nudge_level", 0)
    if args.show:
        print("%d" % lvl)
        return
    lvl = min(lvl + 1, len(NUDGE_LADDER))
    p["nudge_level"] = lvl
    save(t)
    print("PROBLEM: %s  (%s)" % (p["title"], p["id"]))
    print("PATTERN: %s" % p["pattern"])
    print("SPEC:    %s" % (pdir(p) / "problem.md"))
    print("THEIR CODE: %s" % (pdir(p) / "Solution.java"))
    print("NUDGE LEVEL: %d of %d" % (lvl, len(NUDGE_LADDER)))
    print("INSTRUCTION: %s" % NUDGE_LADDER[lvl - 1])
    if lvl == len(NUDGE_LADDER):
        print(col("(final rung -- next: /dsa-teach for the full walkthrough)",
                  "dim"))


def schedule(p, confidence):
    base = BASE_INTERVAL[confidence]
    days = min(int(round(base * (GROWTH ** max(0, p["solved_count"] - 1)))),
               MAX_INTERVAL)
    return (date.today() + timedelta(days=max(days, 1))).isoformat()


def cmd_done(args):
    t = load()
    p = resolve(t, args.id)
    conf = args.confidence
    if conf is None:
        raw = input("confidence 1 (shaky) .. 5 (nailed it): ").strip()
        conf = int(raw) if raw.isdigit() else 3
    conf = max(1, min(5, conf))
    kept = archive_solution(p, "done")
    prior = p["attempts"][-1] if p["attempts"] else None
    relog = (prior and prior["date"] == date.today().isoformat()
             and prior["result"] == "solved")
    if relog:
        # logging the same solve twice in one day is a correction, not a second
        # solve -- do not inflate solved_count or stretch the review interval
        print(col("  already logged as solved today -- updating that entry "
                  "instead of counting a second solve", "dim"))
        p["attempts"].pop()
    else:
        p["solved_count"] += 1
    p["status"] = "solved"
    p["confidence"] = conf
    p["last_practiced"] = date.today().isoformat()
    p["next_review"] = schedule(p, conf)
    used = p.get("nudge_level", 0)
    p["nudge_level"] = 0
    p["attempts"].append({"date": date.today().isoformat(), "result": "solved",
                          "nudges_used": used,
                          "confidence": conf, "minutes": args.minutes,
                          "note": args.note or ""})
    save(t)
    print(col("logged: %s solved (conf %d/5%s)"
              % (p["title"], conf,
                 ", %d nudges" % used if used else ", unaided"), "green"))
    print("  next review %s  (%dd)" % (col(p["next_review"], "blue"),
                                       (parse_d(p["next_review"]) - date.today()).days))
    if kept:
        print(col("  solution archived to %s" % kept, "dim"))
    nb = pdir(p) / "notes.md"
    if nb.exists():
        print(col("  jot down the insight / what tripped you: %s" % nb, "dim"))


def cmd_fail(args):
    t = load()
    p = resolve(t, args.id)
    p["fail_count"] += 1
    if p["status"] != "solved":
        p["status"] = "attempted"
    p["confidence"] = max(1, min(p["confidence"] or 2, 2))
    p["last_practiced"] = date.today().isoformat()
    p["next_review"] = (date.today() + timedelta(days=1)).isoformat()
    p["attempts"].append({"date": date.today().isoformat(), "result": "failed",
                          "confidence": p["confidence"], "minutes": args.minutes,
                          "note": args.note or ""})
    save(t)
    print(col("logged: %s failed -- back tomorrow" % p["title"], "yellow"))


def cmd_cold(args):
    """Archive the current solution and reset the file for a blind re-solve."""
    t = load()
    p = resolve(t, args.id)
    if not is_scaffolded(p):
        die("not scaffolded yet")
    if not has_own_solution(p):
        print(col("Solution.java is already the blank stub -- nothing to archive",
                  "dim"))
    else:
        reset_to_stub(p)
    save(t)
    set_current(t, p, True)


def bar(done, total, width=24):
    if not total:
        return ""
    f = int(round(width * done / total))
    return col("#" * f, "green") + col("." * (width - f), "dim")


def cmd_status(args):
    t = load()
    ps = t["problems"]
    if args.pattern:
        want = match_pattern(t, args.pattern)
        ps = [p for p in ps if p["pattern_slug"] == want]
    groups = {}
    for p in ps:
        groups.setdefault(p["pattern"], []).append(p)
    print()
    print("%-24s %-26s %7s %6s %5s" % ("pattern", "progress", "solved",
                                       "scaf", "due"))
    print("-" * 72)
    for name, items in sorted(groups.items(), key=lambda kv: kv[1][0]["order"]):
        sv = sum(1 for p in items if p["status"] == "solved")
        sc = sum(1 for p in items if is_scaffolded(p))
        du = sum(1 for p in items if is_due(p))
        print("%-24s %s  %3d/%-3d %6d %5s" % (
            name, bar(sv, len(items)), sv, len(items), sc,
            col(str(du), "yellow") if du else "-"))
    print("-" * 72)
    sv = sum(1 for p in ps if p["status"] == "solved")
    sc = sum(1 for p in ps if is_scaffolded(p))
    du = sum(1 for p in ps if is_due(p))
    print("%-24s %s  %3d/%-3d %6d %5d" % ("TOTAL", bar(sv, len(ps)), sv,
                                          len(ps), sc, du))
    if t.get("current"):
        cur = by_id(t, t["current"])
        if cur:
            print(col("\ncurrent: %s  (%s)" % (cur["title"], cur["id"]), "dim"))


def cmd_list(args):
    t = load()
    ps = t["problems"]
    if args.pattern:
        want = match_pattern(t, args.pattern)
        ps = [p for p in ps if p["pattern_slug"] == want]
    if args.unsolved:
        ps = [p for p in ps if p["status"] != "solved"]
    if args.missing:
        ps = [p for p in ps if not is_scaffolded(p)]
    cur_pat = None
    for p in sorted(ps, key=lambda p: p["order"]):
        if p["pattern"] != cur_pat:
            cur_pat = p["pattern"]
            print("\n" + col(cur_pat, "mag"))
        mark = {"solved": col("x", "green"), "attempted": col("~", "yellow"),
                "pending": col(".", "dim")}[p["status"]]
        scaf = "" if is_scaffolded(p) else col(" [no scaffold]", "dim")
        print("  %s %-44s %s%s" % (mark, p["slug"], col(p["difficulty"], "dim"),
                                   scaf))


def cmd_dashboard(args):
    t = load()
    ps = t["problems"]
    groups = {}
    for p in ps:
        groups.setdefault(p["pattern"], []).append(p)
    sv = sum(1 for p in ps if p["status"] == "solved")
    due = [p for p in ps if is_due(p)]
    L = ["# DSA Dashboard", "", "#### Tags:", "[[DSA]]", "",
         "*generated by `dsa.py` on %s -- do not edit by hand*"
         % date.today().isoformat(), "",
         "**%d / %d solved**  ·  **%d due for review**" % (sv, len(ps), len(due)),
         ""]
    if due:
        L += ["## Due today", "", "| Problem | Pattern | Confidence | Overdue |",
              "| --- | --- | --- | --- |"]
        for p in sorted(due, key=lambda p: -(days_overdue(p) or 0)):
            ov = days_overdue(p)
            L.append("| %s | %s | %d/5 | %s |"
                     % (p["title"], p["pattern"], p["confidence"],
                        "today" if ov == 0 else "%dd" % ov))
        L.append("")
    L += ["## Progress by pattern", "",
          "| Pattern | Solved | Total | Scaffolded |", "| --- | --- | --- | --- |"]
    for name, items in sorted(groups.items(), key=lambda kv: kv[1][0]["order"]):
        L.append("| %s | %d | %d | %d |"
                 % (name, sum(1 for p in items if p["status"] == "solved"),
                    len(items), sum(1 for p in items if is_scaffolded(p))))
    L.append("")
    for name, items in sorted(groups.items(), key=lambda kv: kv[1][0]["order"]):
        L += ["## " + name, "",
              "| ✓ | Problem | Diff | Conf | Times | Next review | Notes |",
              "| --- | --- | --- | --- | --- | --- | --- |"]
        for p in sorted(items, key=lambda p: p["order"]):
            mark = {"solved": "✅", "attempted": "🟡", "pending": "⬜"}[p["status"]]
            nb = pdir(p) / "notes.md"
            note = ("[[%s\\|notes]]" % nb.relative_to(ROOT).as_posix()
                    if nb.exists() else "")
            L.append("| %s | %s | %s | %s | %s | %s | %s |"
                     % (mark, p["title"], p["difficulty"], p["confidence"] or "",
                        p["solved_count"] or "", p["next_review"] or "", note))
        L.append("")
    DASHBOARD.write_text("\n".join(L), encoding="utf-8")
    print(col("wrote " + DASHBOARD.name, "green"))


# -------------------------------------------------------------- AI launchers
# `dsa nudge` and friends launch Claude Code in ROOT so the /dsa-* project
# commands resolve no matter which directory you invoked from.
AI_VERBS = {
    "nudge":    ("dsa-nudge",    "interviewer gives one escalating hint"),
    "teach":    ("dsa-teach",    "full first-principles walkthrough"),
    "critique": ("dsa-critique", "post-solve interviewer review"),
    "scaffold": ("dsa-scaffold", "build the local judge for a problem/pattern"),
    "variant":  ("dsa-variant",  "twist a solved problem into a new one"),
    "drill":    ("dsa-drill",    "invent a new problem in a pattern"),
}


def cmd_ai(args):
    slash = AI_VERBS[args.verb][0]
    exe = shutil.which("claude")
    if not exe:
        die("claude CLI not found on PATH.\n"
            "  install Claude Code, or open a session in %s and run /%s there"
            % (ROOT, slash))
    rest = [a for a in (args.rest or []) if a]
    prompt = "/" + slash + ((" " + " ".join(rest)) if rest else "")
    print(col("claude", "mag") + col("  %s   [in %s]" % (prompt, ROOT), "dim"))
    sys.stdout.flush()
    try:
        sys.exit(subprocess.run([exe, prompt], cwd=str(ROOT)).returncode)
    except KeyboardInterrupt:
        sys.exit(130)


def cmd_where(args):
    print(ROOT)


def cmd_code(args):
    exe = shutil.which("code") or shutil.which("code.cmd")
    if not exe:
        die("VS Code CLI ('code') not found on PATH")
    target = ROOT
    if args.id:
        target = pdir(resolve(load(), args.id))
    subprocess.run([exe, str(target)], check=False)
    print(col("opened " + str(target), "green"))


# ------------------------------------------------------------------------ cli
def main():
    ap = argparse.ArgumentParser(
        prog="dsa", description=__doc__,
        formatter_class=argparse.RawDescriptionHelpFormatter)
    sub = ap.add_subparsers(dest="cmd", required=True)

    s = sub.add_parser("init", help="rebuild tracker from the cheat sheet")
    s.set_defaults(fn=cmd_init)

    s = sub.add_parser("next", help="next unsolved problem in pattern order")
    s.add_argument("pattern", nargs="?")
    s.add_argument("--no-open", action="store_true")
    s.set_defaults(fn=cmd_next)

    s = sub.add_parser("random",
                       help="random unsolved problem from one or more patterns")
    s.add_argument("patterns", nargs="*", metavar="PATTERN",
                   help="one or more patterns, space or comma separated "
                        "(omit for all)")
    s.add_argument("--even", action="store_true",
                   help="give each named pattern an equal chance, ignoring "
                        "how many problems it has")
    g = s.add_mutually_exclusive_group()
    g.add_argument("--both", action="store_true",
                   help="draw from solved AND unsolved, weighted by weakness")
    g.add_argument("--solved-only", action="store_true",
                   help="only problems you have already solved (pure revision)")
    f = s.add_mutually_exclusive_group()
    f.add_argument("--cold", action="store_true",
                   help="if already solved, wipe to a blank slate without asking")
    f.add_argument("--keep", action="store_true",
                   help="if already solved, keep the old solution without asking")
    s.add_argument("--no-open", action="store_true")
    s.set_defaults(fn=cmd_random)

    s = sub.add_parser("review", help="problems due today")
    s.add_argument("--start", action="store_true", help="open the most overdue one")
    f = s.add_mutually_exclusive_group()
    f.add_argument("--cold", action="store_true",
                   help="wipe to a blank slate without asking")
    f.add_argument("--keep", action="store_true",
                   help="keep the old solution without asking")
    s.set_defaults(fn=cmd_review)

    s = sub.add_parser("open", help="open a problem")
    s.add_argument("id", nargs="?")
    s.set_defaults(fn=cmd_open)

    s = sub.add_parser("test", help="compile + run the local judge")
    s.add_argument("id", nargs="?")
    s.set_defaults(fn=cmd_test)

    s = sub.add_parser("done", help="record a solved attempt")
    s.add_argument("id", nargs="?")
    s.add_argument("-c", "--confidence", type=int)
    s.add_argument("-m", "--minutes", type=int)
    s.add_argument("-n", "--note")
    s.set_defaults(fn=cmd_done)

    s = sub.add_parser("fail", help="record a failed attempt")
    s.add_argument("id", nargs="?")
    s.add_argument("-m", "--minutes", type=int)
    s.add_argument("-n", "--note")
    s.set_defaults(fn=cmd_fail)

    # internal: advances the hint ladder. /dsa-nudge calls this; humans use
    # `dsa nudge`, which launches Claude Code instead (see AI_VERBS).
    # no help= keyword at all -- that is what keeps it out of the listing
    s = sub.add_parser("nudge-state")
    s.add_argument("id", nargs="?")
    s.add_argument("--reset", action="store_true")
    s.add_argument("--show", action="store_true", help="print level only")
    s.set_defaults(fn=cmd_nudge)

    s = sub.add_parser("cold", help="archive your solution, reset to blank stub")
    s.add_argument("id", nargs="?")
    s.set_defaults(fn=cmd_cold)

    s = sub.add_parser("status", help="progress overview")
    s.add_argument("pattern", nargs="?")
    s.set_defaults(fn=cmd_status)

    s = sub.add_parser("list", help="list problems")
    s.add_argument("pattern", nargs="?")
    s.add_argument("--unsolved", action="store_true")
    s.add_argument("--missing", action="store_true", help="only un-scaffolded")
    s.set_defaults(fn=cmd_list)

    s = sub.add_parser("dashboard", help="write DSA Dashboard.md for Obsidian")
    s.set_defaults(fn=cmd_dashboard)

    s = sub.add_parser("where", help="print the DSA folder path")
    s.set_defaults(fn=cmd_where)

    s = sub.add_parser("code", help="open the DSA folder (or one problem) in VS Code")
    s.add_argument("id", nargs="?")
    s.set_defaults(fn=cmd_code)

    # AI verbs -- each launches Claude Code with the matching /dsa-* command
    for verb, (slash, helptext) in AI_VERBS.items():
        s = sub.add_parser(verb, help=helptext + " [Claude]")
        s.add_argument("rest", nargs=argparse.REMAINDER)
        s.set_defaults(fn=cmd_ai, verb=verb)

    args = ap.parse_args(sys.argv[1:] or ["status"])
    args.fn(args)


if __name__ == "__main__":
    main()
