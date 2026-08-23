public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("No-repeat Substring (hard)");
        Solution s = new Solution();

        j.expect("example 1: abcabcbb", 3, s.lengthOfLongestSubstring("abcabcbb"));
        j.expect("example 2: bbbbb", 1, s.lengthOfLongestSubstring("bbbbb"));
        j.expect("example 3: pwwkew", 3, s.lengthOfLongestSubstring("pwwkew"));
        j.expect("edge: empty string", 0, s.lengthOfLongestSubstring(""));
        j.expect("edge: single character", 1, s.lengthOfLongestSubstring("a"));
        j.expect("edge: all distinct characters", 5, s.lengthOfLongestSubstring("abcde"));

        j.stress("random strings vs brute", 2000, g -> {
            int n = g.intIn(0, 12);
            String str = g.lower(n, 4);
            String in = "s=" + Fmt.show(str);

            int mine = s.lengthOfLongestSubstring(str);
            int ref = new Brute().lengthOfLongestSubstring(str);
            return Judge.diff(in, ref, mine);
        });

        j.perf("n = 100000 must finish fast (O(n) expected)", 300, () -> {
            int n = 100000;
            StringBuilder sb = new StringBuilder(n);
            for (int i = 0; i < n; i++) sb.append((char) ('a' + (i % 26)));
            s.lengthOfLongestSubstring(sb.toString());
        });

        j.finish();
    }
}
