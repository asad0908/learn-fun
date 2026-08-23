# Problem Challenge 1: Permutation in a String (hard)

**Pattern:** Sliding Window  **Difficulty:** hard

## Problem

Given two strings `s1` and `s2`, return `true` if `s2` contains a
contiguous substring that is a permutation of `s1` (i.e. some rearrangement
of exactly `s1`'s characters), `false` otherwise.

## Constraints

- `1 <= s1.length, s2.length <= 10^4`
- `s1` and `s2` consist of lowercase English letters only

## Examples

```
s1 = "ab", s2 = "eidbaooo"   ->  true   // "ba" at index 3-4 is a permutation of "ab"
s1 = "ab", s2 = "eidboaoo"   ->  false  // no contiguous substring rearranges to "ab"
s1 = "adc", s2 = "dcda"      ->  true   // "dca" at index 1-3 is a permutation of "adc"
```

## Target

Time `O(|s2|)`, space `O(1)` (fixed 26-letter alphabet).

## Reference

https://leetcode.com/problems/permutation-in-string/
