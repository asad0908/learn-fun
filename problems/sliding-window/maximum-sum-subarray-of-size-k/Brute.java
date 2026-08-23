/** Obviously-correct oracle: sum every window of size k from scratch. O(n*k). */
class Brute {
    int maxSumSubarrayOfSizeK(int[] arr, int k) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i + k <= arr.length; i++) {
            int sum = 0;
            for (int j = i; j < i + k; j++) sum += arr[j];
            best = Math.max(best, sum);
        }
        return best;
    }
}
