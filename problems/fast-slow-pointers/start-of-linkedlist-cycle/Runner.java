public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Start of LinkedList Cycle (medium)");
        Solution s = new Solution();

        j.expect("example 1: [3,2,0,-4] cycle at index 1", 1,
                 at(s, new int[]{3, 2, 0, -4}, 1));
        j.expect("example 2: [1,2] cycle at index 0", 0,
                 at(s, new int[]{1, 2}, 0));
        j.expect("example 3: [1] no cycle", -1, at(s, new int[]{1}, -1));
        j.expect("edge: empty list", -1, at(s, new int[]{}, -1));
        j.expect("edge: single node pointing at itself", 0,
                 at(s, new int[]{1}, 0));
        j.expect("edge: cycle is the whole list", 0,
                 at(s, new int[]{1, 2, 3, 4}, 0));
        j.expect("edge: cycle is only the tail node", 3,
                 at(s, new int[]{1, 2, 3, 4}, 3));
        j.expect("edge: long tail before the cycle", 5,
                 at(s, new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 5));
        j.expect("edge: duplicate values must not fool you", 2,
                 at(s, new int[]{7, 7, 7, 7}, 2));

        j.stress("random lists, ~half with a cycle", 4000, g -> {
            int n = g.intIn(0, 10);
            int pos = (n == 0 || g.bool()) ? -1 : g.intIn(0, n - 1);
            int[] vals = g.array(n, -9, 9);
            String in = "vals=" + Fmt.show(vals) + " cyclePos=" + pos;

            // rebuild per call: cyclic lists cannot be copied, and identity matters
            int mine = at(s, vals, pos);
            ListNode[] refNodes = Fmt.listNodes(vals, pos);
            ListNode head = refNodes.length == 0 ? null : refNodes[0];
            ListNode refNode = new Brute().detectCycle(head);
            int ref = Fmt.indexOfNode(head, refNode);

            if (mine == ref) return null;
            return "input    " + in
                   + "\n        brute    " + describe(ref)
                   + "\n        yours    " + describe(mine);
        });

        j.finish();
    }

    /** index of the node Solution returns, -1 for null, -2 for a foreign node */
    static int at(Solution s, int[] vals, int pos) {
        ListNode[] nodes = Fmt.listNodes(vals, pos);
        ListNode head = nodes.length == 0 ? null : nodes[0];
        ListNode got = s.detectCycle(head);
        if (got == null) return -1;
        for (int i = 0; i < nodes.length; i++) if (nodes[i] == got) return i;
        return -2;
    }

    static String describe(int idx) {
        if (idx == -1) return "null (no cycle)";
        if (idx == -2) return "a node that is not part of the input list";
        return "the node at index " + idx;
    }
}
