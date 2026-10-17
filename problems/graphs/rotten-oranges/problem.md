# Rotten Oranges

**Pattern:** Graphs  |  **Difficulty:** Medium (LeetCode 994)

`grid` is an `m x n` grid where each cell is `0` (empty), `1` (fresh orange) or
`2` (rotten orange). Every minute, each fresh orange that is 4-directionally adjacent
to a rotten orange becomes rotten (all at the same moment).

Return the minimum number of minutes until no fresh orange remains, or `-1` if that
is impossible. If there are no fresh oranges at the start, the answer is `0`.

## Constraints

- `1 <= m, n <= 10`
- `grid[i][j]` is `0`, `1` or `2`

## Examples

**Example 1**

```
Input:  grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
```

The rot spreads outward one step per minute; the far corner turns at minute 4.

**Example 2**

```
Input:  grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
```

The bottom-left orange is never adjacent to a rotten one.

**Example 3**

```
Input:  grid = [[0,2]]
Output: 0
```

There is nothing fresh to rot.

## Target

Time `O(m*n)`, space `O(m*n)`.

## Reference

- https://leetcode.com/problems/rotting-oranges/
