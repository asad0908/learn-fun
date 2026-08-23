# Triplet Sum to Zero (medium)

**Pattern:** Two Pointers  **Difficulty:** medium

## Problem

Given an integer array `nums`, return **all unique triplets**
`[nums[i], nums[j], nums[k]]` with `i != j != k` that sum to zero.

- The triplets are returned as **values**, not indices.
- No triplet may be repeated. `[-1,0,1]` and `[0,1,-1]` are the same triplet.
- The order of the triplets, and the order within a triplet, does not matter —
  the judge compares them as a set.

## Constraints

- `0 <= nums.length <= 3000` (return an empty list for fewer than 3 elements)
- `-10^5 <= nums[i] <= 10^5`
- `nums` is **not** sorted

## Examples

```
nums = [-1,0,1,2,-1,-4]  ->  [[-1,-1,2], [-1,0,1]]
nums = [0,1,1]           ->  []
nums = [0,0,0]           ->  [[0,0,0]]
nums = [0,0,0,0]         ->  [[0,0,0]]        // still only one unique triplet
```

## Target

Time `O(n^2)`, space `O(1)` beyond the output (sorting the input is allowed).

## Reference

https://leetcode.com/problems/3sum/
