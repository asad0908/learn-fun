# Bipartite Graph / Graph Coloring

**Pattern:** Graphs  |  **Difficulty:** Medium (LeetCode 785)

An undirected graph has `n` nodes labelled `0..n-1`, given as `graph[u]` = the
neighbours of `u`. The graph may be disconnected. It is **bipartite** if its nodes
can be split into two sets so that every edge joins a node of one set to a node of the
other. Return `true` if it is bipartite.

The graph is symmetric (`v` in `graph[u]` iff `u` in `graph[v]`), has no self-loops and
no parallel edges.

## Constraints

- `1 <= n <= 100`
- `0 <= graph[u].length < n`
- `graph[u]` has distinct values, none equal to `u`

## Examples

**Example 1**

```
Input:  graph = [[1,2,3],[0,2],[0,1,3],[0,2]]
Output: false
```

0-1-2 is a triangle; an odd cycle cannot be 2-coloured.

**Example 2**

```
Input:  graph = [[1,3],[0,2],[1,3],[0,2]]
Output: true
```

Sets {0,2} and {1,3}.

**Example 3**

```
Input:  graph = [[],[]]
Output: true
```

No edges at all.

## Target

Time `O(n + E)`, space `O(n)`.

## Reference

- https://leetcode.com/problems/is-graph-bipartite/
