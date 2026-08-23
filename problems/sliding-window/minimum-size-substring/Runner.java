public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Minimum Size Substring (hard)");
        Solution s = new Solution();

        j.expect("example 1: ADOBECODEBANC / ABC", "BANC",
                 s.minWindow("ADOBECODEBANC", "ABC"));
        j.expect("example 2: a / a", "a", s.minWindow("a", "a"));
        j.expect("example 3: a / aa", "", s.minWindow("a", "aa"));
        j.expect("edge: t needs a repeated letter", "aa", s.minWindow("aa", "aa"));
        j.expect("edge: t not present at all", "", s.minWindow("abc", "xyz"));
        j.expect("edge: whole string is the only window", "abc", s.minWindow("abc", "cab"));

        // Multiple minimal-length windows can exist, so check the property
        // (right length, contains t's characters) rather than diffing the
        // exact string against Brute's particular choice.
        j.stress("random strings vs brute", 2000, g -> {
            String str = g.lower(g.intIn(1, 12), 3);
            String t = g.lower(g.intIn(1, 4), 3);
            String in = "s=" + Fmt.show(str) + " t=" + Fmt.show(t);

            String mine = s.minWindow(str, t);
            String ref = new Brute().minWindow(str, t);

            if (ref.isEmpty()) {
                return mine.isEmpty() ? null
                    : "input    " + in + "\n        brute    no window exists"
                      + "\n        yours    " + Fmt.show(mine);
            }
            if (mine.length() != ref.length()) {
                return "input    " + in + "\n        brute length " + ref.length()
                       + " (" + Fmt.show(ref) + ")\n        yours length "
                       + mine.length() + " (" + Fmt.show(mine) + ")";
            }
            if (str.indexOf(mine) < 0 && !mine.isEmpty()) {
                return "input    " + in + "\n        yours    " + Fmt.show(mine)
                       + "  (not even a substring of s)";
            }
            if (!covers(mine, t)) {
                return "input    " + in + "\n        yours    " + Fmt.show(mine)
                       + "  does not contain all of t=" + Fmt.show(t);
            }
            return null;
        });

        j.perf("large inputs must finish fast", 400, () -> {
            int n = 100000;
            StringBuilder sb = new StringBuilder(n);
            for (int i = 0; i < n; i++) sb.append((char) ('a' + (i % 26)));
            s.minWindow(sb.toString(), "xyz");
        });

        j.finish();
    }

    private static boolean covers(String window, String t) {
        int[] need = new int[128];
        for (int i = 0; i < t.length(); i++) need[t.charAt(i)]++;
        int[] have = new int[128];
        for (int i = 0; i < window.length(); i++) have[window.charAt(i)]++;
        for (int c = 0; c < 128; c++) if (have[c] < need[c]) return false;
        return true;
    }
}
