# Middle of the LinkedList (easy)

**Pattern:** Fast & Slow Pointers  **Difficulty:** easy

## Problem

Given the head of a singly linked list, return the **middle node**.

If the list has an even number of nodes, return the **second** of the two middle
nodes.

Return the actual node from the list — the judge compares by identity, and the
returned node still carries the rest of the list behind it.

## Constraints

- `1 <= number of nodes <= 100`
- `1 <= Node.val <= 100`
- one pass, `O(1)` extra space (counting the length first is the brute force)

## Examples

```
[1,2,3,4,5]    ->  node at index 2 (val 3), so [3,4,5]
[1,2,3,4,5,6]  ->  node at index 3 (val 4), so [4,5,6]     // second middle
[1]            ->  node at index 0
[1,2]          ->  node at index 1 (val 2)                 // second middle
```

## Target

Time `O(n)` in a single pass, space `O(1)`.

## Reference

https://leetcode.com/problems/middle-of-the-linked-list/
