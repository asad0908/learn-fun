import java.util.*;

/**
 * Shared test infrastructure for the local judge. Compiled alongside every
 * problem, so Runner.java can just use Judge / Gen / ListNode / TreeNode.
 *
 * The point of this file: you should never have to hand-write test cases.
 * Write a Brute (obviously-correct, slow) implementation, describe how to
 * generate a random input, and let Judge.stress() hunt for the input where
 * your clever Solution disagrees with it.
 */
public class Lib {
    private Lib() {}
}

/* ------------------------------------------------------------------ shapes */

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val; this.left = left; this.right = right;
    }
}

/* -------------------------------------------------- random input generation */

class Gen {
    final Random r;

    Gen(long seed) { this.r = new Random(seed); }

    /** inclusive on both ends */
    int intIn(int lo, int hi) { return lo + r.nextInt(hi - lo + 1); }

    boolean bool() { return r.nextBoolean(); }

    /** one of the given values */
    int pick(int... choices) { return choices[r.nextInt(choices.length)]; }

    <T> T pick(List<T> choices) { return choices.get(r.nextInt(choices.size())); }

    int[] array(int n, int lo, int hi) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = intIn(lo, hi);
        return a;
    }

    int[] sortedArray(int n, int lo, int hi) {
        int[] a = array(n, lo, hi);
        Arrays.sort(a);
        return a;
    }

    /** strictly increasing, needs hi-lo+1 >= n */
    int[] distinctSortedArray(int n, int lo, int hi) {
        Set<Integer> s = new HashSet<>();
        while (s.size() < n) s.add(intIn(lo, hi));
        int[] a = new int[n];
        int i = 0;
        for (int v : s) a[i++] = v;
        Arrays.sort(a);
        return a;
    }

    int[] permutation(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        shuffle(a);
        return a;
    }

    void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        }
    }

    int[][] matrix(int rows, int cols, int lo, int hi) {
        int[][] m = new int[rows][];
        for (int i = 0; i < rows; i++) m[i] = array(cols, lo, hi);
        return m;
    }

    String string(int n, String alphabet) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        return sb.toString();
    }

    /** lowercase string over the first `letters` letters -- small alphabets
     *  make collisions (and therefore interesting cases) far more likely */
    String lower(int n, int letters) {
        return string(n, "abcdefghijklmnopqrstuvwxyz".substring(0, letters));
    }

    ListNode list(int[] a) {
        ListNode dummy = new ListNode(), t = dummy;
        for (int v : a) { t.next = new ListNode(v); t = t.next; }
        return dummy.next;
    }

    /** random binary tree from a level-order array with `nullChance`% holes */
    TreeNode tree(int n, int lo, int hi, int nullChance) {
        if (n == 0) return null;
        Integer[] vals = new Integer[n];
        vals[0] = intIn(lo, hi);
        for (int i = 1; i < n; i++) {
            vals[i] = (intIn(1, 100) <= nullChance) ? null : intIn(lo, hi);
        }
        return Fmt.tree(vals);
    }

    /** random BST built by inserting a shuffled permutation of distinct values */
    TreeNode bst(int n, int lo, int hi) {
        if (n == 0) return null;
        int[] vals = distinctSortedArray(n, lo, hi);
        shuffle(vals);
        TreeNode root = null;
        for (int v : vals) root = bstInsert(root, v);
        return root;
    }

    private TreeNode bstInsert(TreeNode node, int v) {
        if (node == null) return new TreeNode(v);
        if (v < node.val) node.left = bstInsert(node.left, v);
        else node.right = bstInsert(node.right, v);
        return node;
    }

    /** undirected edge list on n nodes, no self loops, no duplicate edges */
    int[][] edges(int n, int m) {
        Set<Long> seen = new HashSet<>();
        List<int[]> out = new ArrayList<>();
        int guard = 0;
        while (out.size() < m && guard++ < m * 40) {
            int u = r.nextInt(n), v = r.nextInt(n);
            if (u == v) continue;
            long key = (long) Math.min(u, v) * n + Math.max(u, v);
            if (!seen.add(key)) continue;
            out.add(new int[]{u, v});
        }
        return out.toArray(new int[0][]);
    }
}

/* ------------------------------------------------------- printing / parsing */

