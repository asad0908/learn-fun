import java.util.*;

/**
 * Obviously-correct oracle: at every start index, cut off wordLen-sized
 * chunks and check the multiset matches words exactly. O(n * k).
 */
class Brute {
    List<Integer> findSubstring(String s, String[] words) {
        List<Integer> out = new ArrayList<>();
        int wordLen = words[0].length();
        int k = words.length;
        int total = wordLen * k;
        int n = s.length();
        if (total > n) return out;

        Map<String, Integer> need = new HashMap<>();
        for (String w : words) need.merge(w, 1, Integer::sum);

        for (int i = 0; i + total <= n; i++) {
            Map<String, Integer> have = new HashMap<>();
            boolean ok = true;
            for (int c = 0; c < k; c++) {
                String chunk = s.substring(i + c * wordLen, i + (c + 1) * wordLen);
                have.merge(chunk, 1, Integer::sum);
            }
            ok = have.equals(need);
            if (ok) out.add(i);
        }
        return out;
    }
}
