# Maximum Sum Subarray of Size K (easy)

**Pattern:** Sliding Window  **Difficulty:** easy

## Problem

Given an array of positive integers and a positive integer `k`, find the
maximum sum of any contiguous subarray of size exactly `k`.

Return the sum (an `int`), not the subarray itself.

## Constraints

- `1 <= arr.length <= 10^5`
- `1 <= arr[i] <= 10^5`
- `1 <= k <= arr.length`

## Examples

```
arr = [2,1,5,1,3,2], k = 3   ->  9   // subarray [5,1,3]
arr = [2,3,4,1,5],   k = 2   ->  7   // subarray [3,4]
arr = [1,1,1,1,1],   k = 5   ->  5   // only one subarray of size 5: the whole array
```

## Target

Time `O(n)`, space `O(1)`.

## Reference

https://www.geeksforgeeks.org/problems/max-sum-subarray-of-size-k5313/1
