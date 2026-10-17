# Graph DFS

**Pattern:** Graphs  |  **Difficulty:** Easy (GFG)

`adj` is the adjacency list of a graph with `V = adj.size()` nodes labelled
`0..V-1`; `adj.get(u)` lists the neighbours of `u` **in the order they must be
tried**.

Return the nodes in the order a **recursive depth-first traversal starting at node 0**
first visits them (pre-order): when at `u`, scan `adj.get(u)` left to right and
recurse into each neighbour not yet visited. Only nodes reachable from 0 appear.

## Constraints

- `1 <= V <= 10^4`
- `0 <= total edges <= 10^4`
- the order inside each `adj.get(u)` is part of the input and must be respected

## Examples

**Example 1**

```
Input:  adj = [[2,3,1],[0],[0,4],[0],[2]]
Output: [0, 2, 4, 3, 1]
```

From 0 go to 2 first, then 2's unvisited neighbour 4; backtrack to 0, then 3, then 1.

**Example 2**

```
Input:  adj = [[1,2],[0,2],[0,1,3,4],[2],[2]]
Output: [0, 1, 2, 3, 4]
```

0 -> 1 -> 2 -> 3, back to 2, then 4.

**Example 3**

```
Input:  adj = [[]]
Output: [0]
```

A single node.

## Target

Time `O(V + E)`, space `O(V)`.

## Reference

- https://www.geeksforgeeks.org/problems/depth-first-traversal-for-a-graph/1
