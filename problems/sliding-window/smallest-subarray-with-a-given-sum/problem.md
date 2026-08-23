# Smallest Subarray with a given sum (easy)

**Pattern:** Sliding Window  **Difficulty:** easy

## Problem

Given an array of positive integers and a positive integer `s`, find the
length of the smallest contiguous subarray whose sum is greater than or equal
to `s`. Return `0` if no such subarray exists.

## Constraints

- `1 <= arr.length <= 10^5`
- `1 <= arr[i] <= 10^4`
- `1 <= s <= 10^9`

## Examples

```
arr = [2,1,5,2,3,2], s = 7   ->  2   // [5,2] sums to 7
arr = [2,1,5,2,8],   s = 7   ->  1   // [8] alone already sums to 8 >= 7
arr = [3,4,1,1,6],    s = 8   ->  3   // [3,4,1] or [1,1,6], both length 3
```

## Target

Time `O(n)`, space `O(1)`.

## Reference

https://leetcode.com/problems/minimum-size-subarray-sum/
