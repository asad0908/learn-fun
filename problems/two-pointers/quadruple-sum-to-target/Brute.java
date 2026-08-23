import java.util.*;

/** Obviously-correct oracle: every 4-tuple, long arithmetic, deduped. O(n^4). */
class Brute {
    List<List<Integer>> fourSum(int[] nums, int target) {
        Set<String> seen = new HashSet<>();
        List<List<Integer>> out = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                        if (sum != target) continue;
                        int[] q = {nums[i], nums[j], nums[k], nums[l]};
                        Arrays.sort(q);
                        String key = q[0] + "," + q[1] + "," + q[2] + "," + q[3];
                        if (seen.add(key)) {
                            out.add(Arrays.asList(q[0], q[1], q[2], q[3]));
                        }
                    }
                }
            }
        }
        return out;
    }
}
