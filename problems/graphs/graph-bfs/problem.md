# Graph BFS

**Pattern:** Graphs  |  **Difficulty:** Easy (GFG)

`adj` is the adjacency list of a graph with `V = adj.size()` nodes labelled
`0..V-1`; `adj.get(u)` lists the neighbours of `u` **in the order they must be
tried**.

Return the nodes in the order a **breadth-first traversal starting at node 0** visits
them: pop a node from the queue, scan its list left to right and enqueue every
neighbour not yet seen. Only nodes reachable from 0 appear.

## Constraints

- `1 <= V <= 10^4`
- `0 <= total edges <= 10^4`
- the order inside each `adj.get(u)` is part of the input and must be respected

## Examples

**Example 1**

```
Input:  adj = [[1,2,3],[],[4],[],[]]
Output: [0, 1, 2, 3, 4]
```

Level 1 is 1,2,3; level 2 is 4 (child of 2).

**Example 2**

```
Input:  adj = [[2,1],[0],[0]]
Output: [0, 2, 1]
```

0's list says 2 before 1.

**Example 3**

```
Input:  adj = [[]]
Output: [0]
```

A single node.

## Target

Time `O(V + E)`, space `O(V)`.

## Reference

- https://www.geeksforgeeks.org/problems/bfs-traversal-of-graph/1
