public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Problem Challenge 1: Permutation in a String (hard)");
        Solution s = new Solution();

        j.expect("example 1: ab / eidbaooo", true, s.checkInclusion("ab", "eidbaooo"));
        j.expect("example 2: ab / eidboaoo", false, s.checkInclusion("ab", "eidboaoo"));
        j.expect("example 3: adc / dcda", true, s.checkInclusion("adc", "dcda"));
        j.expect("edge: s1 longer than s2", false, s.checkInclusion("abcd", "abc"));
        j.expect("edge: s1 equals s2", true, s.checkInclusion("abc", "abc"));
        j.expect("edge: single character match", true, s.checkInclusion("a", "ba"));

        j.stress("random strings vs brute", 2000, g -> {
            String s2 = g.lower(g.intIn(1, 12), 3);
            String s1 = g.lower(g.intIn(1, 5), 3);
            String in = "s1=" + Fmt.show(s1) + " s2=" + Fmt.show(s2);

            boolean mine = s.checkInclusion(s1, s2);
            boolean ref = new Brute().checkInclusion(s1, s2);
            return Judge.diff(in, ref, mine);
        });

        j.perf("s2 length 10000 must finish fast (O(n) expected)", 300, () -> {
            StringBuilder sb = new StringBuilder(10000);
            for (int i = 0; i < 10000; i++) sb.append((char) ('a' + (i % 26)));
            s.checkInclusion("zzzzzzzzzz", sb.toString());
        });

        j.finish();
    }
}