class Fmt {
    /** canonical string for anything we might compare or print */
    static String show(Object o) {
        if (o == null) return "null";
        if (o instanceof ListNode) {
            StringBuilder sb = new StringBuilder("[");
            int guard = 0;
            for (ListNode t = (ListNode) o; t != null; t = t.next) {
                if (guard++ > 10000) { sb.append("...CYCLE"); break; }
                if (sb.length() > 1) sb.append(", ");
                sb.append(t.val);
            }
            return sb.append("]").toString();
        }
        if (o instanceof TreeNode) return show(treeToArray((TreeNode) o));
        if (o instanceof int[]) return Arrays.toString((int[]) o);
        if (o instanceof char[]) return Arrays.toString((char[]) o);
        if (o instanceof long[]) return Arrays.toString((long[]) o);
        if (o instanceof boolean[]) return Arrays.toString((boolean[]) o);
        if (o instanceof Object[]) return Arrays.deepToString((Object[]) o);
        if (o instanceof Collection<?>) {
            StringBuilder sb = new StringBuilder("[");
            for (Object e : (Collection<?>) o) {
                if (sb.length() > 1) sb.append(", ");
                sb.append(show(e));
            }
            return sb.append("]").toString();
        }
        if (o instanceof Map<?, ?>) {
            StringBuilder sb = new StringBuilder("{");
            List<String> parts = new ArrayList<>();
            for (Map.Entry<?, ?> e : ((Map<?, ?>) o).entrySet()) {
                parts.add(show(e.getKey()) + "=" + show(e.getValue()));
            }
            Collections.sort(parts);
            return sb.append(String.join(", ", parts)).append("}").toString();
        }
        if (o instanceof String) return "\"" + o + "\"";
        return String.valueOf(o);
    }

    /** build a tree from a LeetCode-style level-order array (null = hole) */
    static TreeNode tree(Integer... vals) {
        if (vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Deque<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        int i = 1;
        while (i < vals.length && !q.isEmpty()) {
            TreeNode cur = q.poll();
            if (i < vals.length) {
                if (vals[i] != null) { cur.left = new TreeNode(vals[i]); q.add(cur.left); }
                i++;
            }
            if (i < vals.length) {
                if (vals[i] != null) { cur.right = new TreeNode(vals[i]); q.add(cur.right); }
                i++;
            }
        }
        return root;
    }

    static List<Integer> treeToArray(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) return out;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode n = q.poll();
            if (n == null) { out.add(null); continue; }
            out.add(n.val);
            q.add(n.left);
            q.add(n.right);
        }
        while (!out.isEmpty() && out.get(out.size() - 1) == null) out.remove(out.size() - 1);
        return out;
    }

    static ListNode list(int... a) {
        ListNode dummy = new ListNode(), t = dummy;
        for (int v : a) { t.next = new ListNode(v); t = t.next; }
        return dummy.next;
    }

    /**
     * Every node of a list built from vals, with the tail linked back to index
     * `pos` (-1 for no cycle). Element 0 is the head. Handing back the whole
     * array lets a Runner name nodes by index -- needed when the answer IS a
     * node, since cyclic lists cannot be compared by printing them.
     */
    static ListNode[] listNodes(int[] vals, int pos) {
        ListNode[] nodes = new ListNode[vals.length];
        for (int i = 0; i < vals.length; i++) nodes[i] = new ListNode(vals[i]);
        for (int i = 0; i + 1 < vals.length; i++) nodes[i].next = nodes[i + 1];
        if (pos >= 0 && vals.length > 0) nodes[vals.length - 1].next = nodes[pos];
        return nodes;
    }

    /** LeetCode-style cyclic list: tail links back to index pos (-1 = acyclic). */
    static ListNode listWithCycle(int[] vals, int pos) {
        ListNode[] n = listNodes(vals, pos);
        return n.length == 0 ? null : n[0];
    }

    /** 0-based position of `node` in the chain from head, or -1. Cycle-safe. */
    static int indexOfNode(ListNode head, ListNode node) {
        int i = 0;
        for (ListNode t = head; t != null && i < 100000; t = t.next, i++) {
            if (t == node) return i;
        }
        return -1;
    }

    static int[] toArray(ListNode head) {
        List<Integer> out = new ArrayList<>();
        for (ListNode t = head; t != null; t = t.next) out.add(t.val);
        int[] a = new int[out.size()];
        for (int i = 0; i < a.length; i++) a[i] = out.get(i);
        return a;
    }

    /** deep clone so a destructive Solution cannot poison the Brute run */
    static int[] copy(int[] a) { return a == null ? null : a.clone(); }

    static int[][] copy(int[][] a) {
        if (a == null) return null;
        int[][] b = new int[a.length][];
        for (int i = 0; i < a.length; i++) b[i] = a[i].clone();
        return b;
    }

    /** Acyclic lists only -- build cyclic inputs fresh per call instead. */
    static ListNode copy(ListNode head) {
        if (head == null) return null;
        ListNode dummy = new ListNode(), t = dummy;
        int guard = 0;
        for (ListNode c = head; c != null; c = c.next) {
            if (++guard > 1_000_000) {
                throw new IllegalArgumentException(
                    "Fmt.copy(ListNode) walked a cyclic list -- build the input "
                    + "twice with Fmt.listWithCycle instead of copying it");
            }
            t.next = new ListNode(c.val);
            t = t.next;
        }
        return dummy.next;
    }

    static TreeNode copy(TreeNode n) {
        if (n == null) return null;
        return new TreeNode(n.val, copy(n.left), copy(n.right));
    }

    /** sort a List<List<Integer>> answer into a canonical form (order-insensitive) */
    static String canonical(List<List<Integer>> rows) {
        if (rows == null) return "null";
        List<String> outer = new ArrayList<>();
        for (List<Integer> row : rows) {
            if (row == null) { outer.add("null"); continue; }
            List<Integer> r = new ArrayList<>(row);
            Collections.sort(r);
            outer.add(r.toString());
        }
        Collections.sort(outer);
        return outer.toString();
    }

    static String canonicalStrings(Collection<String> items) {
        List<String> s = new ArrayList<>(items);
        Collections.sort(s);
        return s.toString();
    }
}

