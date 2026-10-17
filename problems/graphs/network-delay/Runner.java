public class Runner {
    static int[][] timesFor(Gen g, int n, int m, int maxW) {
        int[][] e = g.digraphEdges(n, m);
        int[][] t = new int[e.length][];
        for (int i = 0; i < e.length; i++) t[i] = new int[]{e[i][0] + 1, e[i][1] + 1, g.intIn(0, maxW)};
        return t;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Network Delay");
        Solution s = new Solution();

        j.expect("example 1", 2, s.networkDelayTime(new int[][]{{2,1,1},{2,3,1},{3,4,1}}, 4, 2));
        j.expect("example 2", 1, s.networkDelayTime(new int[][]{{1,2,1}}, 2, 1));
        j.expect("example 3: unreachable source", -1, s.networkDelayTime(new int[][]{{1,2,1}}, 2, 2));
        j.expect("edge: node never reached", -1, s.networkDelayTime(new int[][]{{1,2,1}}, 3, 1));
        j.expect("edge: indirect route is cheaper", 3,
                s.networkDelayTime(new int[][]{{1,2,1},{2,3,2},{1,3,100}}, 3, 1));
        j.expect("edge: zero-weight edges", 0, s.networkDelayTime(new int[][]{{1,2,0},{2,3,0}}, 3, 1));
        j.expect("edge: answer is the slowest branch", 5,
                s.networkDelayTime(new int[][]{{1,2,5},{1,3,1},{3,4,1}}, 4, 1));
        j.expect("edge: edges are directed", -1,
                s.networkDelayTime(new int[][]{{2,1,1},{3,1,1}}, 3, 1));
        j.expect("edge: cycle does not loop forever", 2,
                s.networkDelayTime(new int[][]{{1,2,1},{2,1,1},{2,3,1}}, 3, 1));

        j.stress("random directed graphs vs brute", 4000, g -> {
            int n = g.intIn(2, 7);
            int m = g.intIn(1, Math.min(n * (n - 1), n + 6));
            int[][] t = timesFor(g, n, m, 6);
            int k = g.intIn(1, n);
            return Judge.diff("n=" + n + " k=" + k + " times=" + Fmt.show(t),
                    new Brute().networkDelayTime(Fmt.copy(t), n, k), s.networkDelayTime(Fmt.copy(t), n, k));
        });

        int[][] big = timesFor(new Gen(12), 100, 6000, 100);
        j.perf("n=100, 6000 edges", 1000, () -> s.networkDelayTime(big, 100, 1));
        j.finish();
    }
}
