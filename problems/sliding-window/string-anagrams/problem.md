# Problem Challenge 2: String Anagrams (hard)

**Pattern:** Sliding Window  **Difficulty:** hard

## Problem

Given a string `s` and a string `p`, return the starting indices of every
substring of `s` that is an anagram of `p` (a rearrangement using exactly
`p`'s characters).

Return the indices as a `List<Integer>`, sorted in ascending order (there is
exactly one valid ordering since indices are unique).

## Constraints

- `1 <= s.length, p.length <= 3 * 10^4`
- `s` and `p` consist of lowercase English letters only

## Examples

```
s = "cbaebabacd", p = "abc"   ->  [0, 6]      // "cba" at 0, "bac" at 6
s = "abab",       p = "ab"    ->  [0, 1, 2]   // "ab", "ba", "ab"
s = "a",          p = "b"     ->  []          // no anagram of "b" exists in "a"
```

## Target

Time `O(|s|)`, space `O(1)` (fixed 26-letter alphabet).

## Reference

https://leetcode.com/problems/find-all-anagrams-in-a-string/
