class Brute {
    /** mark border O's as safe, then keep spreading "safe" to adjacent O's until stable */
    void solve(char[][] board) {
        int m = board.length, n = board[0].length;
        boolean[][] safe = new boolean[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] == 'O' && (i == 0 || j == 0 || i == m - 1 || j == n - 1)) safe[i][j] = true;
        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++) {
                    if (board[i][j] != 'O' || safe[i][j]) continue;
                    for (int d = 0; d < 4; d++) {
                        int r = i + dr[d], c = j + dc[d];
                        if (r >= 0 && c >= 0 && r < m && c < n && safe[r][c]) { safe[i][j] = true; changed = true; break; }
                    }
                }
        }
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) if (board[i][j] == 'O' && !safe[i][j]) board[i][j] = 'X';
    }
}
