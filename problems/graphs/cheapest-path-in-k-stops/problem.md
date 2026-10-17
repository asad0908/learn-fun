# Cheapest Path in K Stops

**Pattern:** Graphs  |  **Difficulty:** Medium (LeetCode 787)

There are `n` cities `0..n-1` and **directed** flights `flights[i] = {from, to, price}`.
Return the cheapest total price of a trip from `src` to `dst` that makes **at most `k`
stops** (cities strictly between `src` and `dst`), i.e. uses at most `k + 1` flights.
Return `-1` if no such trip exists.

## Constraints

- `1 <= n <= 100`
- `0 <= flights.length <= n * (n - 1) / 2`
- `flights[i] = {from, to, price}`, `from != to`
- `1 <= price <= 10^4`
- no two flights share the same `(from, to)`
- `0 <= src, dst, k < n`, `src != dst`

## Examples

**Example 1**

```
Input:  n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
Output: 700
```

0 -> 1 -> 3 costs 100 + 600 with one stop. The cheaper 0-1-2-3 (400) needs two stops.

**Example 2**

```
Input:  n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
Output: 200
```

0 -> 1 -> 2.

**Example 3**

```
Input:  n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
Output: 500
```

No stops allowed: only the direct flight.

## Target

Time `O(k * E)`, space `O(n)`.

## Reference

- https://leetcode.com/problems/cheapest-flights-within-k-stops/description/
