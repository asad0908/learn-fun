# Remove Duplicates (easy)

**Pattern:** Two Pointers  **Difficulty:** easy

## Problem

Given an integer array `nums` sorted in **non-decreasing** order, remove the
duplicates **in place** so that each distinct value appears exactly once, keeping
the relative order.

Return `k`, the number of distinct elements. The first `k` slots of `nums` must
hold those distinct values in sorted order. Whatever sits beyond index `k-1` does
not matter.

## Constraints

- `1 <= nums.length <= 3 * 10^4`
- `-100 <= nums[i] <= 100`
- `nums` is sorted in non-decreasing order
- `O(1)` extra space

## Examples

```
nums = [1,1,2]              ->  k = 2, nums starts [1,2]
nums = [0,0,1,1,1,2,2,3,3,4]->  k = 5, nums starts [0,1,2,3,4]
nums = [7]                  ->  k = 1, nums starts [7]
```

## Target

Time `O(n)`, space `O(1)`.

## Extra reps (same idea, different container)

- https://leetcode.com/problems/remove-duplicates-from-sorted-list/
- https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/ (keep at most 2)

## Reference

https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/
