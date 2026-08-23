---
description: Full first-principles derivation — brute force to optimal, no magic steps
argument-hint: "[problem-id]  (defaults to the current problem)"
allowed-tools: Bash(python dsa.py:*), Read, Write, Edit, Glob
---

Teach the problem `$ARGUMENTS` (if empty, use the `current` field in
`tracker.json`) from first principles. The goal is that the reader could have
*derived* this themselves, and can re-derive it in six months.

Read the problem's `problem.md`, and read their `Solution.java` so you can
address what they actually tried.

## Required structure

**1. Restate the problem in one sentence.** What are we given, what do we
return. Then the constraints that matter and why they matter (n ≤ 10⁵ means
O(n²) is ~10¹⁰ ops — dead).

**2. The dumbest thing that works.** Write the brute force in Java. State its
complexity. This is not filler — it defines what "better" means, and it is your
oracle in `Brute.java`.

**3. Where the waste is.** The single most important section. Point at the exact
repeated work in the brute force. Make it concrete with a small example, walked
by hand: "on `[1,3,5,7]` with target 8, when we test the pair (1,7) and it's too
big, we then test (3,7), (5,7)… but we already knew every one of those is worse."
The reader must *see* the waste before you name a fix.

**4. The observation that removes it.** State the invariant or property. Prove
it in two or three lines — an exchange argument, a monotonicity claim, whatever
is honest. Do not assert it; justify it. If it relies on the input being sorted,
say precisely where sortedness is used, because that is the part that transfers
to other problems.

**5. Build the algorithm from the observation.** What does each pointer/variable
mean at all times (the loop invariant)? Why is the update rule correct? Why does
it terminate? Why does it not miss an answer — the step people hand-wave.

**6. The optimal Java code.** Clean, commented at the decision points only, not
line-by-line narration.

**7. Complexity.** Time and space, with a sentence of justification each.

**8. Edge cases and how the code handles them.** Empty, single element, all
equal, all negative, duplicates, overflow (`left + (right-left)/2`, `long` sums).

**9. Pattern transfer.** Two or three sentences: what is the reusable trigger?
"Sorted array + looking for a pair/triplet with a sum condition → opposite-end
two pointers, because moving either end changes the sum monotonically." This is
the sentence that makes the next problem easier, so make it a *rule*, not a
restatement of this problem.

**10. Their attempt.** If their `Solution.java` has real content, close with a
short honest read: what they had right, where the thinking went sideways, and
whether their approach could have been salvaged or was fundamentally the wrong
shape.

## Rules

- Never skip step 3. A derivation that jumps from brute force to the trick is
  exactly the kind of explanation that leaves them unable to redo it.
- If there is a meaningful middle step (brute → sorting/hashmap → two pointers),
  show it. The ladder matters more than the destination.
- Do not modify `Solution.java`. If they want the worked solution saved, write it
  to `solution-notes.md` in the problem folder and append a link line to
  `notes.md`.
- After teaching, remind them: `python dsa.py cold <id>` archives their attempt
  and resets the file, so they can re-solve it blind in a few days. Understanding
  a solution is not the same as being able to produce one.
