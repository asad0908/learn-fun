---
description: Generate the local judge for a problem or a whole pattern
argument-hint: "<problem-id | pattern-slug>"
allowed-tools: Bash, Read, Write, Edit, Glob, Grep
---

Scaffold `$ARGUMENTS` — either one problem id (`two-pointers/3sum`) or a whole
pattern slug (`two-pointers`, scaffold every un-scaffolded problem in it).

Find the targets with `python dsa.py list $ARGUMENTS --missing`. The problem's
title, difficulty and reference links are in `tracker.json`.

## What to create, in `problems/<pattern-slug>/<slug>/`

**`problem.md`** — so the site is never needed:
- Title, pattern, difficulty
- The statement in your own words. Be exact about what is returned (indices or
  values? 0- or 1-based? in-place or new array? any valid answer or a specific
  one?). This is the #1 source of wasted time, so nail it.
- **Constraints** with real bounds (`1 <= n <= 10^5`, `-10^9 <= a[i] <= 10^9`).
  Take them from the actual source problem — they determine the target
  complexity, so do not invent loose ones.
- 2–3 worked examples with a line of explanation each.
- A `## Target` line: expected time and space complexity. Do not explain how.
- The reference URL(s) at the bottom.
- Nothing else. No hints, no approach section, no pattern tips — they are trying
  to solve it.

**`Solution.java`** — `class Solution` with the exact method signature from the
source problem, an empty body returning a dummy value, and a `// TODO` line.
Include a javadoc line stating the signature contract. No hints in comments.

**`Solution.stub.java`** — byte-identical copy. `dsa.py cold` restores from this
for blind re-solves, so it must stay pristine.

**`Brute.java`** — `class Brute`, same signature, the obviously-correct slow
implementation. Correctness is the only goal; O(n³) is fine. This is the oracle,
so it must be right — prefer the dumbest formulation you can defend over a
clever one. If the problem's answer is not unique, Brute must produce the same
canonical form Runner compares against.

**`Runner.java`** — `class Runner` with `main`, using `Judge`/`Gen`/`Fmt` from
`lib/Lib.java`:
1. `Judge j = new Judge("<title>");`
2. Every example from `problem.md` as a `j.expect(...)`.
3. Explicit edge cases: empty, size 1, all equal, all negative, max/min values,
   duplicates, target absent — whichever apply.
4. **`j.stress(...)` — the important part.** Small random inputs (n ≤ 12, tight
   value range like −5..5) so collisions and duplicates are common, ~2000
   rounds, comparing `Solution` against `Brute`. Use `Judge.diff(...)` (or
   `diffUnordered` for set-like answers) and `Fmt.copy(...)` to deep-copy inputs
   so a destructive Solution cannot poison the Brute call.
5. A `j.perf(...)` check at the real constraint size when the point of the
   problem is beating a slower complexity.
6. `j.finish();` last.

**`notes.md`** — headings only, for them to fill in:
`## Approach`, `## Why it works`, `## Where I got stuck`, `## Pattern trigger`.

## NEVER clobber work in progress

Before writing any file into an existing problem folder, check whether
`Solution.java` differs from `Solution.stub.java`. **If it differs, that is the
user's code — do not overwrite it, and do not overwrite `notes.md` either.**
Write the other files (`problem.md`, `Brute.java`, `Runner.java`,
`Solution.stub.java`) and report which problems you skipped.

Any generator script you write must carry this guard, and must be safe to re-run
— you *will* re-run it after fixing a bad test case, and by then the user may
have solved that problem. The same rule applies to any bulk edit of
`tracker.json`: never reset a whole pattern, only the exact entries you created.

This has bitten before: a cleanup script that reset a whole pattern destroyed a
real solve. `.attempts/` now snapshots on every `dsa test` and `dsa done`, but
that is a safety net, not a licence to overwrite.

## Verify before you report done

Run `python dsa.py test <id>` for each problem. It must **FAIL** with the empty
stub (proving the tests actually test something). Then temporarily copy `Brute`'s
logic into `Solution`, confirm it **PASSES**, and restore the stub. A scaffold
whose tests pass on an empty solution is worse than no scaffold — silently
useless. Report any problem you could not verify both ways.

Do not touch `Solution.java` beyond leaving the stub in place when you finish.
