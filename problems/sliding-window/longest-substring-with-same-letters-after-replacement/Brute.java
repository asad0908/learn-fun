/**
 * Obviously-correct oracle: for every window, count the most frequent letter
 * and check whether replacing the rest fits the budget k. O(n^2 * 26).
 */
class Brute {
    int characterReplacement(String s, int k) {
        int n = s.length();
        int best = 0;
        for (int i = 0; i < n; i++) {
            int[] freq = new int[26];
            int maxFreq = 0;
            for (int j = i; j < n; j++) {
                freq[s.charAt(j) - 'A']++;
                maxFreq = Math.max(maxFreq, freq[s.charAt(j) - 'A']);
                int len = j - i + 1;
                if (len - maxFreq <= k) best = Math.max(best, len);
            }
        }
        return best;
    }
}
