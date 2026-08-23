# No-repeat Substring (hard)

**Pattern:** Sliding Window  **Difficulty:** hard

## Problem

Given a string `s`, find the length of the longest substring that contains
no repeating characters.

Return the length (an `int`), not the substring itself.

## Constraints

- `0 <= s.length <= 10^5`
- `s` consists of English letters, digits, symbols, and spaces

## Examples

```
s = "abcabcbb"  ->  3   // "abc" is the longest run with no repeats
s = "bbbbb"     ->  1   // "b" -- every character repeats immediately
s = "pwwkew"    ->  3   // "wke" -- note "pwke" is not contiguous, so it doesn't count
```

## Target

Time `O(n)`, space `O(min(n, charset size))`.

## Reference

https://leetcode.com/problems/longest-substring-without-repeating-characters/
