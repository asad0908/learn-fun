# Triplets with Smaller Sum (medium)

**Pattern:** Two Pointers  **Difficulty:** medium

## Problem

Given an array `arr` and a value `target`, count the triplets of **distinct
indices** `i < j < k` such that

```
arr[i] + arr[j] + arr[k] < target
```

Return the count. Triplets are counted by index, so equal values at different
indices count separately.

## Constraints

- `0 <= arr.length <= 3000`
- `-10^4 <= arr[i] <= 10^4`
- `-10^4 <= target <= 10^4`
- the count fits in an `int`

## Examples

```
arr = [-1,0,2,3], target = 3  ->  2
      // (-1,0,2) = 1 < 3  and  (-1,0,3) = 2 < 3
arr = [-1,4,2,1,3], target = 5  ->  4
      // (-1,1,2) (-1,1,3) (-1,1,4) (-1,2,3)
arr = [1,1,1], target = 3  ->  0      // 3 is not < 3
```

## Target

Time `O(n^2)`, space `O(1)` (sorting the input is allowed).

## Reference

https://www.geeksforgeeks.org/problems/count-triplets-with-sum-smaller-than-x5549/1
