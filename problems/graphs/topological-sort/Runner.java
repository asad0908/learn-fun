import java.util.*;

public class Runner {
    /** null if `order` is a valid topological order, else why not */
    static String check(int V, int[][] edges, int[] order) {
        if (order == null || order.length != V) return "wrong length: " + Fmt.show(order);
        int[] pos = new int[V];
        Arrays.fill(pos, -1);
        for (int i = 0; i < V; i++) {
            if (order[i] < 0 || order[i] >= V || pos[order[i]] != -1)
                return "not a permutation of 0.." + (V - 1) + ": " + Fmt.show(order);
            pos[order[i]] = i;
        }
        for (int[] e : edges)
            if (pos[e[0]] > pos[e[1]]) return "edge " + e[0] + "->" + e[1] + " violated by " + Fmt.show(order);
        return null;
    }

    static void fixed(Judge j, String label, int V, int[][] edges, Solution s) {
        String bad = check(V, edges, s.topoSort(V, Fmt.copy(edges)));
        j.expect(label, "valid", bad == null ? "valid" : bad);
    }

    public static void main(String[] args) {
        Judge j = new Judge("Topological Sort");
        Solution s = new Solution();

        fixed(j, "example 1", 6, new int[][]{{5,0},{5,2},{4,0},{4,1},{2,3},{3,1}}, s);
        fixed(j, "example 2", 4, new int[][]{{3,0},{1,0},{2,0}}, s);
        j.expect("example 3: chain has one order", Arrays.asList(0, 1, 2),
                toList(s.topoSort(3, new int[][]{{0,1},{1,2}})));
        fixed(j, "edge: reversed chain", 4, new int[][]{{3,2},{2,1},{1,0}}, s);
        fixed(j, "edge: isolated nodes must still appear", 5, new int[][]{{0,1}}, s);
        fixed(j, "edge: diamond", 4, new int[][]{{0,1},{0,2},{1,3},{2,3}}, s);
        fixed(j, "edge: complete DAG", 4, new int[][]{{0,1},{0,2},{0,3},{1,2},{1,3},{2,3}}, s);

        j.stress("random DAGs: output must be a valid order", 4000, g -> {
            int n = g.intIn(2, 9);
            int[][] e = g.dagEdges(n, g.intIn(1, n + 4));
            if (e.length == 0) return null;
            String oracle = check(n, e, new Brute().topoSort(n, Fmt.copy(e)));
            if (oracle != null) return "ORACLE BUG: " + oracle;
            String bad = check(n, e, s.topoSort(n, Fmt.copy(e)));
            return bad == null ? null : "V=" + n + " edges=" + Fmt.show(e) + "\n        " + bad;
        });

        int N = 10000;
        int[][] big = new Gen(8).dagEdges(N, 10000);
        j.perf("V=10^4, E=10^4", 1500, () -> {
            String bad = check(N, big, s.topoSort(N, big));
            if (bad != null) throw new AssertionError(bad);
        });
        j.finish();
    }

    static List<Integer> toList(int[] a) {
        List<Integer> l = new ArrayList<>();
        for (int v : a) l.add(v);
        return l;
    }
}
