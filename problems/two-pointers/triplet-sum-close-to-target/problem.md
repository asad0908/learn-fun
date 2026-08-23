# Triplet Sum Close to Target (medium)

**Pattern:** Two Pointers  **Difficulty:** medium

## Problem

Given an integer array `nums` of length at least 3 and an integer `target`, find
the three integers whose sum is **closest** to `target`.

Return that **sum** (not the triplet, not the indices).

If two different sums are equally close to `target`, either is accepted.

## Constraints

- `3 <= nums.length <= 500`
- `-1000 <= nums[i] <= 1000`
- `-10^4 <= target <= 10^4`
- `nums` is **not** sorted

## Examples

```
nums = [-1,2,1,-4], target = 1   ->  2      // -1 + 2 + 1 = 2
nums = [0,0,0],     target = 1   ->  0
nums = [4,0,5,-5,3,3,0,-4,-5], target = -2  ->  -2   // exact hit
```

## Target

Time `O(n^2)`, space `O(1)` (sorting the input is allowed).

## Reference

https://leetcode.com/problems/3sum-closest/
