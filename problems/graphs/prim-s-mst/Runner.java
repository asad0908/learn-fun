public class Runner {
    static int[][] weighted(Gen g, int[][] e, int lo, int hi) {
        int[][] w = new int[e.length][];
        for (int i = 0; i < e.length; i++) w[i] = new int[]{e[i][0], e[i][1], g.intIn(lo, hi)};
        return w;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Prim's MST");
        Solution s = new Solution();

        j.expect("example 1", 4, s.spanningTree(3, new int[][]{{0,1,5},{1,2,3},{0,2,1}}));
        j.expect("example 2: single edge", 5, s.spanningTree(2, new int[][]{{0,1,5}}));
        j.expect("example 3: skip the expensive closing edge", 3,
                s.spanningTree(4, new int[][]{{0,1,1},{1,2,1},{2,3,1},{0,3,10}}));
        j.expect("edge: graph is already a tree", 6, s.spanningTree(4, new int[][]{{0,1,1},{0,2,2},{0,3,3}}));
        j.expect("edge: all weights equal", 6, s.spanningTree(4,
                new int[][]{{0,1,2},{0,2,2},{0,3,2},{1,2,2},{1,3,2},{2,3,2}}));
        j.expect("edge: cheapest edges are not enough (greedy per node fails)", 7,
                s.spanningTree(4, new int[][]{{0,1,1},{2,3,1},{1,2,5},{0,3,9},{0,2,6}}));
        j.expect("edge: max weights", 2000, s.spanningTree(3, new int[][]{{0,1,1000},{1,2,1000},{0,2,1000}}));
        j.expect("edge: edge given high-to-low", 3, s.spanningTree(3, new int[][]{{1,0,1},{2,1,2}}));

        j.stress("random connected graphs vs brute (exhaustive subsets)", 3000, g -> {
            int n = g.intIn(2, 6);
            int[][] e = weighted(g, g.connectedEdges(n, g.intIn(0, 5)), 1, g.pick(3, 9));
            return Judge.diff("V=" + n + " edges=" + Fmt.show(e), new Brute().spanningTree(n, Fmt.copy(e)),
                    s.spanningTree(n, Fmt.copy(e)));
        });

        Gen big = new Gen(18);
        int[][] be = weighted(big, big.connectedEdges(1000, 20000), 1, 1000);
        j.perf("V=1000, E=~2*10^4", 1000, () -> s.spanningTree(1000, be));
        j.finish();
    }
}
