public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Minimum Window Sort (medium)");
        Solution s = new Solution();

        j.expect("example 1: [2,6,4,8,10,9,15]", 5,
                 s.findUnsortedSubarray(new int[]{2, 6, 4, 8, 10, 9, 15}));
        j.expect("example 2: already sorted", 0,
                 s.findUnsortedSubarray(new int[]{1, 2, 3, 4}));
        j.expect("example 3: single element", 0,
                 s.findUnsortedSubarray(new int[]{1}));
        j.expect("example 4: fully reversed", 3,
                 s.findUnsortedSubarray(new int[]{3, 2, 1}));
        j.expect("example 5: duplicates [1,3,2,2,2]", 4,
                 s.findUnsortedSubarray(new int[]{1, 3, 2, 2, 2}));
        j.expect("edge: all equal", 0,
                 s.findUnsortedSubarray(new int[]{2, 2, 2, 2}));
        j.expect("edge: only the last two swapped", 2,
                 s.findUnsortedSubarray(new int[]{1, 2, 4, 3}));
        j.expect("edge: only the first two swapped", 2,
                 s.findUnsortedSubarray(new int[]{2, 1, 3, 4}));
        j.expect("edge: smallest value sits at the end", 4,
                 s.findUnsortedSubarray(new int[]{2, 3, 4, 1}));

        j.stress("random arrays, tight range (many dups)", 3000, g -> {
            int n = g.intIn(1, 12);
            int[] a = g.array(n, -4, 4);
            return Judge.diff("nums=" + Fmt.show(a),
                    new Brute().findUnsortedSubarray(Fmt.copy(a)),
                    s.findUnsortedSubarray(Fmt.copy(a)));
        });

        j.stress("nearly-sorted arrays (the interesting case)", 3000, g -> {
            int n = g.intIn(2, 12);
            int[] a = g.sortedArray(n, -9, 9);
            int swaps = g.intIn(1, 2);            // perturb a sorted array a little
            for (int i = 0; i < swaps; i++) {
                int x = g.intIn(0, n - 1), y = g.intIn(0, n - 1);
                int tmp = a[x]; a[x] = a[y]; a[y] = tmp;
            }
            return Judge.diff("nums=" + Fmt.show(a),
                    new Brute().findUnsortedSubarray(Fmt.copy(a)),
                    s.findUnsortedSubarray(Fmt.copy(a)));
        });

        j.finish();
    }
}
