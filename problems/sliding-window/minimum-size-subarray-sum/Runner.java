public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Minimum Size Subarray Sum");
        Solution s = new Solution();

        j.expect("example 1: target=7 [2,3,1,2,4,3]", 2,
                 s.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
        j.expect("example 2: target=4 [1,4,4]", 1,
                 s.minSubArrayLen(4, new int[]{1, 4, 4}));
        j.expect("example 3: target=11 [1,1,1,1,1,1,1,1]", 0,
                 s.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
        j.expect("edge: single element meets target exactly", 1,
                 s.minSubArrayLen(5, new int[]{5}));
        j.expect("edge: whole array barely enough", 4,
                 s.minSubArrayLen(10, new int[]{1, 2, 3, 4}));
        j.expect("edge: target smaller than every element", 1,
                 s.minSubArrayLen(1, new int[]{5, 5, 5}));

        j.stress("random arrays vs brute", 2000, g -> {
            int n = g.intIn(1, 12);
            int[] a = g.array(n, 1, 6);
            int target = g.intIn(1, 30);
            String in = "target=" + target + " nums=" + Fmt.show(a);

            int mine = s.minSubArrayLen(target, Fmt.copy(a));
            int ref = new Brute().minSubArrayLen(target, Fmt.copy(a));
            return Judge.diff(in, ref, mine);
        });

        j.perf("n = 100000 must finish fast (O(n) expected)", 300, () -> {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 10) + 1;
            s.minSubArrayLen(1_000_000_000, a);
        });

        j.finish();
    }
}
