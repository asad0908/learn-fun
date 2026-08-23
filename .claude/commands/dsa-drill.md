---
description: Invent a brand-new problem in a pattern you've never seen
argument-hint: "<pattern-slug> [easy|medium|hard]"
allowed-tools: Bash, Read, Write, Edit, Glob
---

Invent a **new** problem in the pattern `$ARGUMENTS` that is not in
`DSA Patterns Cheat Sheet.md` and is not a rewording of one that is.

Check what already exists with `python dsa.py list <pattern-slug>` before you
design, and read a couple of the existing `problem.md` files in that pattern so
the new one sits at the same level of precision.

## Design rules

- It must be **genuinely solvable by the pattern** — the pattern should be the
  natural tool, not a coincidence. If the cleanest solution is a hashmap, it is
  not a two-pointer problem.
- It must have a **checkable, well-defined answer**. Avoid "return any valid
  arrangement" unless `Runner` can verify the *property* rather than compare to a
  fixed answer (if you go that route, validate the property directly instead of
  diffing against Brute).
- Ground it in a concrete scenario (inventory, timestamps, sensor readings) —
  concrete framings are what interviews actually sound like, and translating the
  scenario into the abstraction is half the skill.
- Choose constraints that make the intended complexity necessary and the brute
  force clearly too slow.
- Difficulty: honour the argument if given, else match the hardest problem they
  have already solved in this pattern (check `status` and `confidence` in
  `tracker.json`).

## Where it goes

`problems/<pattern-slug>/drill-<short-slug>/` — the `drill-` prefix marks it as
generated rather than from the sheet.

Same six files and the same verification as `/dsa-scaffold`:  `problem.md`,
`Solution.java`, `Solution.stub.java`, `Brute.java`, `Runner.java` (fixed cases +
edge cases + `j.stress` against Brute), `notes.md`.

Be especially careful with `Brute.java` here: for an invented problem there is no
external judge to catch a wrong oracle, so the brute force *is* the spec. Write
it as the most literal possible transcription of the problem statement — nested
loops enumerating every candidate — and re-read the statement against it line by
line.

## Register it

Add a full entry to `tracker.json` with `"status": "pending"`, `"difficulty"`
set, and `"order"` just after the last problem of that pattern. Then verify with
`python dsa.py test <id>` (fails on stub, passes with Brute's logic pasted in,
stub restored) and open it with `python dsa.py open <id>`.

Tell them the pattern and difficulty, and **nothing about the approach**.
