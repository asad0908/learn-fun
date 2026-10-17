public class Runner {
    static int[][] weighted(Gen g, int[][] e, int lo, int hi) {
        int[][] w = new int[e.length][];
        for (int i = 0; i < e.length; i++) w[i] = new int[]{e[i][0], e[i][1], g.intIn(lo, hi)};
        return w;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Dijkstra's Algorithm");
        Solution s = new Solution();

        j.expect("example 1: detour beats direct edge", new int[]{4, 3, 0},
                s.dijkstra(3, new int[][]{{0,1,1},{1,2,3},{0,2,6}}, 2));
        j.expect("example 2", new int[]{0, 9}, s.dijkstra(2, new int[][]{{0,1,9}}, 0));
        j.expect("example 3: unreachable", new int[]{0, 2, -1}, s.dijkstra(3, new int[][]{{0,1,2}}, 0));
        j.expect("edge: single node", new int[]{0}, s.dijkstra(1, new int[][]{}, 0));
        j.expect("edge: undirected, travel against the listed direction", new int[]{5, 0},
                s.dijkstra(2, new int[][]{{0,1,5}}, 1));
        j.expect("edge: fewest edges is not cheapest", new int[]{0, 2, 5},
                s.dijkstra(3, new int[][]{{0,2,10},{0,1,2},{1,2,3}}, 0));
        j.expect("edge: nonzero source, isolated nodes", new int[]{-1, -1, 0},
                s.dijkstra(3, new int[][]{}, 2));
        j.expect("edge: ties", new int[]{0, 2, 2, 4},
                s.dijkstra(4, new int[][]{{0,1,2},{0,2,2},{1,3,2},{2,3,2}}, 0));

        j.stress("random weighted graphs, random source vs brute", 4000, g -> {
            int n = g.intIn(1, 9);
            int[][] e = weighted(g, g.edges(n, g.intIn(0, Math.min(n * (n - 1) / 2, 14))), 1, 9);
            int src = g.intIn(0, n - 1);
            return Judge.diff("V=" + n + " edges=" + Fmt.show(e) + " src=" + src,
                    new Brute().dijkstra(n, Fmt.copy(e), src), s.dijkstra(n, Fmt.copy(e), src));
        });

        Gen big = new Gen(10);
        int[][] be = weighted(big, big.connectedEdges(10000, 40000), 1, 1000);
        j.perf("V=10^4, E=5*10^4", 1500, () -> s.dijkstra(10000, be, 0));
        j.finish();
    }
}
