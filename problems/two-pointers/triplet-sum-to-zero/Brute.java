import java.util.*;

/** Obviously-correct oracle: every triple, deduped via a sorted-key set. O(n^3). */
class Brute {
    List<List<Integer>> threeSum(int[] nums) {
        Set<String> seen = new HashSet<>();
        List<List<Integer>> out = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] != 0) continue;
                    int[] t = {nums[i], nums[j], nums[k]};
                    Arrays.sort(t);
                    String key = t[0] + "," + t[1] + "," + t[2];
                    if (seen.add(key)) {
                        out.add(Arrays.asList(t[0], t[1], t[2]));
                    }
                }
            }
        }
        return out;
    }
}
