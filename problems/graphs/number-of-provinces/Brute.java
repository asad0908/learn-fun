class Brute {
    /** transitive closure (Floyd-Warshall), then count cities that no
     *  lower-numbered city can reach */
    int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[][] r = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) r[i][j] = i == j || isConnected[i][j] == 1;
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) if (r[i][k] && r[k][j]) r[i][j] = true;
        int count = 0;
        for (int i = 0; i < n; i++) {
            boolean first = true;
            for (int j = 0; j < i; j++) if (r[j][i]) first = false;
            if (first) count++;
        }
        return count;
    }
}
