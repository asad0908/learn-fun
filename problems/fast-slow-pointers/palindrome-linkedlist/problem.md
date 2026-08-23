# Palindrome LinkedList (medium)

**Pattern:** Fast & Slow Pointers  **Difficulty:** medium

## Problem

Given the head of a singly linked list, return `true` if the sequence of values
reads the same forwards and backwards.

You may modify the list while working (reversing half of it is the intended
approach); restoring it afterwards is good manners but not checked.

## Constraints

- `1 <= number of nodes <= 10^5`
- `0 <= Node.val <= 9`
- **`O(1)` extra space** — copying the values into an array or stack is the
  brute force, so beat it

## Examples

```
[1,2,2,1]    ->  true
[1,2]        ->  false
[1]          ->  true
[1,2,3,2,1]  ->  true     // odd length, 3 is the pivot
[1,2,2,2,1]  ->  true
```

## Target

Time `O(n)`, space `O(1)`.

Finding the middle is step one. The trick is reversing the second half in place,
comparing, and knowing when to stop for odd versus even lengths.

## Reference

https://leetcode.com/problems/palindrome-linked-list/
