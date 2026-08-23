import java.util.*;

/** Obviously-correct oracle: remember every node seen. O(n) time, O(n) space. */
class Brute {
    boolean hasCycle(ListNode head) {
        Set<ListNode> seen = new HashSet<>();
        for (ListNode t = head; t != null; t = t.next) {
            if (!seen.add(t)) return true;
        }
        return false;
    }
}
