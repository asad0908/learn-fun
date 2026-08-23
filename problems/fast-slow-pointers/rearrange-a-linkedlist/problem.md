# Rearrange a LinkedList (medium)

**Pattern:** Fast & Slow Pointers  **Difficulty:** medium

## Problem

Given a singly linked list `L0 -> L1 -> ... -> Ln-1 -> Ln`, reorder it **in
place** to:

```
L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 -> ...
```

Only the `next` pointers may change — do not swap node values, and do not
allocate new nodes. Return nothing; the caller keeps the original head, which
must remain the first node.

## Constraints

- `1 <= number of nodes <= 5 * 10^4`
- `1 <= Node.val <= 1000`
- `O(1)` extra space (an `ArrayList` of the nodes is the brute force)

## Examples

```
[1,2,3,4]        ->  [1,4,2,3]
[1,2,3,4,5]      ->  [1,5,2,4,3]
[1]              ->  [1]
[1,2]            ->  [1,2]
[1,2,3]          ->  [1,3,2]
```

## Target

Time `O(n)`, space `O(1)`.

Three moves compose here: find the middle, reverse the second half, then merge
the two halves alternately.

## Reference

https://leetcode.com/problems/reorder-list/
