public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Squaring a Sorted Array (easy)");
        Solution s = new Solution();

        j.expect("example 1", new int[]{0, 1, 9, 16, 100},
                 s.sortedSquares(new int[]{-4, -1, 0, 3, 10}));
        j.expect("example 2", new int[]{4, 9, 9, 49, 121},
                 s.sortedSquares(new int[]{-7, -3, 2, 3, 11}));
        j.expect("example 3: all negative", new int[]{4, 16, 25},
                 s.sortedSquares(new int[]{-5, -4, -2}));
        j.expect("edge: single element", new int[]{9},
                 s.sortedSquares(new int[]{-3}));
        j.expect("edge: all non-negative", new int[]{0, 1, 4},
                 s.sortedSquares(new int[]{0, 1, 2}));
        j.expect("edge: symmetric around zero", new int[]{1, 1, 4, 4},
                 s.sortedSquares(new int[]{-2, -1, 1, 2}));

        j.stress("random sorted arrays (many negatives) vs brute", 3000, g -> {
            int n = g.intIn(1, 14);
            int[] a = g.sortedArray(n, -9, 9);
            return Judge.diff("nums=" + Fmt.show(a),
                              new Brute().sortedSquares(Fmt.copy(a)),
                              s.sortedSquares(Fmt.copy(a)));
        });

        j.finish();
    }
}
