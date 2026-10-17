import java.util.*;

public class Runner {
    static int[][] toArr(List<List<Integer>> adj) {
        int[][] g = new int[adj.size()][];
        for (int i = 0; i < g.length; i++) {
            g[i] = new int[adj.get(i).size()];
            for (int k = 0; k < g[i].length; k++) g[i][k] = adj.get(i).get(k);
        }
        return g;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Bipartite Graph / Graph Coloring");
        Solution s = new Solution();

        j.expect("example 1: triangle", false,
                s.isBipartite(new int[][]{{1,2,3},{0,2},{0,1,3},{0,2}}));
        j.expect("example 2: 4-cycle", true, s.isBipartite(new int[][]{{1,3},{0,2},{1,3},{0,2}}));
        j.expect("example 3: no edges", true, s.isBipartite(new int[][]{{},{}}));
        j.expect("edge: single node", true, s.isBipartite(new int[][]{{}}));
        j.expect("edge: single edge", true, s.isBipartite(new int[][]{{1},{0}}));
        j.expect("edge: 5-cycle", false, s.isBipartite(new int[][]{{1,4},{0,2},{1,3},{2,4},{3,0}}));
        j.expect("edge: odd cycle only in the second component", false,
                s.isBipartite(new int[][]{{1},{0},{3,4},{2,4},{2,3}}));
        j.expect("edge: even cycle plus tree", true,
                s.isBipartite(new int[][]{{1,3},{0,2},{1,3,4},{0,2},{2}}));
        j.expect("edge: long path", true, s.isBipartite(new int[][]{{1},{0,2},{1,3},{2,4},{3}}));

        j.stress("random graphs vs brute", 3000, g -> {
            int n = g.intIn(1, 9);
            int[][] e = g.edges(n, g.intIn(0, Math.min(n * (n - 1) / 2, n + 2)));
            int[][] gr = toArr(Fmt.adj(n, e, false));
            return Judge.diff(Fmt.show(gr), new Brute().isBipartite(Fmt.copy(gr)),
                    s.isBipartite(Fmt.copy(gr)));
        });

        j.stress("graphs built bipartite, plus at most one random extra edge", 3000, g -> {
            int n = g.intIn(2, 10);
            int[] side = g.array(n, 0, 1);
            Set<Long> seen = new HashSet<>();
            List<int[]> es = new ArrayList<>();
            for (int t = 0; t < n * 2; t++) {
                int u = g.intIn(0, n - 1), v = g.intIn(0, n - 1);
                if (u == v || side[u] == side[v] || !seen.add((long) Math.min(u, v) * n + Math.max(u, v))) continue;
                es.add(new int[]{u, v});
            }
            if (g.bool()) {
                int u = g.intIn(0, n - 1), v = g.intIn(0, n - 1);
                if (u != v && seen.add((long) Math.min(u, v) * n + Math.max(u, v))) es.add(new int[]{u, v});
            }
            int[][] gr = toArr(Fmt.adj(n, es.toArray(new int[0][]), false));
            return Judge.diff(Fmt.show(gr), new Brute().isBipartite(Fmt.copy(gr)),
                    s.isBipartite(Fmt.copy(gr)));
        });
        j.finish();
    }
}
