public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Cycle Detection in Directed Graph");
        Solution s = new Solution();

        j.expect("example 1", true, s.isCyclic(4, new int[][]{{0,1},{1,2},{2,3},{3,1}}));
        j.expect("example 2: path", false, s.isCyclic(4, new int[][]{{0,1},{1,2},{2,3}}));
        j.expect("example 3: reconverging paths, no cycle", false,
                s.isCyclic(3, new int[][]{{0,1},{0,2},{1,2}}));
        j.expect("edge: single node", false, s.isCyclic(1, new int[][]{}));
        j.expect("edge: two opposite edges", true, s.isCyclic(2, new int[][]{{0,1},{1,0}}));
        j.expect("edge: no edges", false, s.isCyclic(4, new int[][]{}));
        j.expect("edge: cycle only in a later component", true,
                s.isCyclic(5, new int[][]{{0,1},{2,3},{3,4},{4,2}}));
        j.expect("edge: diamond DAG", false,
                s.isCyclic(4, new int[][]{{0,1},{0,2},{1,3},{2,3}}));
        j.expect("edge: the same edges undirected-looking but one-way", false,
                s.isCyclic(3, new int[][]{{0,1},{1,2},{0,2}}));
        j.expect("edge: long cycle through every node", true,
                s.isCyclic(5, new int[][]{{0,1},{1,2},{2,3},{3,4},{4,0}}));

        j.stress("random digraphs vs brute", 4000, g -> {
            int n = g.intIn(1, 8);
            int[][] e = g.digraphEdges(n, g.intIn(0, Math.min(n * (n - 1), n + 3)));
            return Judge.diff("V=" + n + " edges=" + Fmt.show(e),
                    new Brute().isCyclic(n, Fmt.copy(e)), s.isCyclic(n, Fmt.copy(e)));
        });

        j.stress("DAGs with one back edge (near-misses)", 2000, g -> {
            int n = g.intIn(2, 8);
            int[][] dag = g.dagEdges(n, g.intIn(0, n + 2));
            int[][] e = dag;
            if (g.bool() && dag.length > 0) {
                int[] pick = dag[g.intIn(0, dag.length - 1)];
                e = java.util.Arrays.copyOf(dag, dag.length + 1);
                e[dag.length] = new int[]{pick[1], pick[0]};
            }
            return Judge.diff("V=" + n + " edges=" + Fmt.show(e),
                    new Brute().isCyclic(n, Fmt.copy(e)), s.isCyclic(n, Fmt.copy(e)));
        });

        int N = 10000;
        int[][] dag = new Gen(4).dagEdges(N, 20000);
        j.perf("DAG, V=10^4 E=2*10^4", 1500, () -> {
            if (s.isCyclic(N, dag)) throw new AssertionError("DAG reported as cyclic");
        });
        j.finish();
    }
}
