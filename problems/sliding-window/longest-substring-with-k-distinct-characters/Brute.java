import java.util.*;

/** Obviously-correct oracle: check every substring's distinct-char count. O(n^2). */
class Brute {
    int longestSubstringWithKDistinct(String str, int k) {
        int n = str.length();
        int best = 0;
        for (int i = 0; i < n; i++) {
            Set<Character> seen = new HashSet<>();
            for (int j = i; j < n; j++) {
                seen.add(str.charAt(j));
                if (seen.size() > k) break;
                best = Math.max(best, j - i + 1);
            }
        }
        return best;
    }
}
