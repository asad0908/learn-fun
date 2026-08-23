# Problem Challenge 4: Words Concatenation (hard)

**Pattern:** Sliding Window  **Difficulty:** hard

## Problem

You are given a string `s` and an array of strings `words`, where every word
in `words` has the same length. Return the starting indices in `s` of every
substring that is a concatenation of **all** the words in `words`, each used
**exactly once**, in **any order**, with no characters in between.

Return the indices as a `List<Integer>`, in any order (there are no
duplicate indices). Return an empty list if no such substring exists.

## Constraints

- `1 <= s.length <= 10^4`
- `1 <= words.length <= 5000`
- `1 <= words[i].length <= 30`
- `s` and every `words[i]` consist of lowercase English letters
- All strings in `words` have the same length

## Examples

```
s = "barfoothefoobarman", words = ["foo","bar"]        ->  [0, 9]
    // s[0..5]="barfoo" and s[9..14]="foobar" are both concatenations of {foo, bar}

s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]  ->  []
    // no substring uses each of the two "word"s and one "good"/"best" exactly once

s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]  ->  [6, 9, 12]
```

## Target

Time `O(|s| * wordLen)`, space `O(words.length)`.

## Reference

https://leetcode.com/problems/substring-with-concatenation-of-all-words/
