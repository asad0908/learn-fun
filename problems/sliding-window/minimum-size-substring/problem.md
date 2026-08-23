# Minimum Size Substring (hard)

**Pattern:** Sliding Window  **Difficulty:** hard

## Problem

Given two strings `s` and `t`, find the shortest contiguous substring of `s`
that contains every character of `t`, including duplicates (i.e. if `t` has
two `'a'`s, the substring must contain at least two `'a'`s too). Extra
characters in the substring, and characters in any order, are fine.

Return that substring. If several substrings of the minimum length exist,
return any one of them. Return the empty string `""` if no such substring
exists.

## Constraints

- `1 <= s.length, t.length <= 10^5`
- `s` and `t` consist of uppercase and lowercase English letters

## Examples

```
s = "ADOBECODEBANC", t = "ABC"   ->  "BANC"   // shortest window containing A, B, and C
s = "a",  t = "a"    ->  "a"     // the whole string is the only window
s = "a",  t = "aa"   ->  ""      // s can never contain two a's
```

## Target

Time `O(|s| + |t|)`, space `O(charset size)`.

## Reference

https://leetcode.com/problems/minimum-window-substring/
