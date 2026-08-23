# Minimum Size Subarray Sum

**Pattern:** Sliding Window  **Difficulty:** unknown (treat as medium)

## Problem

Given an integer array `nums` and an integer `target`, find the length of
the shortest contiguous subarray whose sum is greater than or equal to
`target`. Return `0` if no such subarray exists.

## Constraints

- `1 <= target <= 10^9`
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^4`

## Examples

```
target = 7, nums = [2,3,1,2,4,3]   ->  2   // [4,3] sums to 7
target = 4, nums = [1,4,4]         ->  1   // [4] alone already meets the target
target = 11, nums = [1,1,1,1,1,1,1,1]  ->  0   // total sum is 8, target unreachable
```

## Target

Time `O(n)`, space `O(1)`.

## Reference

https://leetcode.com/problems/minimum-size-subarray-sum/
