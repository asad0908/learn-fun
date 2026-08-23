/** Obviously-correct oracle: check every subarray's sum. O(n^2). */
class Brute {
    int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum >= target) {
                    best = Math.min(best, j - i + 1);
                    break;
                }
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }
}
