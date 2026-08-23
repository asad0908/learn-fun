# Longest Substring with K Distinct Characters (medium)

**Pattern:** Sliding Window  **Difficulty:** medium

## Problem

Given a string and an integer `k`, find the length of the longest substring
that contains no more than `k` **distinct** characters.

Return the length (an `int`), not the substring itself.

## Constraints

- `1 <= str.length <= 10^5`
- `str` consists of lowercase English letters
- `0 <= k <= 26`

## Examples

```
str = "araaci", k = 2   ->  4   // "araa" has 2 distinct characters: a, r
str = "araaci", k = 1   ->  2   // "aa" is the longest run of a single character
str = "cbbebi", k = 3   ->  5   // "cbbeb" and "bbebi" both have 3 distinct chars
```

## Target

Time `O(n)`, space `O(k)`.

## Reference

https://www.geeksforgeeks.org/problems/longest-k-unique-characters-substring0853/1
