/** Obviously-correct oracle: check every substring's character counts against t. O(n^3). */
class Brute {
    String minWindow(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;

        int n = s.length();
        int bestLen = Integer.MAX_VALUE, bestStart = -1;
        for (int i = 0; i < n; i++) {
            int[] have = new int[128];
            for (int j = i; j < n; j++) {
                have[s.charAt(j)]++;
                if (j - i + 1 >= bestLen) continue;
                if (covers(have, need)) {
                    bestLen = j - i + 1;
                    bestStart = i;
                    break;
                }
            }
        }
        return bestStart == -1 ? "" : s.substring(bestStart, bestStart + bestLen);
    }

    private boolean covers(int[] have, int[] need) {
        for (int c = 0; c < 128; c++) if (have[c] < need[c]) return false;
        return true;
    }
}
