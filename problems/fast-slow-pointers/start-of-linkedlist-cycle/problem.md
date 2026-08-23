# Start of LinkedList Cycle (medium)

**Pattern:** Fast & Slow Pointers  **Difficulty:** medium

## Problem

Given the head of a linked list, return the **node where the cycle begins**.
Return `null` if there is no cycle.

Return the actual node, not its value or index — the judge compares by identity,
so returning a new node with the right value will fail.

## Constraints

- `0 <= number of nodes <= 10^4`
- `-10^5 <= Node.val <= 10^5`
- **`O(1)` extra space**
- do not modify the list

## Examples

```
[3,2,0,-4], tail links to index 1  ->  the node at index 1 (val 2)
[1,2],      tail links to index 0  ->  the node at index 0 (val 1)
[1],        no cycle               ->  null
[1],        tail links to itself   ->  the node at index 0
```

## Target

Time `O(n)`, space `O(1)`.

Meeting the slow pointer is the easy half. The part worth deriving is *why*
restarting one pointer at the head lands exactly on the cycle entry.

## Reference

https://leetcode.com/problems/linked-list-cycle-ii/
