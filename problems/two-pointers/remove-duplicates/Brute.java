import java.util.*;

/** Obviously-correct oracle: collect distinct values, then write them back. */
class Brute {
    int removeDuplicates(int[] nums) {
        List<Integer> distinct = new ArrayList<>();
        for (int v : nums) {
            if (distinct.isEmpty() || distinct.get(distinct.size() - 1) != v) {
                distinct.add(v);
            }
        }
        for (int i = 0; i < distinct.size(); i++) nums[i] = distinct.get(i);
        return distinct.size();
    }
}
