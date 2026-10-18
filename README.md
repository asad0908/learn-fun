# DSA Practice Kit

Local practice rig for `DSA Patterns Cheat Sheet.md` (185 problems across 15
patterns). No login, no submission limits, unlimited retries, works offline.

The cheat sheet stays the source of truth for *what* to solve. `tracker.json`
records *how it went*. Everything else is generated. Each problem folder ships
a **local judge**, not a fixed answer key — a brute-force oracle plus a random
input generator, stress-tested against your solution — so you can re-solve the
same problem as many times as you want and still get caught on edge cases.

This is a personal copy shared for others to reuse — your own progress
(`tracker.json`, solved code, notes) is yours alone and never overwrites
anyone else's once you clone it.

## Setup

Prerequisites:
- **JDK 21+** on your `PATH` (`javac`/`java`) — problems are solved in Java
- **Python 3.10+** on your `PATH` — the engine (`dsa.py`) is plain stdlib, no pip installs
- *(optional)* [Claude Code CLI](https://docs.claude.com/en/docs/claude-code) — only needed for the AI-assisted commands (`dsa clarify`, `dsa nudge`, `dsa teach`, `dsa critique`, `dsa scaffold`, `dsa variant`, `dsa drill`); everything else works without it

1. Clone this repo.
2. Make `dsa` available from anywhere: copy `bin/dsa` and `bin/dsa.cmd` into a
   directory that's already on your `PATH` (e.g. `~/.local/bin` on
   Git Bash/macOS/Linux, or any folder you've added to `PATH` on Windows).
3. Point it at your clone by setting `DSA_HOME`:
   - Git Bash / macOS / Linux: add `export DSA_HOME="/path/to/dsa-practice-kit"` to your `~/.bashrc` or `~/.zshrc`
   - Windows (cmd/PowerShell): `setx DSA_HOME "C:\path\to\dsa-practice-kit"`, then open a new terminal
4. Open a terminal anywhere and run `dsa` — it should print a status overview
   ("0 / 185 solved"). `dsa where` prints where it thinks the kit lives, if
   something looks off.

If you'd rather not touch your `PATH`, skip steps 2–3 and just run
`python dsa.py <command>` from inside the cloned folder instead of `dsa <command>`.

## Where things stand

Only **Two Pointers**, **Fast & Slow Pointers**, **Sliding Window** and
**Graphs** (52 of 185 problems) have a scaffolded local judge out of the box. The rest just have the cheat-sheet
entry and a link to the original LeetCode/GFG problem. Run
`dsa list --missing` to see what's left, and `dsa scaffold <pattern>` (needs
Claude Code) to build the judge for a pattern yourself — PRs adding scaffolds
for more patterns are welcome.

## `dsa` works from anywhere

Once set up, open a terminal in any directory — cmd, PowerShell or Git Bash —
and type `dsa`. No `cd`, no `python dsa.py`.

If you ever move this folder, update `DSA_HOME` to the new path and everything
keeps working. `dsa where` prints where it's pointing.

## Daily loop

```bash
dsa next            # next unsolved problem, opens in VS Code
dsa clarify         # optional: question the interviewer, agree an approach
# ...solve in Solution.java...
dsa run             # try it on your own inputs (Brute prints the expected)
dsa test            # local judge: fixed cases + random stress test
dsa done -c 4       # log it, schedules the next review
```

### Your own inputs -- `dsa run`

LeetCode's custom-testcase box, except you also get the **expected** answer,
because `Brute.java` is right there as an oracle. The first run creates
`input.txt` next to your Solution, pre-filled with the examples from
`problem.md`; add your own cases underneath and rerun.

```text
[2,7,11,15]                        <- one value per line, like LeetCode
9
nums = [2,7,11,15], target = 9     <- or one line, names optional
Input:  grid = ["101","010"]       <- or pasted straight from problem.md
[3,2,0,-4] pos=1                   <- linked list whose tail links to index 1
[1,null,2,3]                       <- binary tree, level order
```

```bash
dsa run                              # every case in input.txt
dsa run -e "nums = [1,1,1], k = 2"   # one-off case, input.txt untouched
dsa run --edit                       # open input.txt in VS Code
```

Each case prints your answer and time, Brute's answer, and `ok` / `DIFF`.
A `DIFF` only means a bug when the problem has a single correct answer; for
"any valid pair" problems `dsa test` is the real judge. Exceptions show the
line in your Solution.java; anything over 5 s is reported as a timeout.

`-c` is confidence 1–5 and it drives the spaced-repetition interval:
1→tomorrow, 2→3 days, 3→a week, 4→16 days, 5→5 weeks, stretching each time you
re-solve it successfully.

Bare `dsa` shows your status.

## Revision loop

```bash
dsa review              # what's due today
dsa review --start      # open the most overdue one
dsa random              # a random UNSOLVED problem, any pattern
dsa random trees        # ...within one pattern
dsa random trees heap graphs      # ...from several at once
dsa random trees,heap,graphs      # commas work too
dsa random trees,heap --even      # each topic gets an equal shot
dsa random --both       # any problem, weighted toward your weak spots
dsa random --solved-only # pure revision: only things you've already solved
dsa cold <id>           # archive your old solution, reset to blank stub
```

**Mixing topics.** Name as many patterns as you like, space or comma separated,
with the same loose matching as everywhere else (`two-p`, `fast-slow`, `bin`).
Anything unmatched is reported before a problem is served, so a typo in one topic
never silently narrows your mix.

