import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Problem Challenge 2: String Anagrams (hard)");
        Solution s = new Solution();

        j.expect("example 1: cbaebabacd / abc", List.of(0, 6),
                 s.findAnagrams("cbaebabacd", "abc"));
        j.expect("example 2: abab / ab", List.of(0, 1, 2),
                 s.findAnagrams("abab", "ab"));
        j.expect("example 3: a / b", List.of(),
                 s.findAnagrams("a", "b"));
        j.expect("edge: p longer than s", List.of(),
                 s.findAnagrams("ab", "abc"));
        j.expect("edge: s equals p", List.of(0),
                 s.findAnagrams("abc", "abc"));
        j.expect("edge: single-character p matching everywhere", List.of(0, 1, 2),
                 s.findAnagrams("aaa", "a"));

        j.stress("random strings vs brute", 2000, g -> {
            String str = g.lower(g.intIn(1, 14), 3);
            String p = g.lower(g.intIn(1, 5), 3);
            String in = "s=" + Fmt.show(str) + " p=" + Fmt.show(p);

            List<Integer> mine = s.findAnagrams(str, p);
            List<Integer> ref = new Brute().findAnagrams(str, p);
            return Judge.diff(in, ref, mine);
        });

        j.perf("s length 30000 must finish fast (O(n) expected)", 400, () -> {
            StringBuilder sb = new StringBuilder(30000);
            for (int i = 0; i < 30000; i++) sb.append((char) ('a' + (i % 26)));
            s.findAnagrams(sb.toString(), "zzzzzzzzzz");
        });

        j.finish();
    }
}
