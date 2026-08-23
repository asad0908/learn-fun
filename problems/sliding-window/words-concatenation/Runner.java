import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Problem Challenge 4: Words Concatenation (hard)");
        Solution s = new Solution();

        j.expect("example 1: barfoothefoobarman / [foo,bar]", sorted(List.of(0, 9)),
                 sorted(s.findSubstring("barfoothefoobarman", new String[]{"foo", "bar"})));
        j.expect("example 2: wordgoodgoodgoodbestword / [word,good,best,word]",
                 sorted(List.of()),
                 sorted(s.findSubstring("wordgoodgoodgoodbestword",
                                        new String[]{"word", "good", "best", "word"})));
        j.expect("example 3: barfoofoobarthefoobarman / [bar,foo,the]",
                 sorted(List.of(6, 9, 12)),
                 sorted(s.findSubstring("barfoofoobarthefoobarman",
                                        new String[]{"bar", "foo", "the"})));
        j.expect("edge: single word equal to s", sorted(List.of(0)),
                 sorted(s.findSubstring("abc", new String[]{"abc"})));
        j.expect("edge: words longer than s", sorted(List.of()),
                 sorted(s.findSubstring("ab", new String[]{"abc"})));
        j.expect("edge: repeated word needs two copies present", sorted(List.of(0)),
                 sorted(s.findSubstring("aaaa", new String[]{"aa", "aa"})));

        // Order of the returned indices does not matter, so compare sorted
        // copies rather than the raw lists.
        j.stress("random strings vs brute", 2000, g -> {
            int wordLen = g.intIn(1, 3);
            int k = g.intIn(1, 4);
            String[] words = new String[k];
            for (int i = 0; i < k; i++) words[i] = g.lower(wordLen, 2);
            String str = g.lower(g.intIn(1, 14), 2);
            String in = "s=" + Fmt.show(str) + " words=" + Fmt.show(words);

            List<Integer> mine = sorted(s.findSubstring(str, Arrays.copyOf(words, words.length)));
            List<Integer> ref = sorted(new Brute().findSubstring(str, Arrays.copyOf(words, words.length)));
            return Judge.diff(in, ref, mine);
        });

        j.perf("s length 10000, many short words", 400, () -> {
            int n = 10000;
            StringBuilder sb = new StringBuilder(n);
            for (int i = 0; i < n; i++) sb.append((char) ('a' + (i % 4)));
            String[] words = new String[500];
            Arrays.fill(words, "aa");
            s.findSubstring(sb.toString(), words);
        });

        j.finish();
    }

    private static List<Integer> sorted(List<Integer> list) {
        List<Integer> copy = new ArrayList<>(list);
        Collections.sort(copy);
        return copy;
    }
}