By default the merged pool is drawn **proportionally**, so a 23-problem topic
comes up far more often than a 4-problem one. `--even` picks the *pattern* first
and then a problem inside it, giving every named topic the same chance regardless
of size — which is what you want when you're rotating across patterns rather than
grinding one. With a single pattern named, `--even` does nothing.

**`dsa random` never serves a solved problem** unless you ask for it with
`--both` or `--solved-only`. It also remembers the last 25 picks and skips
whatever was just served, because independent random draws cluster far more than
feels right — with 9 candidates you'd otherwise hit the same one twice in a
handful of tries. The pool never shrinks below half, so small patterns still work.

When a mode *does* hand you a solved problem with your old answer still sitting
in `Solution.java`, it asks first:

```
  already solved on 2026-08-21 at confidence 3/5 -- the answer is still in Solution.java
  archive it and re-solve from scratch? [Y/n]
```

Enter accepts. `--cold` always wipes without asking, `--keep` never does. When
output is piped rather than a live terminal it never destroys anything — it just
reminds you `dsa cold` exists. `dsa review --start` behaves the same way.

`cold` is the important one: re-solving from scratch is what builds recall. Your
previous attempts are kept in `.attempts/` (timestamped, never overwritten) so
you can compare.

## When you're stuck

These launch Claude Code in the DSA folder for you:

| Command | What it does |
| --- | --- |
| `dsa clarify` | The first ten minutes of an interview, before any code. Ask about the contract (empty input? duplicates? what if there's no answer?) and get answers from the spec -- or the interviewer's call where it's silent. Pitch an approach and get pushback, a counterexample, or a go. Never names the technique for you. Ends with the questions a strong candidate would also have asked, logged to `notes.md`. `dsa clarify "can the array be empty?"` jumps straight in. |
| `dsa nudge` | Interviewer gives **one** hint, deeper each time you ask. Reads your actual code. 5 rungs: Socratic question → technique family → the key invariant → the algorithm in prose → a skeleton with gaps. Never jumps ahead. |
| `dsa teach` | Full derivation from first principles: brute force → where the waste is → the observation → the algorithm → optimal code → complexity → the reusable pattern trigger. Use *after* you've tried, or when the nudge ladder runs out. |
| `dsa critique` | Your tests pass — now what would an interviewer say? Complexity, overflow, naming, and the natural follow-up question. |

Already inside a Claude Code session? Type the slash command directly instead —
`/dsa-clarify`, `/dsa-nudge`, `/dsa-teach`, `/dsa-critique`. Same thing, keeps your context.

Nudge depth is tracked per problem and resets when you mark it done, so a hint
you took today doesn't leak into next month's re-solve. `dsa done` also records
how many nudges you used, which is the honest measure of progress.

## Growing the problem set

| Command | What it does |
| --- | --- |
| `dsa scaffold <id or pattern>` | Build the local judge for problems that don't have one yet |
| `dsa variant` | Twist a solved problem — removes a guarantee, changes the output shape, adds a dimension. Same pattern, different invariant. |
| `dsa drill <pattern>` | Invent a brand-new problem in that pattern that isn't in the sheet |

The revision ladder in order of value: re-solve cold → variant → fresh drill.

## Anatomy of a problem folder

```
problems/two-pointers/triplet-sum-to-zero/
  problem.md          statement, real constraints, examples, target complexity
  Solution.java       you write this
  Solution.stub.java  pristine blank copy (cold resets from this — don't edit)
  Brute.java          slow but obviously-correct oracle
  Runner.java         the judge: fixed cases, edge cases, stress test, perf check
  notes.md            your approach + the mistake + the pattern trigger
```

### Why the tests are better than a website's

`Runner.java` doesn't just check a handful of examples. `j.stress(...)` generates
thousands of small random inputs and compares your `Solution` against `Brute`. On
a mismatch it prints the exact failing input:

```
FAIL  random sorted arrays vs brute  [seed 47, after 46 passing rounds]
      input    nums=[-5, -4, -3, 1, 2, 2, 4, 4] target=3
      brute    [4, 5]
      yours    [3, 6]
```

Small inputs with tight value ranges are deliberate — duplicates and collisions
are where off-by-one bugs live. `j.perf(...)` separately fails you for shipping
the wrong complexity, which a passing-but-slow solution would otherwise hide.

## Tracking

```bash
dsa status              # progress bars per pattern
dsa status binary-search
dsa list trees --unsolved
dsa list --missing      # what still needs scaffolding
dsa dashboard           # writes "DSA Dashboard.md" for Obsidian
dsa code                # open the whole folder in VS Code
dsa where               # print the DSA folder path
```

`dsa init` re-reads the cheat sheet. It merges — add rows to the sheet and re-run,
and your progress is preserved. Hand-added variants and drills survive too.

## Notes on the setup

- `notes.md` matters more than it looks. The `## Pattern trigger` line — one
  sentence on what in a statement should make you reach for this pattern — is
  what makes the *next* problem faster. Fill it while it's fresh.
- The reference URLs are still in `problem.md`. Paste into LeetCode for the real
  submit whenever you want the badge; treat this system as the gym.
- `lib/Lib.java` holds `ListNode`, `TreeNode`, `Gen` (random inputs), `Fmt`
  (printing, deep copies, tree/list builders) and `Judge`. It's compiled into
  every problem, so new scaffolds just use it. `lib/Custom.java` is the
  generic `dsa run` driver -- it reads parameter types by reflection, so new
  scaffolds get custom input for free.
- `dsa nudge-state` is internal plumbing for `/dsa-nudge` — you won't need it.
- `.build/` is throwaway compiler output. `.attempts/` is your personal archive
  of old solutions. Both are gitignored — they're created the first time you
  run `dsa test` / `dsa cold` and stay local to your clone.
