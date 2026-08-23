# Pair with Target Sum (easy)

**Pattern:** Two Pointers  **Difficulty:** easy

## Problem

You are given an array `numbers` sorted in **non-decreasing** order and an
integer `target`.

Find two numbers that add up to `target` and return their **1-based** indices as
`{i, j}` where `i < j`.

- Exactly one such pair is guaranteed to exist.
- You may not use the same element twice.
- Any valid pair is accepted if several exist.

## Constraints

- `2 <= numbers.length <= 3 * 10^4`
- `-1000 <= numbers[i] <= 1000`
- `numbers` is sorted in non-decreasing order
- `-1000 <= target <= 1000`

## Examples

```
numbers = [2,7,11,15], target = 9   ->  [1,2]     // 2 + 7 = 9
numbers = [2,3,4],     target = 6   ->  [1,3]     // 2 + 4 = 6
numbers = [-1,0],      target = -1  ->  [1,2]     // -1 + 0 = -1
```

## Target

Time `O(n)`, space `O(1)`.

## Reference

https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/
