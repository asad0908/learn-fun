class Brute {
    /** an edge lies on a cycle iff its endpoints stay connected without it */
    boolean isCycle(int V, int[][] edges) {
        for (int skip = 0; skip < edges.length; skip++) {
            boolean[] reach = new boolean[V];
            reach[edges[skip][0]] = true;
            boolean changed = true;
            while (changed) {
                changed = false;
                for (int k = 0; k < edges.length; k++) {
                    if (k == skip) continue;
                    int a = edges[k][0], b = edges[k][1];
                    if (reach[a] != reach[b]) { reach[a] = reach[b] = true; changed = true; }
                }
            }
            if (reach[edges[skip][1]]) return true;
        }
        return false;
    }
}
