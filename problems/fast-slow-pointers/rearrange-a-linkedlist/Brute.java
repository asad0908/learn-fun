import java.util.*;

/** Obviously-correct oracle: collect the nodes, then relink. O(n) space. */
class Brute {
    void reorderList(ListNode head) {
        if (head == null) return;
        List<ListNode> nodes = new ArrayList<>();
        for (ListNode t = head; t != null; t = t.next) nodes.add(t);
        int i = 0, k = nodes.size() - 1;
        ListNode cur = null;
        boolean front = true;
        while (i <= k) {
            ListNode pick = front ? nodes.get(i++) : nodes.get(k--);
            front = !front;
            if (cur != null) cur.next = pick;
            cur = pick;
        }
        cur.next = null;
    }
}
