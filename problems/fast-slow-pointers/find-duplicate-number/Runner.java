import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Find Duplicate Number");
        Solution s = new Solution();

        j.expect("example 1: [1,3,4,2,2]", 2,
                 s.findDuplicate(new int[]{1, 3, 4, 2, 2}));
        j.expect("example 2: [3,1,3,4,2]", 3,
                 s.findDuplicate(new int[]{3, 1, 3, 4, 2}));
        j.expect("example 3: all the same value", 3,
                 s.findDuplicate(new int[]{3, 3, 3, 3, 3}));
        j.expect("edge: smallest possible input [1,1]", 1,
                 s.findDuplicate(new int[]{1, 1}));
        j.expect("edge: duplicate at the front", 1,
                 s.findDuplicate(new int[]{1, 1, 2, 3, 4}));
        j.expect("edge: duplicate at the back", 4,
                 s.findDuplicate(new int[]{1, 2, 3, 4, 4}));
        j.expect("edge: duplicate is n", 5,
                 s.findDuplicate(new int[]{5, 1, 2, 3, 4, 5}));

        // the input must survive the call untouched
        j.stress("input is not modified", 500, g -> {
            int[] a = build(g, false);
            int[] before = Fmt.copy(a);
            s.findDuplicate(a);
            if (Arrays.equals(before, a)) return null;
            return "input    " + Fmt.show(before)
                   + "\n        after your call it became " + Fmt.show(a)
                   + "\n        (the array must not be modified)";
        });

        j.stress("random valid arrays, duplicate appears twice", 3000, g -> {
            int[] a = build(g, false);
            return Judge.diff("nums=" + Fmt.show(a),
                    new Brute().findDuplicate(Fmt.copy(a)),
                    s.findDuplicate(Fmt.copy(a)));
        });

        j.stress("random valid arrays, duplicate repeated many times", 3000, g -> {
            int[] a = build(g, true);
            return Judge.diff("nums=" + Fmt.show(a),
                    new Brute().findDuplicate(Fmt.copy(a)),
                    s.findDuplicate(Fmt.copy(a)));
        });

        j.perf("n = 100000 (O(n) expected)", 500, () -> {
            int n = 100000;
            int[] a = new int[n + 1];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            a[n] = 777;                       // 777 is the duplicate
            s.findDuplicate(a);
        });

        j.finish();
    }

    /**
     * A valid input: length n+1, values in [1,n], exactly one value repeated.
     * heavy=true makes that value fill several slots instead of just two.
     */
    static int[] build(Gen g, boolean heavy) {
        int n = g.intIn(1, 10);
        int[] a = new int[n + 1];
        for (int i = 0; i < n; i++) a[i] = i + 1;      // 1..n once each
        int dup = g.intIn(1, n);
        a[n] = dup;                                    // ...plus one repeat
        if (heavy) {
            // overwrite other slots with dup, but never the last copy of a
            // value that would then vanish and break the guarantee
            for (int i = 0; i < n; i++) {
                if (a[i] != dup && g.intIn(1, 100) <= 30) a[i] = dup;
            }
        }
        g.shuffle(a);
        return a;
    }
}
