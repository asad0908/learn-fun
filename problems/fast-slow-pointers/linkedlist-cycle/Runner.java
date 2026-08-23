public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("LinkedList Cycle (easy)");
        Solution s = new Solution();

        j.expect("example 1: [3,2,0,-4] cycle at index 1", true,
                 s.hasCycle(Fmt.listWithCycle(new int[]{3, 2, 0, -4}, 1)));
        j.expect("example 2: [1,2] cycle at index 0", true,
                 s.hasCycle(Fmt.listWithCycle(new int[]{1, 2}, 0)));
        j.expect("example 3: [1] no cycle", false,
                 s.hasCycle(Fmt.listWithCycle(new int[]{1}, -1)));
        j.expect("edge: empty list", false, s.hasCycle(null));
        j.expect("edge: single node pointing at itself", true,
                 s.hasCycle(Fmt.listWithCycle(new int[]{1}, 0)));
        j.expect("edge: long acyclic list", false,
                 s.hasCycle(Fmt.listWithCycle(new int[]{1, 2, 3, 4, 5, 6, 7}, -1)));
        j.expect("edge: cycle is the whole list", true,
                 s.hasCycle(Fmt.listWithCycle(new int[]{1, 2, 3, 4}, 0)));
        j.expect("edge: cycle is only the tail", true,
                 s.hasCycle(Fmt.listWithCycle(new int[]{1, 2, 3, 4}, 3)));

        // A cyclic list cannot be deep-copied, so build the input twice.
        j.stress("random lists, ~half with a cycle", 4000, g -> {
            int n = g.intIn(0, 10);
            int pos = (n == 0 || g.bool()) ? -1 : g.intIn(0, n - 1);
            int[] vals = g.array(n, -9, 9);
            String in = "vals=" + Fmt.show(vals) + " cyclePos=" + pos;
            return Judge.diff(in,
                    new Brute().hasCycle(Fmt.listWithCycle(vals, pos)),
                    s.hasCycle(Fmt.listWithCycle(vals, pos)));
        });

        j.perf("10000 nodes, no cycle", 300, () -> {
            s.hasCycle(Fmt.listWithCycle(new Gen(4).array(10000, -100, 100), -1));
        });

        j.finish();
    }
}