/* -------------------------------------------------------------------- judge */

class Judge {
    private int pass = 0;
    private final List<String> failures = new ArrayList<>();
    private final String title;
    private final long started = System.nanoTime();

    Judge(String title) {
        this.title = title;
        System.out.println("== " + title);
    }

    /** a named fixed case */
    void expect(String label, Object expected, Object actual) {
        String e = Fmt.show(expected), a = Fmt.show(actual);
        if (e.equals(a)) {
            pass++;
            System.out.println("  ok    " + label);
        } else {
            failures.add(label + "\n        expected " + e + "\n        got      " + a);
            System.out.println("  FAIL  " + label);
            System.out.println("        expected " + e);
            System.out.println("        got      " + a);
        }
    }

    /** answer is a list of groups whose order (inner and outer) is irrelevant */
    void expectUnordered(String label, List<List<Integer>> expected,
                         List<List<Integer>> actual) {
        String e = Fmt.canonical(expected), a = Fmt.canonical(actual);
        if (e.equals(a)) {
            pass++;
            System.out.println("  ok    " + label);
        } else {
            failures.add(label + "\n        expected " + e + "\n        got      " + a);
            System.out.println("  FAIL  " + label);
            System.out.println("        expected " + e);
            System.out.println("        got      " + a);
        }
    }

    void expectTrue(String label, boolean cond) { expect(label, true, cond); }

    /**
     * The workhorse. Runs `rounds` random trials; each trial gets a fresh Gen
     * seeded deterministically, so a failure is always reproducible. `trial`
     * must return null when Solution and Brute agree, or a human-readable
     * description of the input when they disagree.
     */
    void stress(String label, int rounds, Trial trial) {
        for (int seed = 1; seed <= rounds; seed++) {
            String bad;
            try {
                bad = trial.run(new Gen(seed));
            } catch (Throwable ex) {
                bad = "threw " + ex.getClass().getSimpleName()
                      + (ex.getMessage() == null ? "" : ": " + ex.getMessage());
            }
            if (bad != null) {
                failures.add(label + " (seed " + seed + ")\n        " + bad);
                System.out.println("  FAIL  " + label + "  [seed " + seed + ", after "
                                   + (seed - 1) + " passing rounds]");
                System.out.println("        " + bad.replace("\n", "\n        "));
                System.out.println("        reproduce: new Gen(" + seed + ")");
                return;                       // first counterexample is the useful one
            }
        }
        pass++;
        System.out.println("  ok    " + label + "  (" + rounds + " random rounds)");
    }

    /** helper for stress bodies: null when equal, else a diff string */
    static String diff(String input, Object expected, Object actual) {
        String e = Fmt.show(expected), a = Fmt.show(actual);
        if (e.equals(a)) return null;
        return "input    " + input + "\n        brute    " + e + "\n        yours    " + a;
    }

    /** order-insensitive variant of diff, for list-of-groups answers */
    static String diffUnordered(String input, List<List<Integer>> expected,
                                List<List<Integer>> actual) {
        String e = Fmt.canonical(expected), a = Fmt.canonical(actual);
        if (e.equals(a)) return null;
        return "input    " + input + "\n        brute    " + e + "\n        yours    " + a;
    }

    /** fail the run if `body` takes longer than `millis` -- catches accidental O(n^2) */
    void perf(String label, long millis, Runnable body) {
        long t0 = System.nanoTime();
        try {
            body.run();
        } catch (Throwable ex) {
            failures.add(label + " threw " + ex);
            System.out.println("  FAIL  " + label + " threw " + ex);
            return;
        }
        long ms = (System.nanoTime() - t0) / 1_000_000;
        if (ms > millis) {
            failures.add(label + ": took " + ms + "ms, budget " + millis + "ms"
                         + " -- your complexity is probably too high");
            System.out.println("  FAIL  " + label + "  " + ms + "ms > " + millis + "ms budget");
        } else {
            pass++;
            System.out.println("  ok    " + label + "  (" + ms + "ms, budget " + millis + "ms)");
        }
    }

    /** call last; exits the JVM with 0 on success, 1 on any failure */
    void finish() {
        long ms = (System.nanoTime() - started) / 1_000_000;
        System.out.println();
        if (failures.isEmpty()) {
            System.out.println("PASS  " + pass + " checks  (" + ms + "ms)");
            System.exit(0);
        }
        System.out.println("FAIL  " + failures.size() + " of " + (pass + failures.size())
                           + " checks  (" + ms + "ms)");
        for (String f : failures) System.out.println("  - " + f);
        System.exit(1);
    }

    interface Trial {
        /** return null if Solution matches Brute, else describe the counterexample */
        String run(Gen g) throws Exception;
    }
}
