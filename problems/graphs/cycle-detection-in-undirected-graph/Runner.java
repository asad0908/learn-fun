public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Cycle Detection in Undirected Graph");
        Solution s = new Solution();

        j.expect("example 1: triangle with tail", true,
                s.isCycle(4, new int[][]{{0,1},{0,2},{1,2},{2,3}}));
        j.expect("example 2: path", false, s.isCycle(4, new int[][]{{0,1},{1,2},{2,3}}));
        j.expect("example 3: cycle in second component", true,
                s.isCycle(5, new int[][]{{0,1},{2,3},{3,4},{4,2}}));
        j.expect("edge: single node", false, s.isCycle(1, new int[][]{}));
        j.expect("edge: one edge is not a cycle", false, s.isCycle(2, new int[][]{{0,1}}));
        j.expect("edge: no edges", false, s.isCycle(5, new int[][]{}));
        j.expect("edge: forest of several trees", false,
                s.isCycle(6, new int[][]{{0,1},{1,2},{3,4},{3,5}}));
        j.expect("edge: star", false, s.isCycle(5, new int[][]{{0,1},{0,2},{0,3},{0,4}}));
        j.expect("edge: square", true, s.isCycle(4, new int[][]{{0,1},{1,2},{2,3},{3,0}}));
        j.expect("edge: edge given in both orientations across the cycle", true,
                s.isCycle(3, new int[][]{{1,0},{2,1},{0,2}}));

        j.stress("random sparse graphs vs brute", 4000, g -> {
            int n = g.intIn(1, 9);
            int[][] e = g.edges(n, g.intIn(0, Math.min(n * (n - 1) / 2, n + 1)));
            return Judge.diff("V=" + n + " edges=" + Fmt.show(e),
                    new Brute().isCycle(n, Fmt.copy(e)), s.isCycle(n, Fmt.copy(e)));
        });

        int N = 100000;
        int[][] tree = new Gen(2).connectedEdges(N, 0);
        int[][] withCycle = new Gen(2).connectedEdges(N, 1);
        j.perf("tree on 10^5 nodes (acyclic)", 1500, () -> {
            if (s.isCycle(N, tree)) throw new AssertionError("tree reported as cyclic");
        });
        j.perf("tree + 1 extra edge on 10^5 nodes", 1500, () -> {
            if (!s.isCycle(N, withCycle)) throw new AssertionError("cycle missed");
        });
        j.finish();
    }
}
