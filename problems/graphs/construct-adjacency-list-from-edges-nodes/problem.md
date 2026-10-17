# Construct Adjacency List from Edges + Nodes

**Pattern:** Graphs  |  **Difficulty:** Easy (GFG)

Given `V` nodes labelled `0..V-1` and a list of **undirected** edges, return the
adjacency list: element `i` is the list of all neighbours of node `i`.

Return `List<List<Integer>>` of exactly `V` lists. Each neighbour list must be in
**ascending order** (the order the edges were given in is irrelevant). A node with
no edges gets an empty list. The graph has no self-loops and no duplicate edges.

## Constraints

- `1 <= V <= 10^4`
- `0 <= edges.length <= 10^4`
- `edges[i] = {u, v}` with `0 <= u, v < V`, `u != v`
- no edge appears twice (in either orientation)

## Examples

**Example 1**

```
Input:  V = 5, edges = [[0,1],[0,4],[4,1],[4,3],[1,3],[1,2],[3,2]]
Output: [[1, 4], [0, 2, 3, 4], [1, 3], [1, 2, 4], [0, 1, 3]]
```

Edges are undirected, so `0-1` puts 1 in 0's list and 0 in 1's list.

**Example 2**

```
Input:  V = 4, edges = [[0,3],[0,2]]
Output: [[2, 3], [], [0], [0]]
```

Node 1 is isolated: empty list.

**Example 3**

```
Input:  V = 1, edges = []
Output: [[]]
```

A single node, no edges.

## Target

Time `O(V + E log E)`, space `O(V + E)`.

## Reference

- https://www.geeksforgeeks.org/problems/print-adjacency-list-1587115620/1
