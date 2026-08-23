/** Obviously-correct oracle: for every window, count zeros and check the budget. O(n^2). */
class Brute {
    int longestOnes(int[] nums, int k) {
        int n = nums.length;
        int best = 0;
        for (int i = 0; i < n; i++) {
            int zeros = 0;
            for (int j = i; j < n; j++) {
                if (nums[j] == 0) zeros++;
                if (zeros > k) break;
                best = Math.max(best, j - i + 1);
            }
        }
        return best;
    }
}
