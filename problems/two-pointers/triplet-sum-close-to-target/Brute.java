import java.util.*;

/** Obviously-correct oracle: every triple. O(n^3). */
class Brute {
    int threeSumClosest(int[] nums, int target) {
        long best = Long.MAX_VALUE;
        int bestSum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    long d = Math.abs((long) sum - target);
                    if (d < best) { best = d; bestSum = sum; }
                }
            }
        }
        return bestSum;
    }

    /** every achievable triplet sum -- lets the judge accept any tied answer */
    Set<Integer> allTripletSums(int[] nums) {
        Set<Integer> sums = new HashSet<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    sums.add(nums[i] + nums[j] + nums[k]);
                }
            }
        }
        return sums;
    }
}
