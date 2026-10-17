class Brute {
    /** try every one of the 2^n colourings */
    boolean isBipartite(int[][] graph) {
        int n = graph.length;
        for (int mask = 0; mask < (1 << n); mask++) {
            boolean ok = true;
            for (int u = 0; u < n && ok; u++)
                for (int v : graph[u])
                    if (((mask >> u) & 1) == ((mask >> v) & 1)) { ok = false; break; }
            if (ok) return true;
        }
        return false;
    }
}
