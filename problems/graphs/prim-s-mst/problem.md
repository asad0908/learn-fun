# Prim's MST

**Pattern:** Graphs  |  **Difficulty:** Medium (GFG)

A **connected, undirected, weighted** graph has `V` nodes labelled `0..V-1`;
`edges[i] = {u, v, w}` joins `u` and `v` with weight `w`. Return the total weight of a
**minimum spanning tree** (the cheapest set of `V - 1` edges that connects every node).

Return the weight only, not the edges. No self-loops, no repeated edges.

## Constraints

- `2 <= V <= 1000`
- `V - 1 <= edges.length <= V * (V - 1) / 2`
- `edges[i] = {u, v, w}`, `0 <= u, v < V`, `u != v`
- `1 <= w <= 1000`
- the graph is connected

## Examples

**Example 1**

```
Input:  V = 3, edges = [[0,1,5],[1,2,3],[0,2,1]]
Output: 4
```

Take 0-2 (1) and 1-2 (3).

**Example 2**

```
Input:  V = 2, edges = [[0,1,5]]
Output: 5
```

The only edge.

**Example 3**

```
Input:  V = 4, edges = [[0,1,1],[1,2,1],[2,3,1],[0,3,10]]
Output: 3
```

The expensive closing edge is never needed.

## Target

Time `O(E log V)`, space `O(V + E)`.

## Reference

- https://www.geeksforgeeks.org/problems/minimum-spanning-tree/1
