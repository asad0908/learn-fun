import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Remove Duplicates (easy)");
        Solution s = new Solution();

        j.expect("example 1: [1,1,2]", "k=2 prefix=[1, 2]",
                 run(s, new int[]{1, 1, 2}));
        j.expect("example 2: [0,0,1,1,1,2,2,3,3,4]", "k=5 prefix=[0, 1, 2, 3, 4]",
                 run(s, new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));
        j.expect("example 3: single element", "k=1 prefix=[7]",
                 run(s, new int[]{7}));
        j.expect("edge: all identical", "k=1 prefix=[5]",
                 run(s, new int[]{5, 5, 5, 5, 5}));
        j.expect("edge: already distinct", "k=4 prefix=[1, 2, 3, 4]",
                 run(s, new int[]{1, 2, 3, 4}));
        j.expect("edge: negatives", "k=3 prefix=[-3, -1, 0]",
                 run(s, new int[]{-3, -3, -1, 0, 0}));

        j.stress("random sorted arrays vs brute", 3000, g -> {
            int n = g.intIn(1, 14);
            int[] a = g.sortedArray(n, -4, 4);       // tight range => many dups
            String mine = run(s, a);
            int[] refArr = Fmt.copy(a);
            int refK = new Brute().removeDuplicates(refArr);
            String ref = fmt(refArr, refK);
            return Judge.diff("nums=" + Fmt.show(a), ref, mine);
        });

        j.perf("n = 30000", 200, () -> {
            int[] a = new int[30000];
            for (int i = 0; i < a.length; i++) a[i] = i / 150;   // heavy dups
            s.removeDuplicates(a);
        });

        j.finish();
    }

    static String run(Solution s, int[] a) {
        int[] c = Fmt.copy(a);
        int k = s.removeDuplicates(c);
        return fmt(c, k);
    }

    /** only the first k slots are meaningful -- ignore the tail */
    static String fmt(int[] arr, int k) {
        if (k < 0 || k > arr.length) return "k=" + k + " (out of range!)";
        return "k=" + k + " prefix=" + Arrays.toString(Arrays.copyOf(arr, k));
    }
}
