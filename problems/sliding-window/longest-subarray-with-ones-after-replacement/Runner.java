public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Longest Subarray with Ones after Replacement (hard)");
        Solution s = new Solution();

        j.expect("example 1: k=2", 6,
                 s.longestOnes(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2));
        j.expect("example 2: k=3", 10,
                 s.longestOnes(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3));
        j.expect("example 3: all zeros, k=0", 0,
                 s.longestOnes(new int[]{0, 0, 0, 0}, 0));
        j.expect("edge: all ones", 5,
                 s.longestOnes(new int[]{1, 1, 1, 1, 1}, 0));
        j.expect("edge: k covers every zero", 5,
                 s.longestOnes(new int[]{1, 0, 1, 0, 1}, 2));
        j.expect("edge: single zero, k=0", 0,
                 s.longestOnes(new int[]{0}, 0));

        j.stress("random binary arrays vs brute", 2000, g -> {
            int n = g.intIn(1, 14);
            int[] a = g.array(n, 0, 1);
            int k = g.intIn(0, n);
            String in = "nums=" + Fmt.show(a) + " k=" + k;

            int mine = s.longestOnes(Fmt.copy(a), k);
            int ref = new Brute().longestOnes(Fmt.copy(a), k);
            return Judge.diff(in, ref, mine);
        });

        j.perf("n = 100000 must finish fast (O(n) expected)", 300, () -> {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 3 == 0) ? 0 : 1;
            s.longestOnes(a, 1000);
        });

        j.finish();
    }
}
