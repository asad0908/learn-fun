# Bellman Ford

**Pattern:** Graphs  |  **Difficulty:** Medium (GFG)

A **directed** graph has `V` nodes labelled `0..V-1`; `edges[i] = {u, v, w}` is an edge
`u -> v` of weight `w`. Weights may be **negative**.

Return an array `dist` of length `V`:
- `dist[i]` = minimum total weight of a path from `src` to `i`;
- `dist[i] = 100000000` (`10^8`) if `i` is unreachable from `src`;
- `dist[src] = 0`.

If a **negative-weight cycle is reachable from `src`**, shortest paths are undefined:
return the single-element array `{-1}` instead. A negative cycle that `src` cannot
reach is ignored.

## Constraints

- `1 <= V <= 500`
- `0 <= edges.length <= V * (V - 1)`
- `edges[i] = {u, v, w}`, `0 <= u, v < V`, `u != v`
- `-1000 <= w <= 1000`
- `0 <= src < V`
- no directed edge appears twice

## Examples

**Example 1**

```
Input:  V = 5, edges = [[1,3,2],[4,3,-1],[2,4,1],[1,2,1],[0,1,5]], src = 0
Output: [0, 5, 6, 6, 7]
```

Node 3 is cheapest via 0-1-2-4-3: 5 + 1 + 1 - 1 = 6, not via 1-3 directly (7).

**Example 2**

```
Input:  V = 4, edges = [[0,1,4],[1,2,-6],[2,3,5],[3,1,-2]], src = 0
Output: [-1]
```

The cycle 1 -> 2 -> 3 -> 1 weighs -6 + 5 - 2 = -3 and is reachable from 0.

**Example 3**

```
Input:  V = 3, edges = [[0,1,2]], src = 0
Output: [0, 2, 100000000]
```

Node 2 is unreachable.

## Target

Time `O(V * E)`, space `O(V)`.

## Reference

- https://www.geeksforgeeks.org/problems/distance-from-the-source-bellman-ford-algorithm/1
