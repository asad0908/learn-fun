class Brute {
    /** give every land cell its own label, then repeatedly adopt the smallest
     *  neighbouring label until nothing changes; count distinct labels */
    int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] lab = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) lab[i][j] = grid[i][j] == '1' ? i * n + j : -1;
        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++) {
                    if (lab[i][j] < 0) continue;
                    for (int d = 0; d < 4; d++) {
                        int r = i + dr[d], c = j + dc[d];
                        if (r < 0 || c < 0 || r >= m || c >= n || lab[r][c] < 0) continue;
                        if (lab[r][c] < lab[i][j]) { lab[i][j] = lab[r][c]; changed = true; }
                    }
                }
        }
        java.util.Set<Integer> labels = new java.util.HashSet<>();
        for (int[] row : lab) for (int v : row) if (v >= 0) labels.add(v);
        return labels.size();
    }
}
