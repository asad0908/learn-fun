# Swim in Rising Water

**Pattern:** Graphs  |  **Difficulty:** Hard (LeetCode 778)

`grid` is an `n x n` grid; `grid[i][j]` is the elevation of cell `(i, j)`. At time `t`
the water depth everywhere is `t`. You may swim from a cell to a 4-directionally
adjacent cell only if **both** cells have elevation `<= t`; swimming itself takes no time.

You start at `(0, 0)` at time 0. Return the least time `t` at which you can reach
`(n-1, n-1)`.

## Constraints

- `1 <= n <= 50`
- `0 <= grid[i][j] < n^2`
- every value in `grid` is unique (a permutation of `0..n^2-1`)

## Examples

**Example 1**

```
Input:  grid = [[0,2],[1,3]]
Output: 3
```

The destination has elevation 3, so nothing earlier than t = 3 works.

**Example 2**

```
Input:  grid = [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
Output: 16
```

The route around the border has highest cell 16.

**Example 3**

```
Input:  grid = [[0]]
Output: 0
```

Already there.

## Target

Time `O(n^2 log n)`, space `O(n^2)`.

## Reference

- https://leetcode.com/problems/swim-in-rising-water/
