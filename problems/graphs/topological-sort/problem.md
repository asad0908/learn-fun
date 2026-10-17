# Topological Sort

**Pattern:** Graphs  |  **Difficulty:** Medium (GFG)

A directed **acyclic** graph has `V` nodes labelled `0..V-1`; `edges[i] = {u, v}` is an
edge from `u` to `v`. Return an array containing every node exactly once such that
for every edge `u -> v`, `u` appears **before** `v`.

Many orders are usually valid; **any valid order is accepted**.

## Constraints

- `2 <= V <= 10^4`
- `1 <= edges.length <= 10^4`
- `edges[i] = {u, v}`, `0 <= u, v < V`
- the graph is a DAG: no cycles, no self-loops, no repeated edges

## Examples

**Example 1**

```
Input:  V = 6, edges = [[5,0],[5,2],[4,0],[4,1],[2,3],[3,1]]
Output: e.g. [5, 4, 2, 3, 1, 0]
```

5 before 0 and 2; 4 before 0 and 1; 2 before 3; 3 before 1. Other orders such as [4, 5, 2, 3, 1, 0] are also valid.

**Example 2**

```
Input:  V = 4, edges = [[3,0],[1,0],[2,0]]
Output: e.g. [3, 2, 1, 0]
```

0 must come last; 1, 2, 3 are free.

**Example 3**

```
Input:  V = 3, edges = [[0,1],[1,2]]
Output: [0, 1, 2]
```

A chain has exactly one valid order.

## Target

Time `O(V + E)`, space `O(V)`.

## Reference

- https://www.geeksforgeeks.org/problems/topological-sort/1
