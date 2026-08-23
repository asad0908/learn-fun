public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Dutch National Flag Problem (medium)");
        Solution s = new Solution();

        j.expect("example 1: [2,0,2,1,1,0]", new int[]{0, 0, 1, 1, 2, 2},
                 run(s, new int[]{2, 0, 2, 1, 1, 0}));
        j.expect("example 2: [2,0,1]", new int[]{0, 1, 2}, run(s, new int[]{2, 0, 1}));
        j.expect("example 3: single element", new int[]{0}, run(s, new int[]{0}));
        j.expect("edge: already sorted", new int[]{0, 1, 2}, run(s, new int[]{0, 1, 2}));
        j.expect("edge: fully reversed", new int[]{0, 1, 2}, run(s, new int[]{2, 1, 0}));
        j.expect("edge: no 1s at all", new int[]{0, 0, 2, 2},
                 run(s, new int[]{2, 0, 2, 0}));
        j.expect("edge: all the same value", new int[]{1, 1, 1},
                 run(s, new int[]{1, 1, 1}));

        j.stress("random 0/1/2 arrays vs brute", 3000, g -> {
            int n = g.intIn(1, 14);
            int[] a = g.array(n, 0, 2);
            int[] mine = Fmt.copy(a);
            s.sortColors(mine);
            int[] ref = Fmt.copy(a);
            new Brute().sortColors(ref);
            return Judge.diff("nums=" + Fmt.show(a), ref, mine);
        });

        j.finish();
    }

    static int[] run(Solution s, int[] a) {
        int[] c = Fmt.copy(a);
        s.sortColors(c);
        return c;
    }
}
