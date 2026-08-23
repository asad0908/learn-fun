# Longest Subarray with Ones after Replacement (hard)

**Pattern:** Sliding Window  **Difficulty:** hard

## Problem

Given a binary array `nums` and an integer `k`, you may flip at most `k`
zeros to ones. Return the maximum number of consecutive `1`s achievable in
the array after doing so.

## Constraints

- `1 <= nums.length <= 10^5`
- `nums[i]` is `0` or `1`
- `0 <= k <= nums.length`

## Examples

```
nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2   ->  6   // flip the two 0's in "000" plus the run of 1's before it
nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3  ->  10
nums = [0,0,0,0], k = 0   ->  0   // no flips allowed, no 1's present
```

## Target

Time `O(n)`, space `O(1)`.

## Reference

https://leetcode.com/problems/max-consecutive-ones-iii/
