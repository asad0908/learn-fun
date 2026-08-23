# LinkedList Cycle (easy)

**Pattern:** Fast & Slow Pointers  **Difficulty:** easy

## Problem

Given the head of a linked list, return `true` if the list contains a cycle.

A cycle exists if some node can be reached again by continuously following
`next`. You are not told where the cycle starts — only whether one exists.

## Constraints

- `0 <= number of nodes <= 10^4`
- `-10^5 <= Node.val <= 10^5`
- **`O(1)` extra space** — the `HashSet` answer is the brute force here, so beat it

## Examples

```
[3,2,0,-4], tail links to index 1  ->  true
[1,2],      tail links to index 0  ->  true
[1],        no cycle               ->  false
[],         empty                  ->  false
[1],        tail links to itself   ->  true
```

## Target

Time `O(n)`, space `O(1)`.

## Reference

https://leetcode.com/problems/linked-list-cycle/
