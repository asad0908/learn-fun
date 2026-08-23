# Minimum Window Sort (medium)

**Pattern:** Two Pointers  **Difficulty:** medium

## Problem

Given an integer array `nums`, find the **shortest contiguous subarray** such
that sorting just that subarray makes the whole array sorted in non-decreasing
order.

Return the **length** of that subarray. If `nums` is already sorted, return `0`.

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^5 <= nums[i] <= 10^5`

## Examples

```
nums = [2,6,4,8,10,9,15]  ->  5    // sort [6,4,8,10,9]
nums = [1,2,3,4]          ->  0    // already sorted
nums = [1]                ->  0
nums = [3,2,1]            ->  3    // the whole array
nums = [1,3,2,2,2]        ->  4    // sort [3,2,2,2]
```

## Target

Time `O(n)`, space `O(1)`. (The `O(n log n)` sort-a-copy approach is the brute
force — beat it.)

## Reference

https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
