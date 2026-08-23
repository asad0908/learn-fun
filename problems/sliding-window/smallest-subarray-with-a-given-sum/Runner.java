public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Smallest Subarray with a given sum (easy)");
        Solution s = new Solution();

        j.expect("example 1: [2,1,5,2,3,2] s=7", 2,
                 s.smallestSubarrayWithGivenSum(7, new int[]{2, 1, 5, 2, 3, 2}));
        j.expect("example 2: [2,1,5,2,8] s=7", 1,
                 s.smallestSubarrayWithGivenSum(7, new int[]{2, 1, 5, 2, 8}));
        j.expect("example 3: [3,4,1,1,6] s=8", 3,
                 s.smallestSubarrayWithGivenSum(8, new int[]{3, 4, 1, 1, 6}));
        j.expect("edge: no subarray reaches s", 0,
                 s.smallestSubarrayWithGivenSum(100, new int[]{1, 2, 3}));
        j.expect("edge: single element already enough", 1,
                 s.smallestSubarrayWithGivenSum(5, new int[]{5}));
        j.expect("edge: whole array barely enough", 4,
                 s.smallestSubarrayWithGivenSum(10, new int[]{1, 2, 3, 4}));

        j.stress("random arrays vs brute", 2000, g -> {
            int n = g.intIn(1, 12);
            int[] a = g.array(n, 1, 6);
            int target = g.intIn(1, 30);
            String in = "arr=" + Fmt.show(a) + " s=" + target;

            int mine = s.smallestSubarrayWithGivenSum(target, Fmt.copy(a));
            int ref = new Brute().smallestSubarrayWithGivenSum(target, Fmt.copy(a));
            return Judge.diff(in, ref, mine);
        });

        j.perf("n = 100000 must finish fast (O(n) expected)", 300, () -> {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 50) + 1;
            s.smallestSubarrayWithGivenSum(1_000_000_000, a);
        });

        j.finish();
    }
}
