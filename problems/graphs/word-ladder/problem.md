# Word Ladder

**Pattern:** Graphs  |  **Difficulty:** Hard (LeetCode 127)

A **transformation sequence** from `beginWord` to `endWord` is a list of words
`beginWord -> s1 -> s2 -> ... -> endWord` where every adjacent pair differs in exactly
**one letter** and every word after `beginWord` is in `wordList`. (`beginWord` itself
need not be in `wordList`.)

Return the number of **words** in the shortest such sequence (counting both
`beginWord` and `endWord`), or `0` if none exists.

## Constraints

- `1 <= beginWord.length <= 10`
- `endWord.length == beginWord.length`
- `1 <= wordList.length <= 5000`
- all words have the same length and use lowercase English letters
- `beginWord != endWord`
- words in `wordList` are unique

## Examples

**Example 1**

```
Input:  beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
```

hit -> hot -> dot -> dog -> cog.

**Example 2**

```
Input:  beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
```

`endWord` is not in `wordList`, so no valid sequence exists.

**Example 3**

```
Input:  beginWord = "a", endWord = "c", wordList = ["a","b","c"]
Output: 2
```

a -> c directly.

## Target

Time `O(N * L^2)` (or `O(N^2 * L)` pairwise), space `O(N * L)`; `N` = words, `L` = word length.

## Reference

- https://leetcode.com/problems/word-ladder/
