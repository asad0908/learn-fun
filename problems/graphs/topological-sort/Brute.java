class Brute {
    /** lexicographically smallest valid order: repeatedly place the smallest node
     *  whose predecessors are all placed */
    int[] topoSort(int V, int[][] edges) {
        boolean[] placed = new boolean[V];
        int[] out = new int[V];
        for (int k = 0; k < V; k++) {
            for (int v = 0; v < V; v++) {
                if (placed[v]) continue;
                boolean ready = true;
                for (int[] e : edges) if (e[1] == v && !placed[e[0]]) ready = false;
                if (ready) { placed[v] = true; out[k] = v; break; }
            }
        }
        return out;
    }
}
