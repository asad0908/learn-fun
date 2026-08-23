/** Obviously-correct oracle: check every subarray's sum. O(n^2). */
class Brute {
    int smallestSubarrayWithGivenSum(int s, int[] arr) {
        int n = arr.length;
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += arr[j];
                if (sum >= s) {
                    best = Math.min(best, j - i + 1);
                    break;
                }
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }
}
