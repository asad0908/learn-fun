import java.util.*;

/** Obviously-correct oracle: check every window's character counts against s1. O(n * m). */
class Brute {
    boolean checkInclusion(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        if (m > n) return false;
        int[] need = new int[26];
        for (char c : s1.toCharArray()) need[c - 'a']++;

        for (int i = 0; i + m <= n; i++) {
            int[] have = new int[26];
            for (int j = i; j < i + m; j++) have[s2.charAt(j) - 'a']++;
            if (Arrays.equals(need, have)) return true;
        }
        return false;
    }
}
