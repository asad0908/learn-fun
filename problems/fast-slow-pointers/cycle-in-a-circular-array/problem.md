# Cycle in a Circular Array (hard)

**Pattern:** Fast & Slow Pointers  **Difficulty:** hard

## Problem

You are given a circular array `nums` of non-zero integers. At index `i`, move
`nums[i]` steps forward if positive, or `nums[i]` steps backward if negative.
The array wraps around at both ends.

Return `true` if `nums` contains a cycle, where a cycle must satisfy **both**:

- its **length is strictly greater than 1** (so a single index pointing at
  itself does not count), and
- **every move in it goes the same direction** — a cycle mixing forward and
  backward moves does not count.

You may modify `nums`.

## Constraints

- `1 <= nums.length <= 5000`
- `-1000 <= nums[i] <= 1000`
- `nums[i] != 0`

## Examples

```
nums = [2,-1,1,2,2]        ->  true
      // 0 -> 2 -> 3 -> 0, all forward, length 3

nums = [-1,-2,-3,-4,-5,6]  ->  false
      // the only cycle mixes directions

nums = [1,-1,5,1,4]        ->  true
      // 3 -> 4 -> 3, both forward, length 2

nums = [-1,2]              ->  false
      // 0 -> 1 -> 0 mixes a backward and a forward move

nums = [1]                 ->  false
      // 0 -> 0 is length 1
```

## Target

Time `O(n)`, space `O(1)`.

Two traps make this hard: a length-1 self loop must be rejected, and a walk that
changes direction must be abandoned rather than followed.

## Reference

https://leetcode.com/problems/circular-array-loop/
