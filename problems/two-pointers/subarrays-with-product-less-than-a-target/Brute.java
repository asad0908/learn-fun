/** Obviously-correct oracle: every window, product in a long. O(n^2). */
class Brute {
    int numSubarrayProductLessThanK(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            long product = 1;
            for (int j = i; j < nums.length; j++) {
                product *= nums[j];
                if (product < k) count++;
                else break;              // values are positive, so it only grows
            }
        }
        return count;
    }
}
