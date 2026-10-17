# Cycle Detection in Directed Graph

**Pattern:** Graphs  |  **Difficulty:** Medium (GFG)

A directed graph has `V` nodes labelled `0..V-1`; `edges[i] = {u, v}` is an edge
from `u` to `v`. The graph may be disconnected. Return `true` if it contains a
directed cycle, otherwise `false`.

There are no self-loops and no repeated edges. Note that two opposite edges
`u -> v` and `v -> u` **do** form a cycle.

## Constraints

- `1 <= V <= 10^5`
- `0 <= edges.length <= 10^5`
- `edges[i] = {u, v}`, `0 <= u, v < V`, `u != v`
- no directed edge appears twice

## Examples

**Example 1**

```
Input:  V = 4, edges = [[0,1],[1,2],[2,3],[3,1]]
Output: true
```

1 -> 2 -> 3 -> 1.

**Example 2**

```
Input:  V = 4, edges = [[0,1],[1,2],[2,3]]
Output: false
```

A directed path.

**Example 3**

```
Input:  V = 3, edges = [[0,1],[0,2],[1,2]]
Output: false
```

Node 2 is reached twice, but no path leads back.

## Target

Time `O(V + E)`, space `O(V)`.

## Reference

- https://www.geeksforgeeks.org/problems/detect-cycle-in-a-directed-graph/1
