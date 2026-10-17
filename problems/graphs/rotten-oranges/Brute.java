class Brute {
    /** simulate literally: one full-grid sweep per minute */
    int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] g = new int[m][];
        for (int i = 0; i < m; i++) g[i] = grid[i].clone();
        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
        int minutes = 0;
        while (true) {
            int[][] next = new int[m][];
            for (int i = 0; i < m; i++) next[i] = g[i].clone();
            boolean changed = false;
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++) {
                    if (g[i][j] != 1) continue;
                    for (int d = 0; d < 4; d++) {
                        int r = i + dr[d], c = j + dc[d];
                        if (r >= 0 && c >= 0 && r < m && c < n && g[r][c] == 2) {
                            next[i][j] = 2;
                            changed = true;
                        }
                    }
                }
            if (!changed) break;
            g = next;
            minutes++;
        }
        for (int[] row : g) for (int v : row) if (v == 1) return -1;
        return minutes;
    }
}
