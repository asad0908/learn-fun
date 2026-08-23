public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Longest Substring with Same Letters after Replacement (hard)");
        Solution s = new Solution();

        j.expect("example 1: ABAB k=2", 4, s.characterReplacement("ABAB", 2));
        j.expect("example 2: AABABBA k=1", 4, s.characterReplacement("AABABBA", 1));
        j.expect("example 3: ABCDE k=1", 2, s.characterReplacement("ABCDE", 1));
        j.expect("edge: k = 0 keeps only existing runs", 2,
                 s.characterReplacement("AAB", 0));
        j.expect("edge: single character", 1, s.characterReplacement("A", 0));
        j.expect("edge: k covers the whole string", 4,
                 s.characterReplacement("ABCD", 4));

        j.stress("random strings vs brute", 2000, g -> {
            int n = g.intIn(1, 12);
            String str = g.string(n, "AB").toUpperCase();
            int k = g.intIn(0, n);
            String in = "s=" + Fmt.show(str) + " k=" + k;

            int mine = s.characterReplacement(str, k);
            int ref = new Brute().characterReplacement(str, k);
            return Judge.diff(in, ref, mine);
        });

        j.stress("random strings over 4 letters vs brute", 2000, g -> {
            int n = g.intIn(1, 12);
            String str = g.string(n, "ABCD");
            int k = g.intIn(0, n);
            String in = "s=" + Fmt.show(str) + " k=" + k;

            int mine = s.characterReplacement(str, k);
            int ref = new Brute().characterReplacement(str, k);
            return Judge.diff(in, ref, mine);
        });

        j.perf("n = 100000 must finish fast (O(n) expected)", 300, () -> {
            int n = 100000;
            StringBuilder sb = new StringBuilder(n);
            for (int i = 0; i < n; i++) sb.append((char) ('A' + (i % 4)));
            s.characterReplacement(sb.toString(), 1000);
        });

        j.finish();
    }
}
