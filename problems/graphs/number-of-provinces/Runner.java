public class Runner {
    static int[][] sym(Gen g, int n, int pct) {
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            m[i][i] = 1;
            for (int k = i + 1; k < n; k++) m[i][k] = m[k][i] = g.intIn(1, 100) <= pct ? 1 : 0;
        }
        return m;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Number of Provinces");
        Solution s = new Solution();

        j.expect("example 1", 2, s.findCircleNum(new int[][]{{1,1,0},{1,1,0},{0,0,1}}));
        j.expect("example 2", 3, s.findCircleNum(new int[][]{{1,0,0},{0,1,0},{0,0,1}}));
        j.expect("example 3: indirect link", 1, s.findCircleNum(new int[][]{{1,1,0},{1,1,1},{0,1,1}}));
        j.expect("edge: single city", 1, s.findCircleNum(new int[][]{{1}}));
        j.expect("edge: fully connected", 1, s.findCircleNum(new int[][]{{1,1,1},{1,1,1},{1,1,1}}));
        j.expect("edge: two pairs", 2, s.findCircleNum(new int[][]{{1,0,1,0},{0,1,0,1},{1,0,1,0},{0,1,0,1}}));
        j.expect("edge: long chain via high index", 1,
                s.findCircleNum(new int[][]{{1,0,0,1},{0,1,1,0},{0,1,1,1},{1,0,1,1}}));

        j.stress("random symmetric matrices vs brute", 3000, g -> {
            int[][] m = sym(g, g.intIn(1, 9), g.pick(10, 25, 50));
            return Judge.diff(Fmt.show(m), new Brute().findCircleNum(Fmt.copy(m)),
                    s.findCircleNum(Fmt.copy(m)));
        });

        int[][] big = sym(new Gen(3), 200, 1);
        j.perf("n=200", 1000, () -> s.findCircleNum(Fmt.copy(big)));
        j.finish();
    }
}
