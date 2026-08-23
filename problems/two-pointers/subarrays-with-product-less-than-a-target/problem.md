# Subarrays with Product Less than a Target (medium)

**Pattern:** Two Pointers  **Difficulty:** medium

## Problem

Given an array of positive integers `nums` and an integer `k`, return the number
of **contiguous** subarrays whose product is **strictly less than** `k`.

## Constraints

- `1 <= nums.length <= 3 * 10^4`
- `1 <= nums[i] <= 1000`
- `0 <= k <= 10^6`
- the product of a window can exceed `int` — mind the overflow

## Examples

```
nums = [10,5,2,6], k = 100  ->  8
      // [10] [5] [2] [6] [10,5] [5,2] [2,6] [5,2,6]
nums = [1,2,3],    k = 0    ->  0      // nothing is < 0
nums = [1,1,1],    k = 2    ->  6      // every subarray has product 1
```

## Target

Time `O(n)`, space `O(1)`.

## Reference

https://leetcode.com/problems/subarray-product-less-than-k/
