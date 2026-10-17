class Brute {
    /** transitive closure; a cycle exists iff some node reaches itself in >= 1 step */
    boolean isCyclic(int V, int[][] edges) {
        boolean[][] r = new boolean[V][V];
        for (int[] e : edges) r[e[0]][e[1]] = true;
        for (int k = 0; k < V; k++)
            for (int i = 0; i < V; i++)
                for (int j = 0; j < V; j++) if (r[i][k] && r[k][j]) r[i][j] = true;
        for (int i = 0; i < V; i++) if (r[i][i]) return true;
        return false;
    }
}
