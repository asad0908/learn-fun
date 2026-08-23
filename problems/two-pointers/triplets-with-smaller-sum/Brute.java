/** Obviously-correct oracle: enumerate every index triple. O(n^3). */
class Brute {
    int countTriplets(int[] arr, int target) {
        int count = 0, n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (arr[i] + arr[j] + arr[k] < target) count++;
                }
            }
        }
        return count;
    }
}
