public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Subarrays with Product Less than a Target (medium)");
        Solution s = new Solution();

        j.expect("example 1: [10,5,2,6] k=100", 8,
                 s.numSubarrayProductLessThanK(new int[]{10, 5, 2, 6}, 100));
        j.expect("example 2: k=0", 0,
                 s.numSubarrayProductLessThanK(new int[]{1, 2, 3}, 0));
        j.expect("example 3: [1,1,1] k=2", 6,
                 s.numSubarrayProductLessThanK(new int[]{1, 1, 1}, 2));
        j.expect("edge: k=1 excludes everything", 0,
                 s.numSubarrayProductLessThanK(new int[]{1, 2, 3}, 1));
        j.expect("edge: single element below k", 1,
                 s.numSubarrayProductLessThanK(new int[]{5}, 6));
        j.expect("edge: single element at k (not strictly less)", 0,
                 s.numSubarrayProductLessThanK(new int[]{5}, 5));
        j.expect("edge: big values, overflow bait", 3,
                 s.numSubarrayProductLessThanK(new int[]{1000, 1000, 1000}, 1001));

        j.stress("random positive arrays vs brute", 3000, g -> {
            int n = g.intIn(1, 12);
            int[] a = g.array(n, 1, 9);
            int k = g.intIn(0, 200);
            return Judge.diff("nums=" + Fmt.show(a) + " k=" + k,
                    new Brute().numSubarrayProductLessThanK(Fmt.copy(a), k),
                    s.numSubarrayProductLessThanK(Fmt.copy(a), k));
        });

        j.stress("all ones (worst case for the window count)", 200, g -> {
            int n = g.intIn(1, 30);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 1;
            int k = g.intIn(0, 3);
            return Judge.diff("nums=" + Fmt.show(a) + " k=" + k,
                    new Brute().numSubarrayProductLessThanK(Fmt.copy(a), k),
                    s.numSubarrayProductLessThanK(Fmt.copy(a), k));
        });

        j.perf("n = 30000 all ones, k = 2 (O(n) expected)", 400, () -> {
            int[] a = new int[30000];
            for (int i = 0; i < a.length; i++) a[i] = 1;
            s.numSubarrayProductLessThanK(a, 2);
        });

        j.finish();
    }
}
