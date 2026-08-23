public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Rearrange a LinkedList (medium)");
        Solution s = new Solution();

        j.expect("example 1: [1,2,3,4]", new int[]{1, 4, 2, 3},
                 run(s, new int[]{1, 2, 3, 4}));
        j.expect("example 2: [1,2,3,4,5]", new int[]{1, 5, 2, 4, 3},
                 run(s, new int[]{1, 2, 3, 4, 5}));
        j.expect("example 3: single node", new int[]{1}, run(s, new int[]{1}));
        j.expect("example 4: [1,2]", new int[]{1, 2}, run(s, new int[]{1, 2}));
        j.expect("example 5: [1,2,3]", new int[]{1, 3, 2},
                 run(s, new int[]{1, 2, 3}));
        j.expect("edge: six nodes", new int[]{1, 6, 2, 5, 3, 4},
                 run(s, new int[]{1, 2, 3, 4, 5, 6}));
        j.expect("edge: seven nodes", new int[]{1, 7, 2, 6, 3, 5, 4},
                 run(s, new int[]{1, 2, 3, 4, 5, 6, 7}));
        j.expect("edge: duplicate values", new int[]{2, 2, 2, 2},
                 run(s, new int[]{2, 2, 2, 2}));

        j.stress("every length from 1 to 100 vs brute", 1, g -> {
            for (int n = 1; n <= 100; n++) {
                int[] vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = i + 1;
                ListNode ref = Fmt.list(vals);
                new Brute().reorderList(ref);
                String bad = Judge.diff("a list of " + n + " nodes",
                                        Fmt.toArray(ref), run(s, vals));
                if (bad != null) return bad;
            }
            return null;
        });

        j.stress("random lists vs brute", 3000, g -> {
            int[] vals = g.array(g.intIn(1, 12), 1, 9);
            ListNode ref = Fmt.list(vals);
            new Brute().reorderList(ref);
            return Judge.diff("vals=" + Fmt.show(vals),
                              Fmt.toArray(ref), run(s, vals));
        });

        j.perf("50000 nodes (O(n) expected)", 500, () -> {
            s.reorderList(Fmt.list(new Gen(9).array(50000, 1, 1000)));
        });

        j.finish();
    }

    /** reorder a fresh list and hand back the resulting value sequence */
    static int[] run(Solution s, int[] vals) {
        ListNode head = Fmt.list(vals);
        s.reorderList(head);
        return Fmt.toArray(head);
    }
}
