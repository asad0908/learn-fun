public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Fruits into Baskets (medium)");
        Solution s = new Solution();

        j.expect("example 1: [1,2,1]", 3,
                 s.totalFruit(new int[]{1, 2, 1}));
        j.expect("example 2: [0,1,2,2]", 3,
                 s.totalFruit(new int[]{0, 1, 2, 2}));
        j.expect("example 3: [1,2,3,2,2]", 4,
                 s.totalFruit(new int[]{1, 2, 3, 2, 2}));
        j.expect("edge: single tree", 1,
                 s.totalFruit(new int[]{5}));
        j.expect("edge: all same type", 4,
                 s.totalFruit(new int[]{3, 3, 3, 3}));
        j.expect("edge: three distinct types, each blocks the next", 2,
                 s.totalFruit(new int[]{1, 2, 3, 1}));

        j.stress("random arrays vs brute", 2000, g -> {
            int n = g.intIn(1, 12);
            int[] a = g.array(n, 0, 3);
            String in = "fruits=" + Fmt.show(a);

            int mine = s.totalFruit(Fmt.copy(a));
            int ref = new Brute().totalFruit(Fmt.copy(a));
            return Judge.diff(in, ref, mine);
        });

        j.perf("n = 100000 must finish fast (O(n) expected)", 300, () -> {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i % 3;
            s.totalFruit(a);
        });

        j.finish();
    }
}
