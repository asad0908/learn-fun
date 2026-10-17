# Number of Provinces

**Pattern:** Graphs  |  **Difficulty:** Medium (LeetCode 547)

There are `n` cities. `isConnected[i][j] == 1` means city `i` and city `j` are
directly connected; `0` means they are not. Connectivity is transitive through other
cities: a **province** is a maximal group of cities that are directly or indirectly
connected. Return the number of provinces.

## Constraints

- `1 <= n <= 200`
- `isConnected[i][j]` is `0` or `1`
- `isConnected[i][i] == 1`
- `isConnected[i][j] == isConnected[j][i]`

## Examples

**Example 1**

```
Input:  isConnected = [[1,1,0],[1,1,0],[0,0,1]]
Output: 2
```

Cities 0 and 1 form one province, city 2 another.

**Example 2**

```
Input:  isConnected = [[1,0,0],[0,1,0],[0,0,1]]
Output: 3
```

No direct connections.

**Example 3**

```
Input:  isConnected = [[1,1,0],[1,1,1],[0,1,1]]
Output: 1
```

0-1 and 1-2 connect 0 to 2 indirectly.

## Target

Time `O(n^2)`, space `O(n)`.

## Reference

- https://leetcode.com/problems/number-of-provinces/description/
