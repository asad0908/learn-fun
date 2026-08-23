import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Quadruple Sum to Target (medium)");
        Solution s = new Solution();

        j.expectUnordered("example 1: [1,0,-1,0,-2,2] t=0",
                rows(new int[][]{{-2, -1, 1, 2}, {-2, 0, 0, 2}, {-1, 0, 0, 1}}),
                s.fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        j.expectUnordered("example 2: [2,2,2,2,2] t=8",
                rows(new int[][]{{2, 2, 2, 2}}),
                s.fourSum(new int[]{2, 2, 2, 2, 2}, 8));
        j.expectUnordered("example 3: fewer than 4 elements",
                rows(new int[][]{}), s.fourSum(new int[]{1, 2, 3}, 6));
        j.expectUnordered("edge: empty input",
                rows(new int[][]{}), s.fourSum(new int[]{}, 0));
        j.expectUnordered("edge: exactly 4 elements that work",
                rows(new int[][]{{1, 2, 3, 4}}),
                s.fourSum(new int[]{1, 2, 3, 4}, 10));
        j.expectUnordered("edge: no quadruplet matches",
                rows(new int[][]{}), s.fourSum(new int[]{1, 2, 3, 4}, 100));
        j.expectUnordered("edge: all zeros",
                rows(new int[][]{{0, 0, 0, 0}}),
                s.fourSum(new int[]{0, 0, 0, 0, 0}, 0));

        // OVERFLOW BAIT: 4 * 10^9 does not fit in an int
        j.expectUnordered("edge: overflow -- large positives, huge target",
                rows(new int[][]{}),
                s.fourSum(new int[]{1000000000, 1000000000, 1000000000,
                                    1000000000}, -294967296));
        j.expectUnordered("edge: large magnitudes that really do sum to target",
                rows(new int[][]{{-1000000000, -1000000000, 1000000000, 1000000000}}),
                s.fourSum(new int[]{-1000000000, 1000000000, -1000000000,
                                    1000000000}, 0));

        j.stress("random small arrays, tight range (many dups)", 1500, g -> {
            int n = g.intIn(0, 9);
            int[] a = g.array(n, -4, 4);
            int target = g.intIn(-8, 8);
            return Judge.diffUnordered("nums=" + Fmt.show(a) + " target=" + target,
                    new Brute().fourSum(Fmt.copy(a), target),
                    s.fourSum(Fmt.copy(a), target));
        });

        j.stress("random arrays with extreme values (overflow hunting)", 500, g -> {
            int n = g.intIn(4, 8);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = g.pick(1000000000, -1000000000, 999999999, -999999999, 0, 1);
            }
            int target = g.pick(0, 1000000000, -1000000000, 2, -2);
            return Judge.diffUnordered("nums=" + Fmt.show(a) + " target=" + target,
                    new Brute().fourSum(Fmt.copy(a), target),
                    s.fourSum(Fmt.copy(a), target));
        });

        j.perf("n = 200 (O(n^3) expected, O(n^4) will blow the budget)", 2500, () -> {
            int[] a = new Gen(13).array(200, -50, 50);
            s.fourSum(a, 0);
        });

        j.finish();
    }

    static List<List<Integer>> rows(int[][] raw) {
        List<List<Integer>> out = new ArrayList<>();
        for (int[] r : raw) {
            List<Integer> row = new ArrayList<>();
            for (int v : r) row.add(v);
            out.add(row);
        }
        return out;
    }
}
