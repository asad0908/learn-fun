public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Shortest Path in Non-Weighted Graph");
        Solution s = new Solution();

        j.expect("example 1", new int[]{0, 1, 2, 1, 2, 3, 3, 4, 4},
                s.shortestPath(new int[][]{{0,1},{0,3},{3,4},{4,5},{5,6},{1,2},{2,6},{6,7},{7,8},{6,8}}, 9, 0));
        j.expect("example 2: unreachable", new int[]{0, 1, 2, -1},
                s.shortestPath(new int[][]{{0,1},{1,2}}, 4, 0));
        j.expect("example 3: single node", new int[]{0}, s.shortestPath(new int[][]{}, 1, 0));
        j.expect("edge: source in the middle", new int[]{1, 0, 1},
                s.shortestPath(new int[][]{{0,1},{1,2}}, 3, 1));
        j.expect("edge: nonzero source, isolated nodes", new int[]{-1, -1, 0},
                s.shortestPath(new int[][]{}, 3, 2));
        j.expect("edge: edge listed high-to-low is still undirected", new int[]{2, 1, 0},
                s.shortestPath(new int[][]{{1,2},{0,1}}, 3, 2));
        j.expect("edge: triangle", new int[]{0, 1, 1},
                s.shortestPath(new int[][]{{0,1},{1,2},{2,0}}, 3, 0));

        j.stress("random graphs, random source vs brute", 4000, g -> {
            int n = g.intIn(1, 9);
            int[][] e = g.edges(n, g.intIn(0, Math.min(n * (n - 1) / 2, 12)));
            int src = g.intIn(0, n - 1);
            return Judge.diff("N=" + n + " edges=" + Fmt.show(e) + " src=" + src,
                    new Brute().shortestPath(Fmt.copy(e), n, src), s.shortestPath(Fmt.copy(e), n, src));
        });

        Gen big = new Gen(6);
        int[][] be = big.connectedEdges(10000, 0);
        j.perf("N=10^4 tree", 1500, () -> s.shortestPath(be, 10000, 0));
        j.finish();
    }
}
