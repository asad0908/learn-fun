public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Pair with Target Sum (easy)");
        Solution s = new Solution();

        j.expect("example 1: [2,7,11,15] t=9", new int[]{1, 2},
                 s.twoSum(new int[]{2, 7, 11, 15}, 9));
        j.expect("example 2: [2,3,4] t=6", new int[]{1, 3},
                 s.twoSum(new int[]{2, 3, 4}, 6));
        j.expect("example 3: [-1,0] t=-1", new int[]{1, 2},
                 s.twoSum(new int[]{-1, 0}, -1));
        j.expect("edge: duplicates [3,3] t=6", new int[]{1, 2},
                 s.twoSum(new int[]{3, 3}, 6));
        j.expect("edge: negatives [-5,-3,-1,2] t=-4", new int[]{2, 3},
                 s.twoSum(new int[]{-5, -3, -1, 2}, -4));
        j.expect("edge: pair sits at both ends [2,5,9,11] t=13", new int[]{1, 4},
                 s.twoSum(new int[]{2, 5, 9, 11}, 13));

        // Several valid pairs can exist, so check the property rather than
        // diffing against Brute's particular choice.
        j.stress("random sorted arrays vs brute", 3000, g -> {
            int n = g.intIn(2, 12);
            int[] a = g.sortedArray(n, -8, 8);
            int target = g.intIn(-16, 16);
            String in = "numbers=" + Fmt.show(a) + " target=" + target;

            int[] mine = s.twoSum(Fmt.copy(a), target);
            int[] ref = new Brute().twoSum(Fmt.copy(a), target);

            if (ref.length == 0) {
                return mine.length == 0 ? null
                    : "input    " + in + "\n        brute    no pair exists"
                      + "\n        yours    " + Fmt.show(mine);
            }
            if (mine.length != 2) {
                return "input    " + in + "\n        brute    " + Fmt.show(ref)
                       + "\n        yours    " + Fmt.show(mine);
            }
            int i = mine[0], k = mine[1];
            if (i < 1 || k > n || i >= k) {
                return "input    " + in + "\n        yours    " + Fmt.show(mine)
                       + "  (must be 1-based indices with i < j)";
            }
            if (a[i - 1] + a[k - 1] != target) {
                return "input    " + in + "\n        yours    " + Fmt.show(mine)
                       + " sums to " + (a[i - 1] + a[k - 1]) + ", not " + target;
            }
            return null;
        });

        j.perf("n = 30000 must finish fast (O(n) expected)", 200, () -> {
            int n = 30000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i - 1000;
            s.twoSum(a, a[n - 2] + a[n - 1]);
        });

        j.finish();
    }
}
