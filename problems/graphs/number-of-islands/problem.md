# Number of Islands

**Pattern:** Graphs  |  **Difficulty:** Medium (LeetCode 200)

`grid` is an `m x n` grid of characters `'1'` (land) and `'0'` (water). An island is a
maximal group of land cells connected **horizontally or vertically** (diagonals do
not connect). Return the number of islands.

The input grid may be modified.

## Constraints

- `1 <= m, n <= 300`
- `grid[i][j]` is `'0'` or `'1'`

## Examples

**Example 1**

```
Input:  grid = ["11110","11010","11000","00000"]
Output: 1
```

All land cells are connected.

**Example 2**

```
Input:  grid = ["11000","11000","00100","00011"]
Output: 3
```

Top-left block, the middle cell, the bottom-right pair.

**Example 3**

```
Input:  grid = ["101","010","101"]
Output: 5
```

Diagonal neighbours are not connected.

## Target

Time `O(m*n)`, space `O(m*n)` worst case.

## Reference

- https://leetcode.com/problems/number-of-islands/description/
