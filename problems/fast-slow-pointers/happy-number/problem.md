# Happy Number (medium)

**Pattern:** Fast & Slow Pointers  **Difficulty:** medium

## Problem

A number is **happy** if repeatedly replacing it with the sum of the squares of
its digits eventually reaches `1`. If the process instead loops forever without
reaching `1`, the number is not happy.

Return `true` if `n` is happy.

## Constraints

- `1 <= n <= 2^31 - 1`
- **`O(1)` extra space** — the `HashSet` of seen values is the brute force

## Examples

```
n = 19  ->  true    // 1+81=82, 64+4=68, 36+64=100, 1+0+0=1
n = 2   ->  false   // 4, 16, 37, 58, 89, 145, 42, 20, 4 ... loops
n = 1   ->  true
n = 7   ->  true    // 49, 97, 130, 10, 1
```

## Target

Time `O(log n)` per step with `O(1)` space.

The link to this pattern: the sequence of digit-square-sums is a linked list
where `next(x)` is the next value, so "does it loop?" is cycle detection.

## Reference

https://leetcode.com/problems/happy-number/
