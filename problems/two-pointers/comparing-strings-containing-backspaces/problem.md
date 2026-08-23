# Comparing Strings containing Backspaces (medium)

**Pattern:** Two Pointers  **Difficulty:** medium

## Problem

Given two strings `s` and `t`, return `true` if they are equal when both are
typed into an empty text editor, where `'#'` means **backspace**.

A backspace on empty text does nothing.

## Constraints

- `1 <= s.length, t.length <= 200`
- `s` and `t` contain lowercase letters and `'#'` only

## Examples

```
s = "ab#c",   t = "ad#c"   ->  true    // both become "ac"
s = "ab##",   t = "c#d#"   ->  true    // both become ""
s = "a#c",    t = "b"      ->  false   // "c" vs "b"
s = "###a",   t = "a"      ->  true    // backspace on empty is a no-op
```

## Target

Time `O(n + m)`, space `O(1)` — do it by scanning **from the back** rather than
building the result strings.

## Reference

https://leetcode.com/problems/backspace-string-compare/
