---
description: Post-solve review — what an interviewer would say about your passing code
argument-hint: "[problem-id]  (defaults to the current problem)"
allowed-tools: Bash, Read, Glob
---

Their tests pass. Now review `$ARGUMENTS` (default: `current` in `tracker.json`)
the way a good interviewer would in the last five minutes of the round.

Read `problem.md`, their `Solution.java`, and `Brute.java` for reference. Run
`python dsa.py test <id>` to confirm it actually passes before reviewing — if it
does not, say so and stop.

Cover, briefly and in this order:

**1. Complexity.** State the actual time and space of *their* code, not the
intended one. If it is worse than the target in `problem.md`, that is the
headline: say where the extra factor comes from.

**2. Correctness risks the tests did not catch.** Integer overflow (`left+right`
vs `left+(right-left)/2`, sums exceeding `int`), aliasing, mutating the input
when the caller may not expect it, assumptions about non-empty input. Be
concrete: name the input that would break it.

**3. Clarity as an interviewer scores it.** Variable names that carry meaning
(`left`/`right` over `i`/`j` when they are ends of a window), the invariant being
obvious from the code, dead branches, redundant checks. Would you understand this
in 30 seconds on a whiteboard?

**4. The idiomatic version, only if theirs is materially clumsier.** Show the
tightened code and say what specifically improved. If their solution is already
good, say so plainly and skip this — inventing nitpicks is worse than saying
"this is clean".

**5. The follow-up question.** What the interviewer asks next: "now the array is
streamed and you can't index it", "now do it in O(1) space", "now there can be
duplicates". One question, the natural one. Do not answer it — offer to turn it
into a real problem with `/dsa-variant`.

Keep it under 300 words unless something is genuinely wrong. Prose and short
code blocks, no scorecards or rubric tables.

Finally, if `notes.md` still has empty sections, remind them to fill in
`## Pattern trigger` in one sentence while it is fresh — that line is what makes
the next problem in this pattern faster, and it is the thing they will skip.
