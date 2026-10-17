# Cycle Detection in Undirected Graph

**Pattern:** Graphs  |  **Difficulty:** Medium (GFG)

An undirected graph has `V` nodes labelled `0..V-1` and an edge list. The graph may be
**disconnected**. Return `true` if it contains a cycle, otherwise `false`.

There are no self-loops and no repeated edges, so two nodes joined by a single edge
are **not** a cycle.

## Constraints

- `1 <= V <= 10^5`
- `0 <= edges.length <= 10^5`
- `edges[i] = {u, v}`, `0 <= u, v < V`, `u != v`
- no edge appears twice (in either orientation)

## Examples

**Example 1**

```
Input:  V = 4, edges = [[0,1],[0,2],[1,2],[2,3]]
Output: true
```

0-1-2-0 is a cycle.

**Example 2**

```
Input:  V = 4, edges = [[0,1],[1,2],[2,3]]
Output: false
```

A path has no cycle.

**Example 3**

```
Input:  V = 5, edges = [[0,1],[2,3],[3,4],[4,2]]
Output: true
```

The cycle lives in the component not containing node 0.

## Target

Time `O(V + E)`, space `O(V)`.

## Reference

- https://www.geeksforgeeks.org/problems/detect-cycle-in-an-undirected-graph/1
