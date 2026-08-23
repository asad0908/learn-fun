import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Triplet Sum to Zero (medium)");
        Solution s = new Solution();

        j.expectUnordered("example 1: [-1,0,1,2,-1,-4]",
                rows(new int[][]{{-1, -1, 2}, {-1, 0, 1}}),
                s.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        j.expectUnordered("example 2: [0,1,1] -> none",
                rows(new int[][]{}), s.threeSum(new int[]{0, 1, 1}));
        j.expectUnordered("example 3: [0,0,0]",
                rows(new int[][]{{0, 0, 0}}), s.threeSum(new int[]{0, 0, 0}));
        j.expectUnordered("edge: [0,0,0,0] still one triplet",
                rows(new int[][]{{0, 0, 0}}), s.threeSum(new int[]{0, 0, 0, 0}));
        j.expectUnordered("edge: empty input",
                rows(new int[][]{}), s.threeSum(new int[]{}));
        j.expectUnordered("edge: fewer than 3 elements",
                rows(new int[][]{}), s.threeSum(new int[]{1, -1}));
        j.expectUnordered("edge: all positive -> none",
                rows(new int[][]{}), s.threeSum(new int[]{1, 2, 3, 4}));
        j.expectUnordered("edge: heavy duplicates [-2,0,1,1,2,-2,2]",
                rows(new int[][]{{-2, 0, 2}, {-2, 1, 1}}),
                s.threeSum(new int[]{-2, 0, 1, 1, 2, -2, 2}));

        j.stress("random small arrays, tight range (lots of dups)", 2000, g -> {
            int n = g.intIn(0, 10);
            int[] a = g.array(n, -5, 5);
            return Judge.diffUnordered("nums=" + Fmt.show(a),
                    new Brute().threeSum(Fmt.copy(a)),
                    s.threeSum(Fmt.copy(a)));
        });

        j.stress("random wider range (fewer hits, tests dedup logic)", 1000, g -> {
            int n = g.intIn(3, 14);
            int[] a = g.array(n, -12, 12);
            return Judge.diffUnordered("nums=" + Fmt.show(a),
                    new Brute().threeSum(Fmt.copy(a)),
                    s.threeSum(Fmt.copy(a)));
        });

        j.perf("n = 1500 (O(n^2) expected, O(n^3) will blow the budget)", 2000, () -> {
            int[] a = new Gen(11).array(1500, -300, 300);
            s.threeSum(a);
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
