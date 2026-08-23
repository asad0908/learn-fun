/** Obviously-correct oracle: count the nodes, then walk to n/2. Two passes. */
class Brute {
    ListNode middleNode(ListNode head) {
        int n = 0;
        for (ListNode t = head; t != null; t = t.next) n++;
        ListNode t = head;
        for (int i = 0; i < n / 2; i++) t = t.next;
        return t;
    }
}
