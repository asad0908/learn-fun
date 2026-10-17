public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Path With Minimum Effort");
        Solution s = new Solution();

        j.expect("example 1", 2, s.minimumEffortPath(new int[][]{{1,2,2},{3,8,2},{5,3,5}}));
        j.expect("example 2", 1, s.minimumEffortPath(new int[][]{{1,2,3},{3,8,4},{5,3,5}}));
        j.expect("example 3: single cell", 0, s.minimumEffortPath(new int[][]{{7}}));
        j.expect("example 4: winding zero-effort path", 0,
                s.minimumEffortPath(new int[][]{{1,2,1,1,1},{1,2,1,2,1},{1,2,1,2,1},{1,2,1,2,1},{1,1,1,2,1}}));
        j.expect("edge: single row", 4, s.minimumEffortPath(new int[][]{{1,2,6,3}}));
        j.expect("edge: single column", 5, s.minimumEffortPath(new int[][]{{1},{6},{2}}));
        j.expect("edge: all equal", 0, s.minimumEffortPath(new int[][]{{5,5},{5,5}}));
        j.expect("edge: huge heights", 999999, s.minimumEffortPath(new int[][]{{1,1000000}}));
        j.expect("edge: path may need to go uphill then down", 1,
                s.minimumEffortPath(new int[][]{{1,2},{9,3}}));
        j.expect("edge: detour avoids one big jump", 1,
                s.minimumEffortPath(new int[][]{{1,2,3},{9,9,4},{1,1,5}}));

        j.stress("random grids vs brute", 3000, g -> {
            int[][] h = g.matrix(g.intIn(1, 6), g.intIn(1, 6), 1, g.pick(3, 9, 20));
            return Judge.diff(Fmt.show(h), new Brute().minimumEffortPath(Fmt.copy(h)),
                    s.minimumEffortPath(Fmt.copy(h)));
        });

        int[][] big = new Gen(14).matrix(100, 100, 1, 1000000);
        j.perf("100x100, heights up to 10^6", 1500, () -> s.minimumEffortPath(Fmt.copy(big)));
        j.finish();
    }
}
