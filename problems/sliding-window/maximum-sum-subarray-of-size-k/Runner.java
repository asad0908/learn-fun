public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Maximum Sum Subarray of Size K (easy)");
        Solution s = new Solution();

        j.expect("example 1: [2,1,5,1,3,2] k=3", 9,
                 s.maxSumSubarrayOfSizeK(new int[]{2, 1, 5, 1, 3, 2}, 3));
        j.expect("example 2: [2,3,4,1,5] k=2", 7,
                 s.maxSumSubarrayOfSizeK(new int[]{2, 3, 4, 1, 5}, 2));
        j.expect("example 3: [1,1,1,1,1] k=5", 5,
                 s.maxSumSubarrayOfSizeK(new int[]{1, 1, 1, 1, 1}, 5));
        j.expect("edge: k == arr.length", 6,
                 s.maxSumSubarrayOfSizeK(new int[]{1, 2, 3}, 3));
        j.expect("edge: k == 1", 5,
                 s.maxSumSubarrayOfSizeK(new int[]{1, 5, 2, 4}, 1));
        j.expect("edge: all equal values", 30,
                 s.maxSumSubarrayOfSizeK(new int[]{10, 10, 10, 10}, 3));

        j.stress("random arrays vs brute", 2000, g -> {
            int n = g.intIn(1, 12);
            int[] a = g.array(n, 1, 8);
            int k = g.intIn(1, n);
            String in = "arr=" + Fmt.show(a) + " k=" + k;

            int mine = s.maxSumSubarrayOfSizeK(Fmt.copy(a), k);
            int ref = new Brute().maxSumSubarrayOfSizeK(Fmt.copy(a), k);
            return Judge.diff(in, ref, mine);
        });

        j.perf("n = 100000 must finish fast (O(n) expected)", 300, () -> {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 97) + 1;
            s.maxSumSubarrayOfSizeK(a, 1000);
        });

        j.finish();
    }
}
