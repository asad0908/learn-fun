class Brute {
    /** try effort limits 0, 1, 2, ...: flood-fill using only steps within the limit
     *  and return the first limit that reaches the destination */
    int minimumEffortPath(int[][] h) {
        int m = h.length, n = h[0].length;
        for (int limit = 0; ; limit++) {
            boolean[][] seen = new boolean[m][n];
            seen[0][0] = true;
            boolean changed = true;
            int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
            while (changed) {
                changed = false;
                for (int i = 0; i < m; i++)
                    for (int j = 0; j < n; j++) {
                        if (!seen[i][j]) continue;
                        for (int d = 0; d < 4; d++) {
                            int r = i + dr[d], c = j + dc[d];
                            if (r < 0 || c < 0 || r >= m || c >= n || seen[r][c]) continue;
                            if (Math.abs(h[i][j] - h[r][c]) <= limit) { seen[r][c] = true; changed = true; }
                        }
                    }
            }
            if (seen[m - 1][n - 1]) return limit;
        }
    }
}
