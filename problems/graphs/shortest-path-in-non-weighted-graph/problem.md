# Shortest Path in Non-Weighted Graph

**Pattern:** Graphs  |  **Difficulty:** Medium (GFG)

An **undirected, unweighted** graph has `N` nodes labelled `0..N-1` and the given edge
list. Return an array `dist` of length `N` where `dist[i]` is the minimum number of
edges on a path from `src` to `i`, or `-1` if `i` is unreachable. `dist[src] = 0`.

No self-loops and no repeated edges.

## Constraints

- `1 <= N <= 10^4`
- `0 <= edges.length <= 10^4`
- `edges[i] = {u, v}`, `0 <= u, v < N`, `u != v`
- `0 <= src < N`

## Examples

**Example 1**

```
Input:  N = 9, edges = [[0,1],[0,3],[3,4],[4,5],[5,6],[1,2],[2,6],[6,7],[7,8],[6,8]], src = 0
Output: [0, 1, 2, 1, 2, 3, 3, 4, 4]
```

6 is reached in 3 steps via 0-1-2-6, shorter than 0-3-4-5-6 (4 steps).

**Example 2**

```
Input:  N = 4, edges = [[0,1],[1,2]], src = 0
Output: [0, 1, 2, -1]
```

Node 3 is isolated.

**Example 3**

```
Input:  N = 1, edges = [], src = 0
Output: [0]
```

Only the source.

## Target

Time `O(N + E)`, space `O(N + E)`.

## Reference

- https://www.geeksforgeeks.org/problems/shortest-path-in-undirected-graph-having-unit-distance/1
