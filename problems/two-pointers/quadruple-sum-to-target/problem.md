# Quadruple Sum to Target (medium)

**Pattern:** Two Pointers  **Difficulty:** medium

## Problem

Given an integer array `nums` and an integer `target`, return **all unique
quadruplets** `[a, b, c, d]` of values from four **distinct indices** such that
`a + b + c + d == target`.

- Quadruplets are returned as values, not indices.
- No quadruplet may be repeated (as a multiset of values).
- Order of the quadruplets, and order within each quadruplet, does not matter.

## Constraints

- `0 <= nums.length <= 200`
- `-10^9 <= nums[i] <= 10^9`
- `-10^9 <= target <= 10^9`
- **the sum of four elements can overflow a 32-bit `int`**

## Examples

```
nums = [1,0,-1,0,-2,2], target = 0
  ->  [[-2,-1,1,2], [-2,0,0,2], [-1,0,0,1]]

nums = [2,2,2,2,2], target = 8   ->  [[2,2,2,2]]
nums = [1,2,3],     target = 6   ->  []      // fewer than 4 elements
```

## Target

Time `O(n^3)`, space `O(1)` beyond the output (sorting is allowed).

## Reference

https://leetcode.com/problems/4sum/
