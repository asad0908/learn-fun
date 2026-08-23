import java.util.*;

/** Obviously-correct oracle: remember every value seen. O(n) space. */
class Brute {
    int findDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int v : nums) if (!seen.add(v)) return v;
        return -1;
    }
}
