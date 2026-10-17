public class Runner {
    static int[][] flightsFor(Gen g, int n, int m, int maxPrice) {
        int[][] e = g.digraphEdges(n, m);
        int[][] f = new int[e.length][];
        for (int i = 0; i < e.length; i++) f[i] = new int[]{e[i][0], e[i][1], g.intIn(1, maxPrice)};
        return f;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Cheapest Path in K Stops");
        Solution s = new Solution();
        int[][] ex1 = {{0,1,100},{1,2,100},{2,0,100},{1,3,600},{2,3,200}};
        int[][] ex2 = {{0,1,100},{1,2,100},{0,2,500}};

        j.expect("example 1: k=1", 700, s.findCheapestPrice(4, ex1, 0, 3, 1));
        j.expect("example 2: k=1", 200, s.findCheapestPrice(3, ex2, 0, 2, 1));
        j.expect("example 3: k=0 forces direct flight", 500, s.findCheapestPrice(3, ex2, 0, 2, 0));
        j.expect("edge: k=2 unlocks the cheap long route", 400, s.findCheapestPrice(4, ex1, 0, 3, 2));
        j.expect("edge: k=0 and no direct flight", -1, s.findCheapestPrice(4, ex1, 0, 3, 0));
        j.expect("edge: no flights", -1, s.findCheapestPrice(2, new int[][]{}, 0, 1, 1));
        j.expect("edge: destination unreachable (directed)", -1,
                s.findCheapestPrice(3, new int[][]{{1,0,5},{2,1,5}}, 0, 2, 2));
        j.expect("edge: more stops than needed", 10,
                s.findCheapestPrice(3, new int[][]{{0,1,5},{1,2,5}}, 0, 2, 2));
        j.expect("edge: cheaper path uses more stops than a pricey short one", 3,
                s.findCheapestPrice(5, new int[][]{{0,4,100},{0,1,1},{1,2,1},{2,4,1}}, 0, 4, 3));
        j.expect("edge: stop budget exactly sufficient", 3,
                s.findCheapestPrice(5, new int[][]{{0,4,100},{0,1,1},{1,2,1},{2,4,1}}, 0, 4, 2));
        j.expect("edge: stop budget one short", 100,
                s.findCheapestPrice(5, new int[][]{{0,4,100},{0,1,1},{1,2,1},{2,4,1}}, 0, 4, 1));

        j.stress("random flight maps vs brute", 4000, g -> {
            int n = g.intIn(2, 6);
            int[][] f = flightsFor(g, n, g.intIn(0, Math.min(n * (n - 1), n + 6)), 9);
            int src = g.intIn(0, n - 1), dst;
            do { dst = g.intIn(0, n - 1); } while (dst == src);
            int k = g.intIn(0, n - 1);
            return Judge.diff("n=" + n + " flights=" + Fmt.show(f) + " src=" + src + " dst=" + dst + " k=" + k,
                    new Brute().findCheapestPrice(n, Fmt.copy(f), src, dst, k),
                    s.findCheapestPrice(n, Fmt.copy(f), src, dst, k));
        });

        int[][] big = flightsFor(new Gen(17), 100, 4000, 10000);
        j.perf("n=100, 4000 flights, k=50", 1000, () -> s.findCheapestPrice(100, big, 0, 99, 50));
        j.finish();
    }
}
