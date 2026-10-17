import java.util.*;

public class Runner {
    static List<List<Integer>> adj(int[]... rows) {
        List<List<Integer>> a = new ArrayList<>();
        for (int[] r : rows) { List<Integer> l = new ArrayList<>(); for (int v : r) l.add(v); a.add(l); }
        return a;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Graph BFS");
        Solution s = new Solution();

        j.expect("example 1", Arrays.asList(0, 1, 2, 3, 4),
                s.bfsOfGraph(adj(new int[]{1,2,3}, new int[]{}, new int[]{4}, new int[]{}, new int[]{})));
        j.expect("example 2: listed order", Arrays.asList(0, 2, 1),
                s.bfsOfGraph(adj(new int[]{2,1}, new int[]{0}, new int[]{0})));
        j.expect("edge: single node", Arrays.asList(0), s.bfsOfGraph(adj(new int[]{})));
        j.expect("edge: unreachable node omitted", Arrays.asList(0, 1),
                s.bfsOfGraph(adj(new int[]{1}, new int[]{0}, new int[]{})));
        j.expect("edge: breadth beats depth", Arrays.asList(0, 1, 2, 3),
                s.bfsOfGraph(adj(new int[]{1,2}, new int[]{3}, new int[]{}, new int[]{})));
        j.expect("edge: cycle does not repeat nodes", Arrays.asList(0, 1, 2),
                s.bfsOfGraph(adj(new int[]{1,2}, new int[]{0,2}, new int[]{0,1})));

        j.stress("random undirected + directed adjacency lists vs brute", 3000, g -> {
            int n = g.intIn(1, 9);
            boolean dir = g.bool();
            int[][] e = dir ? g.digraphEdges(n, g.intIn(0, 14))
                            : g.edges(n, g.intIn(0, Math.min(n * (n - 1) / 2, 12)));
            List<List<Integer>> a = g.shuffledAdj(n, e, dir);
            return Judge.diff("adj=" + a, new Brute().bfsOfGraph(Fmt.copy(a)), s.bfsOfGraph(Fmt.copy(a)));
        });

        Gen big = new Gen(13);
        List<List<Integer>> ba = big.shuffledAdj(10000, big.connectedEdges(10000, 5000), false);
        j.perf("V=10^4, E=15*10^3", 1500, () -> s.bfsOfGraph(ba));
        j.finish();
    }
}
