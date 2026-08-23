import java.util.*;

/** Obviously-correct oracle: check every substring for a repeat. O(n^2). */
class Brute {
    int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int best = 0;
        for (int i = 0; i < n; i++) {
            Set<Character> seen = new HashSet<>();
            for (int j = i; j < n; j++) {
                if (!seen.add(s.charAt(j))) break;
                best = Math.max(best, j - i + 1);
            }
        }
        return best;
    }
}
