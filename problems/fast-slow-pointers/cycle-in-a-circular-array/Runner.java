public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Cycle in a Circular Array (hard)");
        Solution s = new Solution();

        j.expect("example 1: [2,-1,1,2,2]", true,
                 s.circularArrayLoop(new int[]{2, -1, 1, 2, 2}));
        j.expect("example 2: [-1,-2,-3,-4,-5,6]", false,
                 s.circularArrayLoop(new int[]{-1, -2, -3, -4, -5, 6}));
        j.expect("example 3: [1,-1,5,1,4]", true,
                 s.circularArrayLoop(new int[]{1, -1, 5, 1, 4}));
        j.expect("example 4: [-1,2] mixes directions", false,
                 s.circularArrayLoop(new int[]{-1, 2}));
        j.expect("example 5: [1] is a length-1 loop", false,
                 s.circularArrayLoop(new int[]{1}));
        j.expect("edge: [1,1] forward cycle of length 2", true,
                 s.circularArrayLoop(new int[]{1, 1}));
        j.expect("edge: [-1,-1] backward cycle of length 2", true,
                 s.circularArrayLoop(new int[]{-1, -1}));
        j.expect("edge: [2,2] -- a full lap lands back on itself, so length 1",
                 false, s.circularArrayLoop(new int[]{2, 2}));
        j.expect("edge: [-2,-2] -- same trap backwards", false,
                 s.circularArrayLoop(new int[]{-2, -2}));
        j.expect("edge: every step wraps to itself", false,
                 s.circularArrayLoop(new int[]{3, 3, 3}));
        j.expect("edge: length-1 loop must not mask a real one", true,
                 s.circularArrayLoop(new int[]{3, 1, 2}));

        j.stress("tiny random arrays, small values (dense cycles)", 4000, g -> {
            int n = g.intIn(1, 8);
            int[] a = nonZero(g, n, 4);
            return Judge.diff("nums=" + Fmt.show(a),
                    new Brute().circularArrayLoop(Fmt.copy(a)),
                    s.circularArrayLoop(Fmt.copy(a)));
        });

        j.stress("larger values so wrapping dominates", 3000, g -> {
            int n = g.intIn(2, 10);
            int[] a = nonZero(g, n, 12);
            return Judge.diff("nums=" + Fmt.show(a),
                    new Brute().circularArrayLoop(Fmt.copy(a)),
                    s.circularArrayLoop(Fmt.copy(a)));
        });

        j.stress("all one direction (no early direction exit)", 2000, g -> {
            int n = g.intIn(2, 10);
            int[] a = new int[n];
            boolean forward = g.bool();
            for (int i = 0; i < n; i++) {
                a[i] = forward ? g.intIn(1, 6) : -g.intIn(1, 6);
            }
            return Judge.diff("nums=" + Fmt.show(a),
                    new Brute().circularArrayLoop(Fmt.copy(a)),
                    s.circularArrayLoop(Fmt.copy(a)));
        });

        j.perf("n = 5000 (O(n) expected, O(n^2) will blow the budget)", 800, () -> {
            int[] a = nonZero(new Gen(21), 5000, 1000);
            s.circularArrayLoop(a);
        });

        j.finish();
    }

    /** n values uniform over [-mag, mag] excluding zero */
    static int[] nonZero(Gen g, int n, int mag) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            int v = g.intIn(1, 2 * mag);
            a[i] = v <= mag ? v : -(v - mag);
        }
        return a;
    }
}
