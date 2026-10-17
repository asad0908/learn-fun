# Path With Minimum Effort

**Pattern:** Graphs  |  **Difficulty:** Medium (LeetCode 1631)

`heights` is an `rows x cols` grid. You start at the top-left cell `(0, 0)` and want to
reach the bottom-right cell `(rows-1, cols-1)`, moving up, down, left or right.

The **effort** of a route is the **maximum** absolute height difference between two
consecutive cells along it. Return the minimum effort over all routes.

## Constraints

- `1 <= rows, cols <= 100`
- `1 <= heights[i][j] <= 10^6`

## Examples

**Example 1**

```
Input:  heights = [[1,2,2],[3,8,2],[5,3,5]]
Output: 2
```

Route 1-3-5-3-5 has max difference 2; the shorter route through 8 is worse.

**Example 2**

```
Input:  heights = [[1,2,3],[3,8,4],[5,3,5]]
Output: 1
```

Route 1-2-3-4-5 changes by 1 each step.

**Example 3**

```
Input:  heights = [[7]]
Output: 0
```

Start is the destination.

## Target

Time `O(rows*cols*log(rows*cols))`, space `O(rows*cols)`.

## Reference

- https://leetcode.com/problems/path-with-minimum-effort/
