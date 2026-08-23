import java.util.*;

/**
 * Obviously-correct oracle: start a walk at every index and insist every step
 * keeps the starting direction. Any valid cycle is found when the start is on
 * it, so this is exhaustive. O(n^2).
 */
class Brute {
    boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        for (int start = 0; start < n; start++) {
            int dir = nums[start] > 0 ? 1 : -1;
            Map<Integer, Integer> stepAt = new HashMap<>();
            int cur = start, step = 0;
            while (true) {
                if ((nums[cur] > 0 ? 1 : -1) != dir) break;   // changed direction
                stepAt.put(cur, step);
                int nxt = next(cur, nums, n);
                if (nxt == cur) break;                        // length-1 self loop
                step++;
                Integer seen = stepAt.get(nxt);
                if (seen != null) {
                    if (step - seen > 1) return true;
                    break;
                }
                cur = nxt;
            }
        }
        return false;
    }

    private int next(int i, int[] nums, int n) {
        int k = (i + nums[i]) % n;
        return k < 0 ? k + n : k;
    }
}
