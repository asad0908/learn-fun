# Rearrange 0 and 1

**Pattern:** Two Pointers  **Difficulty:** easy

## Problem

Given an array `arr` containing only `0`s and `1`s, rearrange it **in place** so
that every `0` comes before every `1`.

Modify `arr` directly; return nothing.

## Constraints

- `1 <= arr.length <= 10^6`
- `arr[i]` is `0` or `1`
- Must be done in place with `O(1)` extra space, in a single pass

## Examples

```
arr = [0,1,0,1,0,0,1,1,1,0]  ->  [0,0,0,0,0,1,1,1,1,1]
arr = [1,1,1,1]              ->  [1,1,1,1]
arr = [0]                    ->  [0]
```

## Target

Time `O(n)`, space `O(1)`, one pass.

## Reference

https://www.geeksforgeeks.org/problems/segregate-0s-and-1s5106/1
