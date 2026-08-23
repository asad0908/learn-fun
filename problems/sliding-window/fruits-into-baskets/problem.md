# Fruits into Baskets (medium)

**Pattern:** Sliding Window  **Difficulty:** medium

## Problem

You are visiting a farm with a row of fruit trees, given as `fruits`, where
`fruits[i]` is the fruit type produced by the `i`-th tree.

You have exactly two baskets, and each basket can hold only a single type of
fruit (unlimited quantity of that type). Starting from any tree of your
choice, you must pick exactly one fruit from every tree while moving to the
right, stopping the moment you'd need to pick a fruit type that fits in
neither basket.

Return the maximum number of fruits you can pick this way (an `int`).

## Constraints

- `1 <= fruits.length <= 10^5`
- `0 <= fruits[i] < fruits.length`

## Examples

```
fruits = [1,2,1]       ->  3   // both baskets cover types 1 and 2: take everything
fruits = [0,1,2,2]     ->  3   // pick starting at index 1: [1,2,2]
fruits = [1,2,3,2,2]   ->  4   // pick starting at index 1: [2,3,2,2]
```

## Target

Time `O(n)`, space `O(1)` (at most 2 distinct fruit types tracked at once).

## Reference

https://leetcode.com/problems/fruit-into-baskets/
