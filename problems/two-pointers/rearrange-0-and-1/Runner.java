public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Rearrange 0 and 1");
        Solution s = new Solution();

        j.expect("example 1", new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                 run(s, new int[]{0, 1, 0, 1, 0, 0, 1, 1, 1, 0}));
        j.expect("example 2: all ones", new int[]{1, 1, 1, 1},
                 run(s, new int[]{1, 1, 1, 1}));
        j.expect("example 3: single element", new int[]{0}, run(s, new int[]{0}));
        j.expect("edge: all zeros", new int[]{0, 0, 0}, run(s, new int[]{0, 0, 0}));
        j.expect("edge: already segregated", new int[]{0, 0, 1, 1},
                 run(s, new int[]{0, 0, 1, 1}));
        j.expect("edge: fully reversed", new int[]{0, 0, 1, 1},
                 run(s, new int[]{1, 1, 0, 0}));

        j.stress("random 0/1 arrays vs brute", 3000, g -> {
            int n = g.intIn(1, 14);
            int[] a = g.array(n, 0, 1);
            int[] mine = Fmt.copy(a);
            s.segregate(mine);
            int[] ref = Fmt.copy(a);
            new Brute().segregate(ref);
            return Judge.diff("arr=" + Fmt.show(a), ref, mine);
        });

        j.perf("n = 1000000 in place", 300, () -> {
            int[] a = new Gen(7).array(1000000, 0, 1);
            s.segregate(a);
        });

        j.finish();
    }

    /** run in place on a copy and hand back the mutated array */
    static int[] run(Solution s, int[] a) {
        int[] c = Fmt.copy(a);
        s.segregate(c);
        return c;
    }
}
