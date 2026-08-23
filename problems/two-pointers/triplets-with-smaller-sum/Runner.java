public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Triplets with Smaller Sum (medium)");
        Solution s = new Solution();

        j.expect("example 1: [-1,0,2,3] t=3", 2,
                 s.countTriplets(new int[]{-1, 0, 2, 3}, 3));
        j.expect("example 2: [-1,4,2,1,3] t=5", 4,
                 s.countTriplets(new int[]{-1, 4, 2, 1, 3}, 5));
        j.expect("example 3: strict inequality [1,1,1] t=3", 0,
                 s.countTriplets(new int[]{1, 1, 1}, 3));
        j.expect("edge: fewer than 3 elements", 0,
                 s.countTriplets(new int[]{1, 2}, 100));
        j.expect("edge: empty", 0, s.countTriplets(new int[]{}, 0));
        j.expect("edge: every triplet qualifies", 4,
                 s.countTriplets(new int[]{-5, -4, -3, -2}, 100));
        j.expect("edge: no triplet qualifies", 0,
                 s.countTriplets(new int[]{5, 6, 7, 8}, 0));
        j.expect("edge: all equal, counted by index", 4,
                 s.countTriplets(new int[]{1, 1, 1, 1}, 4));

        j.stress("random arrays vs brute", 3000, g -> {
            int n = g.intIn(0, 12);
            int[] a = g.array(n, -8, 8);
            int target = g.intIn(-12, 12);
            return Judge.diff("arr=" + Fmt.show(a) + " target=" + target,
                    new Brute().countTriplets(Fmt.copy(a), target),
                    s.countTriplets(Fmt.copy(a), target));
        });

        j.perf("n = 2000 (O(n^2) expected)", 2000, () -> {
            int[] a = new Gen(3).array(2000, -10000, 10000);
            s.countTriplets(a, 500);
        });

        j.finish();
    }
}
