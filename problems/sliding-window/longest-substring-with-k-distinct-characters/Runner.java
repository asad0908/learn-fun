public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Longest Substring with K Distinct Characters (medium)");
        Solution s = new Solution();

        j.expect("example 1: araaci k=2", 4,
                 s.longestSubstringWithKDistinct("araaci", 2));
        j.expect("example 2: araaci k=1", 2,
                 s.longestSubstringWithKDistinct("araaci", 1));
        j.expect("example 3: cbbebi k=3", 5,
                 s.longestSubstringWithKDistinct("cbbebi", 3));
        j.expect("edge: k = 0", 0,
                 s.longestSubstringWithKDistinct("abc", 0));
        j.expect("edge: single character string", 1,
                 s.longestSubstringWithKDistinct("a", 1));
        j.expect("edge: k >= distinct chars in string", 6,
                 s.longestSubstringWithKDistinct("abcabc", 5));

        j.stress("random strings vs brute", 2000, g -> {
            int n = g.intIn(1, 12);
            String str = g.lower(n, 4);
            int k = g.intIn(0, 4);
            String in = "str=" + Fmt.show(str) + " k=" + k;

            int mine = s.longestSubstringWithKDistinct(str, k);
            int ref = new Brute().longestSubstringWithKDistinct(str, k);
            return Judge.diff(in, ref, mine);
        });

        j.perf("n = 100000 must finish fast (O(n) expected)", 300, () -> {
            int n = 100000;
            StringBuilder sb = new StringBuilder(n);
            for (int i = 0; i < n; i++) sb.append((char) ('a' + (i % 20)));
            s.longestSubstringWithKDistinct(sb.toString(), 10);
        });

        j.finish();
    }
}
