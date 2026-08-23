import java.util.*;

/** Obviously-correct oracle: copy the values out and compare ends. O(n) space. */
class Brute {
    boolean isPalindrome(ListNode head) {
        List<Integer> vals = new ArrayList<>();
        for (ListNode t = head; t != null; t = t.next) vals.add(t.val);
        for (int i = 0, k = vals.size() - 1; i < k; i++, k--) {
            if (!vals.get(i).equals(vals.get(k))) return false;
        }
        return true;
    }
}
