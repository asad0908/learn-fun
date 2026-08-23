public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Middle of the LinkedList (easy)");
        Solution s = new Solution();

        j.expect("example 1: [1,2,3,4,5]", 2, at(s, new int[]{1, 2, 3, 4, 5}));
        j.expect("example 2: [1,2,3,4,5,6] -> second middle", 3,
                 at(s, new int[]{1, 2, 3, 4, 5, 6}));
        j.expect("example 3: single node", 0, at(s, new int[]{1}));
        j.expect("example 4: [1,2] -> second middle", 1, at(s, new int[]{1, 2}));
        j.expect("edge: three nodes", 1, at(s, new int[]{1, 2, 3}));
        j.expect("edge: four nodes", 2, at(s, new int[]{1, 2, 3, 4}));
        j.expect("edge: all values identical", 2, at(s, new int[]{7, 7, 7, 7, 7}));

        j.stress("every length from 1 to 200 vs brute", 1, g -> {
            for (int n = 1; n <= 200; n++) {
                int[] vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = i + 1;
                int mine = at(s, vals);
                ListNode head = Fmt.list(vals);
                int ref = Fmt.indexOfNode(head, new Brute().middleNode(head));
                if (mine != ref) {
                    return "input    a list of " + n + " nodes"
                           + "\n        brute    index " + ref
                           + "\n        yours    " + describe(mine);
                }
            }
            return null;
        });

        j.stress("random lists vs brute", 3000, g -> {
            int[] vals = g.array(g.intIn(1, 12), 1, 100);
            int mine = at(s, vals);
            ListNode head = Fmt.list(vals);
            int ref = Fmt.indexOfNode(head, new Brute().middleNode(head));
            if (mine == ref) return null;
            return "input    " + Fmt.show(vals)
                   + "\n        brute    index " + ref
                   + "\n        yours    " + describe(mine);
        });

        j.finish();
    }

    /** index of the returned node, -1 for null, -2 for a node not in the list */
    static int at(Solution s, int[] vals) {
        ListNode[] nodes = Fmt.listNodes(vals, -1);
        ListNode head = nodes.length == 0 ? null : nodes[0];
        ListNode got = s.middleNode(head);
        if (got == null) return -1;
        for (int i = 0; i < nodes.length; i++) if (nodes[i] == got) return i;
        return -2;
    }

    static String describe(int idx) {
        if (idx == -1) return "null";
        if (idx == -2) return "a node that is not part of the input list";
        return "index " + idx;
    }
}
