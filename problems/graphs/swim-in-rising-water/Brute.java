class Brute {
    /** raise the water one unit at a time and flood-fill from the start each time */
    int swimInWater(int[][] grid) {
        int n = grid.length;
        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
        for (int t = 0; ; t++) {
            if (grid[0][0] > t) continue;
            boolean[][] seen = new boolean[n][n];
            seen[0][0] = true;
            boolean changed = true;
            while (changed) {
                changed = false;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) {
                        if (!seen[i][j]) continue;
                        for (int d = 0; d < 4; d++) {
                            int r = i + dr[d], c = j + dc[d];
                            if (r < 0 || c < 0 || r >= n || c >= n || seen[r][c] || grid[r][c] > t) continue;
                            seen[r][c] = true;
                            changed = true;
                        }
                    }
            }
            if (seen[n - 1][n - 1]) return t;
        }
    }
}
