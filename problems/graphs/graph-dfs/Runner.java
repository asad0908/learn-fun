import java.util.*;

public class Runner {
    static List<List<Integer>> adj(int[]... rows) {
        List<List<Integer>> a = new ArrayList<>();
        for (int[] r : rows) { List<Integer> l = new ArrayList<>(); for (int v : r) l.add(v); a.add(l); }
        return a;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Graph DFS");
        Solution s = new Solution();

        j.expect("example 1", Arrays.asList(0, 2, 4, 3, 1),
                s.dfsOfGraph(adj(new int[]{2,3,1}, new int[]{0}, new int[]{0,4}, new int[]{0}, new int[]{2})));
        j.expect("example 2", Arrays.asList(0, 1, 2, 3, 4),
                s.dfsOfGraph(adj(new int[]{1,2}, new int[]{0,2}, new int[]{0,1,3,4}, new int[]{2}, new int[]{2})));
        j.expect("edge: single node", Arrays.asList(0), s.dfsOfGraph(adj(new int[]{})));
        j.expect("edge: node 1 unreachable from 0", Arrays.asList(0),
                s.dfsOfGraph(adj(new int[]{}, new int[]{})));
        j.expect("edge: neighbour order matters (reversed list)", Arrays.asList(0, 2, 1),
                s.dfsOfGraph(adj(new int[]{2,1}, new int[]{0}, new int[]{0})));
        j.expect("edge: depth beats breadth", Arrays.asList(0, 1, 3, 2),
                s.dfsOfGraph(adj(new int[]{1,2}, new int[]{0,3}, new int[]{0,3}, new int[]{1,2})));

        j.stress("random undirected + directed adjacency lists vs brute", 3000, g -> {
            int n = g.intIn(1, 9);
            boolean dir = g.bool();
            int[][] e = dir ? g.digraphEdges(n, g.intIn(0, 14))
                            : g.edges(n, g.intIn(0, Math.min(n * (n - 1) / 2, 12)));
            List<List<Integer>> a = g.shuffledAdj(n, e, dir);
            return Judge.diff("adj=" + a, new Brute().dfsOfGraph(Fmt.copy(a)), s.dfsOfGraph(Fmt.copy(a)));
        });

        Gen big = new Gen(11);
        List<List<Integer>> ba = big.shuffledAdj(10000, big.connectedEdges(10000, 0), false);
        // random tree: shallow enough for a recursive solution
        j.perf("V=10^4 random tree", 1500, () -> s.dfsOfGraph(ba));
        j.finish();
    }
}
