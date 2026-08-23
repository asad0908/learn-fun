# Squaring a Sorted Array (easy)

**Pattern:** Two Pointers  **Difficulty:** easy

## Problem

Given an integer array `nums` sorted in **non-decreasing** order, return a new
array containing the squares of each number, also sorted in non-decreasing order.

Note that `nums` may contain negative numbers, so the largest square can come
from either end.

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is sorted in non-decreasing order

## Examples

```
nums = [-4,-1,0,3,10]  ->  [0,1,9,16,100]
nums = [-7,-3,2,3,11]  ->  [4,9,9,49,121]
nums = [-5,-4,-2]      ->  [4,16,25]
```

## Target

Time `O(n)`, space `O(n)` for the output (do not sort).

## Reference

https://leetcode.com/problems/squares-of-a-sorted-array/
