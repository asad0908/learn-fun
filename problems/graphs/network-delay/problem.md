# Network Delay

**Pattern:** Graphs  |  **Difficulty:** Medium (LeetCode 743)

A network has `n` nodes labelled `1..n` (**1-based**). `times[i] = {u, v, w}` is a
**directed** edge: a signal sent from `u` reaches `v` after `w` time units.

A signal is sent from node `k` at time 0. Return the time at which **all** `n` nodes
have received it, or `-1` if some node never receives it.

## Constraints

- `1 <= k <= n <= 100`
- `1 <= times.length <= 6000`
- `times[i] = {u, v, w}`, `1 <= u, v <= n`, `u != v`
- `0 <= w <= 100`
- all `(u, v)` pairs are distinct

## Examples

**Example 1**

```
Input:  times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
Output: 2
```

Node 2 reaches 1 and 3 at time 1; 4 at time 2.

**Example 2**

```
Input:  times = [[1,2,1]], n = 2, k = 1
Output: 1
```

One edge of weight 1.

**Example 3**

```
Input:  times = [[1,2,1]], n = 2, k = 2
Output: -1
```

Node 1 can never be reached from 2.

## Target

Time `O((n + E) log n)` (or `O(n * E)` with Bellman-Ford), space `O(n + E)`.

## Reference

- https://leetcode.com/problems/network-delay-time/
