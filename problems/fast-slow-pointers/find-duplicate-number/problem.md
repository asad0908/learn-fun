# Find Duplicate Number

**Pattern:** Fast & Slow Pointers  **Difficulty:** medium

## Problem

Given an array `nums` of `n + 1` integers where every value is in the range
`[1, n]`, exactly one value is repeated (possibly more than twice). Return that
repeated value.

## Constraints

- `nums.length == n + 1`, `1 <= n <= 10^5`
- `1 <= nums[i] <= n`
- exactly one value appears more than once
- **you must not modify the array** (no sorting, no marking) and must use
  `O(1)` extra space

## Examples

```
nums = [1,3,4,2,2]      ->  2
nums = [3,1,3,4,2]      ->  3
nums = [3,3,3,3,3]      ->  3
nums = [1,1]            ->  1
```

## Target

Time `O(n)`, space `O(1)`, input left untouched.

The pattern link: read `i -> nums[i]` as `next` pointers. Because every value is
in `[1, n]`, following them from index 0 must eventually cycle, and the entry to
that cycle is the duplicated value.

## Reference

https://leetcode.com/problems/find-the-duplicate-number/description/
