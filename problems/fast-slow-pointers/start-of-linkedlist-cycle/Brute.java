import java.util.*;

/** Obviously-correct oracle: first node seen twice is the cycle entry. */
class Brute {
    ListNode detectCycle(ListNode head) {
        Set<ListNode> seen = new HashSet<>();
        for (ListNode t = head; t != null; t = t.next) {
            if (!seen.add(t)) return t;
        }
        return null;
    }
}
