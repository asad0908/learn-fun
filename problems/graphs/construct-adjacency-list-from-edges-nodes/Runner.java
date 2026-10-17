import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Construct Adjacency List from Edges + Nodes");
        Solution s = new Solution();

        j.expect("example 1", Arrays.asList(Arrays.asList(1, 4), Arrays.asList(0, 2, 3, 4),
                Arrays.asList(1, 3), Arrays.asList(1, 2, 4), Arrays.asList(0, 1, 3)),
                s.printGraph(5, new int[][]{{0,1},{0,4},{4,1},{4,3},{1,3},{1,2},{3,2}}));
        j.expect("example 2: isolated node", Arrays.asList(Arrays.asList(2, 3),
                Arrays.<Integer>asList(), Arrays.asList(0), Arrays.asList(0)),
                s.printGraph(4, new int[][]{{0,3},{0,2}}));
        j.expect("edge: single node", Arrays.asList(Arrays.<Integer>asList()),
                s.printGraph(1, new int[][]{}));
        j.expect("edge: no edges, 3 nodes", Arrays.asList(Arrays.<Integer>asList(),
                Arrays.<Integer>asList(), Arrays.<Integer>asList()),
                s.printGraph(3, new int[][]{}));
        j.expect("edge: edge given high-to-low still sorted both sides",
                Arrays.asList(Arrays.asList(1, 2), Arrays.asList(0), Arrays.asList(0)),
                s.printGraph(3, new int[][]{{2,0},{1,0}}));

        j.stress("random graphs vs brute", 2000, g -> {
            int n = g.intIn(1, 9);
            int[][] e = g.edges(n, g.intIn(0, Math.min(n * (n - 1) / 2, 14)));
            return Judge.diff("V=" + n + " edges=" + Fmt.show(e),
                    new Brute().printGraph(n, Fmt.copy(e)), s.printGraph(n, Fmt.copy(e)));
        });

        Gen big = new Gen(7);
        int[][] be = big.edges(10000, 10000);
        j.perf("V=10^4, E=10^4", 1500, () -> s.printGraph(10000, be));
        j.finish();
    }
}
