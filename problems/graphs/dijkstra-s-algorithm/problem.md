# Dijkstra's Algorithm

**Pattern:** Graphs  |  **Difficulty:** Medium (GFG)

An **undirected weighted** graph has `V` nodes labelled `0..V-1`; `edges[i] = {u, v, w}`
joins `u` and `v` with weight `w`. Return an array `dist` of length `V` where `dist[i]`
is the minimum total weight of a path from `src` to `i`, or `-1` if `i` is unreachable.
`dist[src] = 0`.

No self-loops and no repeated edges. All weights are positive.

## Constraints

- `1 <= V <= 10^4`
- `0 <= edges.length <= 10^5`
- `edges[i] = {u, v, w}`, `0 <= u, v < V`, `u != v`
- `1 <= w <= 1000`
- `0 <= src < V`

## Examples

**Example 1**

```
Input:  V = 3, edges = [[0,1,1],[1,2,3],[0,2,6]], src = 2
Output: [4, 3, 0]
```

From 2: to 1 costs 3, to 0 costs 3 + 1 = 4, cheaper than the direct edge of 6.

**Example 2**

```
Input:  V = 2, edges = [[0,1,9]], src = 0
Output: [0, 9]
```

A single edge.

**Example 3**

```
Input:  V = 3, edges = [[0,1,2]], src = 0
Output: [0, 2, -1]
```

Node 2 is unreachable.

## Target

Time `O((V + E) log V)`, space `O(V + E)`.

## Reference

- https://www.geeksforgeeks.org/problems/implementing-dijkstra-set-1-adjacency-matrix/1
