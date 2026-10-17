# Surrounded Regions

**Pattern:** Graphs  |  **Difficulty:** Medium (LeetCode 130)

`board` is an `m x n` grid of `'X'` and `'O'`. A group of connected `'O'` cells
(4-directional) is **surrounded** if none of its cells lies on the border of the board
and none touches an `'O'` that does. Capture every surrounded region by flipping all
its `'O'` cells to `'X'`.

The method returns nothing: modify `board` **in place**. `'O'` cells connected to the
border stay `'O'`.

## Constraints

- `1 <= m, n <= 200`
- `board[i][j]` is `'X'` or `'O'`

## Examples

**Example 1**

```
Input:  board = ["XXXX","XOOX","XXOX","XOXX"]
Output: ["XXXX","XXXX","XXXX","XOXX"]
```

The three inner O's are enclosed; the bottom O is on the border so it survives.

**Example 2**

```
Input:  board = ["X"]
Output: ["X"]
```

Nothing to capture.

**Example 3**

```
Input:  board = ["OOO","OXO","OOO"]
Output: ["OOO","OXO","OOO"]
```

Every O touches the border.

## Target

Time `O(m*n)`, space `O(m*n)` worst case.

## Reference

- https://leetcode.com/problems/surrounded-regions/
