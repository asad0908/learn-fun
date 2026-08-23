---
description: Twist a problem you've solved into a variant with fresh tests
argument-hint: "[problem-id] [--harder|--twist <idea>]"
allowed-tools: Bash, Read, Write, Edit, Glob
---

Take `$ARGUMENTS` (default: the `current` problem in `tracker.json`) and produce
a **variant** — same pattern, changed requirements, so the muscle memory of the
original solution does not carry over unmodified.

This is the highest-value rung of the revision ladder: it is the thing no
practice site gives them.

## Choosing the twist

Read the original `problem.md` and `notes.md` (their notes tell you what they
found hard — aim at that). Then change **one structural thing**:

- Remove a guarantee the solution leaned on (input no longer sorted; duplicates
  now allowed; values can be negative)
- Change the output shape (count instead of return, all answers instead of one,
  indices instead of values, in-place instead of a new array)
- Change the objective (closest instead of exact, at-most instead of exactly,
  minimise instead of maximise)
- Add a dimension (k of them instead of two; a second array; a stream instead of
  an array)
- Invert it (given the answer, reconstruct an input)

Pick the twist that forces a *different invariant*, not just different
bookkeeping. If `--twist <idea>` was given, use their idea. If `--harder`, prefer
a twist that raises the difficulty a full step.

Say in one line at the top of `problem.md` what changed relative to the original,
because knowing the delta is part of the learning — but do **not** say what to do
about it.

## Where it goes

`problems/<pattern-slug>/<original-slug>-v2/` (`-v3` if `-v2` exists).

Same six files as `/dsa-scaffold`, same rules: `problem.md`, `Solution.java`,
`Solution.stub.java`, `Brute.java`, `Runner.java` with real stress testing,
`notes.md`. The new `Brute.java` must solve the *variant*, not the original —
this is the most likely place to get it wrong, so re-derive it rather than
editing the old one.

## Register it

1. Add the variant to the tracker: append its slug to the original problem's
   `variants` array in `tracker.json`, and add a full problem entry for it with
   `"status": "pending"`, `"order"` = the original's order (so it sorts next to
   its parent), and `"pattern"`/`"pattern_slug"` copied from the original.
2. Verify with `python dsa.py test <new-id>`: must fail on the stub, pass when
   Brute's logic is pasted into Solution. Restore the stub.
3. Finish by opening it: `python dsa.py open <new-id>`.

Note that `python dsa.py init` preserves unknown entries, so a hand-added variant
survives future re-parses of the cheat sheet.
