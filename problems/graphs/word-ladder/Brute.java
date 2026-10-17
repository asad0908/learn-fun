import java.util.*;

class Brute {
    /** distance 1 at beginWord, then relax every pair of words that differ in exactly
     *  one letter until nothing improves */
    int ladderLength(String beginWord, String endWord, List<String> wordList) {
        List<String> nodes = new ArrayList<>();
        nodes.add(beginWord);
        for (String w : wordList) if (!nodes.contains(w)) nodes.add(w);
        int n = nodes.size(), INF = Integer.MAX_VALUE / 2;
        int[] d = new int[n];
        Arrays.fill(d, INF);
        d[0] = 1;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int a = 0; a < n; a++)
                for (int b = 0; b < n; b++)
                    if (d[a] + 1 < d[b] && oneApart(nodes.get(a), nodes.get(b))) { d[b] = d[a] + 1; changed = true; }
        }
        int target = nodes.indexOf(endWord);
        if (target < 0 || d[target] >= INF) return 0;
        return d[target];
    }

    private boolean oneApart(String x, String y) {
        int diff = 0;
        for (int i = 0; i < x.length(); i++) if (x.charAt(i) != y.charAt(i)) diff++;
        return diff == 1;
    }
}
