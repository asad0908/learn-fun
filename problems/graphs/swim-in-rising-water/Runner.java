public class Runner {
    static int[][] perm(Gen g, int n) {
        int[] p = g.permutation(n * n);
        int[][] m = new int[n][n];
        for (int i = 0; i < n * n; i++) m[i / n][i % n] = p[i];
        return m;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Swim in Rising Water");
        Solution s = new Solution();

        j.expect("example 1", 3, s.swimInWater(new int[][]{{0,2},{1,3}}));
        j.expect("example 2: spiral", 16, s.swimInWater(new int[][]{
                {0,1,2,3,4},{24,23,22,21,5},{12,13,14,15,16},{11,17,18,19,20},{10,9,8,7,6}}));
        j.expect("example 3: single cell", 0, s.swimInWater(new int[][]{{0}}));
        j.expect("edge: start is not the lowest cell", 3, s.swimInWater(new int[][]{{3,0},{1,2}}));
        j.expect("edge: destination is the maximum", 8,
                s.swimInWater(new int[][]{{0,1,2},{3,4,5},{6,7,8}}));
        j.expect("edge: start dominates", 8, s.swimInWater(new int[][]{{8,1,2},{3,4,5},{6,7,0}}));
        j.expect("edge: low shortcut through the middle", 4,
                s.swimInWater(new int[][]{{0,8,7},{1,2,6},{5,3,4}}));

        j.stress("random permutation grids vs brute", 3000, g -> {
            int[][] gr = perm(g, g.intIn(1, 5));
            return Judge.diff(Fmt.show(gr), new Brute().swimInWater(Fmt.copy(gr)),
                    s.swimInWater(Fmt.copy(gr)));
        });

        int[][] big = perm(new Gen(15), 50);
        j.perf("50x50", 1000, () -> s.swimInWater(Fmt.copy(big)));
        j.finish();
    }
}
