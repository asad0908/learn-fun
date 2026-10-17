class Brute {
    /** try every subset of exactly V-1 edges; keep the cheapest one that connects everything */
    int spanningTree(int V, int[][] edges) {
        int m = edges.length, best = Integer.MAX_VALUE;
        for (int mask = 0; mask < (1 << m); mask++) {
            if (Integer.bitCount(mask) != V - 1) continue;
            int[] p = new int[V];
            for (int i = 0; i < V; i++) p[i] = i;
            int cost = 0, joined = 0;
            for (int i = 0; i < m; i++) {
                if ((mask >> i & 1) == 0) continue;
                int a = find(p, edges[i][0]), b = find(p, edges[i][1]);
                if (a != b) { p[a] = b; joined++; }
                cost += edges[i][2];
            }
            if (joined == V - 1) best = Math.min(best, cost);
        }
        return best;
    }

    private int find(int[] p, int x) {
        while (p[x] != x) x = p[x];
        return x;
    }
}
