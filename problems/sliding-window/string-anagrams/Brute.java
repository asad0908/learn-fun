import java.util.*;

/** Obviously-correct oracle: check every window's character counts against p. O(n * m). */
class Brute {
    List<Integer> findAnagrams(String s, String p) {
        List<Integer> out = new ArrayList<>();
        int m = p.length(), n = s.length();
        if (m > n) return out;
        int[] need = new int[26];
        for (char c : p.toCharArray()) need[c - 'a']++;

        for (int i = 0; i + m <= n; i++) {
            int[] have = new int[26];
            for (int j = i; j < i + m; j++) have[s.charAt(j) - 'a']++;
            if (Arrays.equals(need, have)) out.add(i);
        }
        return out;
    }
}
