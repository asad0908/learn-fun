# Longest Substring with Same Letters after Replacement (hard)

**Pattern:** Sliding Window  **Difficulty:** hard

## Problem

Given a string `s` and an integer `k`, you may replace up to `k` characters
of `s` with any other uppercase English letter. Return the length of the
longest substring you can make consist of a single repeated letter after
performing at most `k` replacements.

## Constraints

- `1 <= s.length <= 10^5`
- `s` consists of only uppercase English letters
- `0 <= k <= s.length`

## Examples

```
s = "ABAB", k = 2   ->  4   // replace both B's with A (or vice versa): "AAAA"
s = "AABABBA", k = 1   ->  4   // replace the middle A in "ABBA" (indices 3-6) with B: "BBBB"
s = "ABCDE", k = 1   ->  2   // one replacement can only fix one boundary between two letters
```

## Target

Time `O(n)`, space `O(1)` (fixed 26-letter alphabet).

## Reference

https://leetcode.com/problems/longest-repeating-character-replacement/
