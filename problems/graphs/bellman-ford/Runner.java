public class Runner {
    static int[][] weighted(Gen g, int[][] e, int lo, int hi) {
        int[][] w = new int[e.length][];
        for (int i = 0; i < e.length; i++) w[i] = new int[]{e[i][0], e[i][1], g.intIn(lo, hi)};
        return w;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Bellman Ford");
        Solution s = new Solution();

        j.expect("example 1: negative edge helps", new int[]{0, 5, 6, 6, 7},
                s.bellmanFord(5, new int[][]{{1,3,2},{4,3,-1},{2,4,1},{1,2,1},{0,1,5}}, 0));
        j.expect("example 2: reachable negative cycle", new int[]{-1},
                s.bellmanFord(4, new int[][]{{0,1,4},{1,2,-6},{2,3,5},{3,1,-2}}, 0));
        j.expect("example 3: unreachable", new int[]{0, 2, 100000000},
                s.bellmanFord(3, new int[][]{{0,1,2}}, 0));
        j.expect("edge: single node", new int[]{0}, s.bellmanFord(1, new int[][]{}, 0));
        j.expect("edge: negative cycle NOT reachable from src is ignored",
                new int[]{0, 100000000, 100000000},
                s.bellmanFord(3, new int[][]{{1,2,-1},{2,1,-1}}, 0));
        j.expect("edge: zero-weight cycle is fine", new int[]{0, 1, 1},
                s.bellmanFord(3, new int[][]{{0,1,1},{1,2,0},{2,1,0}}, 0));
        j.expect("edge: negative edge but no cycle", new int[]{0, -3},
                s.bellmanFord(2, new int[][]{{0,1,-3}}, 0));
        j.expect("edge: directed, cannot go backwards", new int[]{100000000, 0},
                s.bellmanFord(2, new int[][]{{0,1,4}}, 1));
        j.expect("edge: negative cycle that also reaches onward", new int[]{-1},
                s.bellmanFord(4, new int[][]{{0,1,1},{1,2,-3},{2,1,1},{2,3,1}}, 0));
        j.expect("edge: 2-cycle summing to exactly zero", new int[]{0, 5},
                s.bellmanFord(2, new int[][]{{0,1,5},{1,0,-5}}, 0));

        j.stress("random digraphs, mixed-sign weights vs brute", 4000, g -> {
            int n = g.intIn(1, 6);
            int[][] e = weighted(g, g.digraphEdges(n, g.intIn(0, Math.min(n * (n - 1), n + 5))), -4, 8);
            int src = g.intIn(0, n - 1);
            return Judge.diff("V=" + n + " edges=" + Fmt.show(e) + " src=" + src,
                    new Brute().bellmanFord(n, Fmt.copy(e), src), s.bellmanFord(n, Fmt.copy(e), src));
        });

        j.stress("mostly-positive weights (negative cycles rarer)", 3000, g -> {
            int n = g.intIn(2, 7);
            int[][] e = weighted(g, g.digraphEdges(n, g.intIn(1, Math.min(n * (n - 1), n + 5))), -2, 9);
            int src = g.intIn(0, n - 1);
            return Judge.diff("V=" + n + " edges=" + Fmt.show(e) + " src=" + src,
                    new Brute().bellmanFord(n, Fmt.copy(e), src), s.bellmanFord(n, Fmt.copy(e), src));
        });

        int[][] big = weighted(new Gen(16), new Gen(16).digraphEdges(500, 10000), 0, 1000);
        j.perf("V=500, E=10^4", 1000, () -> s.bellmanFord(500, big, 0));
        j.finish();
    }
}
