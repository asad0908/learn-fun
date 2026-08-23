# Dutch National Flag Problem (medium)

**Pattern:** Two Pointers  **Difficulty:** medium

## Problem

Given an array `nums` containing only the values `0`, `1` and `2`, sort it
**in place** so that all `0`s come first, then all `1`s, then all `2`s.

Modify `nums` directly; return nothing. You may not use a library sort, and you
may not count occurrences and overwrite (that is two passes — the point of this
problem is one pass).

## Constraints

- `1 <= nums.length <= 300`
- `nums[i]` is `0`, `1` or `2`
- one pass, `O(1)` extra space

## Examples

```
nums = [2,0,2,1,1,0]  ->  [0,0,1,1,2,2]
nums = [2,0,1]        ->  [0,1,2]
nums = [0]            ->  [0]
```

## Target

Time `O(n)` in a single pass, space `O(1)`.

## Reference

https://leetcode.com/problems/sort-colors/description/
