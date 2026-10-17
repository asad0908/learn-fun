import java.util.*;

public class Runner {
    static List<String> L(String... w) { return new ArrayList<>(Arrays.asList(w)); }

    public static void main(String[] args) {
        Judge j = new Judge("Word Ladder");
        Solution s = new Solution();

        j.expect("example 1", 5, s.ladderLength("hit", "cog", L("hot","dot","dog","lot","log","cog")));
        j.expect("example 2: endWord missing from list", 0, s.ladderLength("hit", "cog", L("hot","dot","dog","lot","log")));
        j.expect("example 3: direct hop", 2, s.ladderLength("a", "c", L("a","b","c")));
        j.expect("edge: one hop, begin not in list", 2, s.ladderLength("hot", "dot", L("dot")));
        j.expect("edge: end in list but no connecting chain", 0, s.ladderLength("aa", "zz", L("ab", "zz")));
        j.expect("edge: begin appears in the list too", 3, s.ladderLength("aa", "cc", L("aa", "ac", "cc")));
        j.expect("edge: two-letter jump is not allowed", 0, s.ladderLength("ab", "cd", L("cd")));
        j.expect("edge: shortest of several chains", 3,
                s.ladderLength("aaa", "abb", L("aab", "abb", "aba", "baa")));
        j.expect("edge: path must pass through list words only", 0,
                s.ladderLength("aaa", "abb", L("abb")));
        j.expect("edge: single-letter words", 2, s.ladderLength("a", "b", L("b")));

        j.stress("random small dictionaries vs brute", 4000, g -> {
            int len = g.intIn(1, 3);
            int letters = g.intIn(2, 3);
            String begin = g.lower(len, letters), end;
            do { end = g.lower(len, letters); } while (end.equals(begin));
            Set<String> words = new LinkedHashSet<>();
            int cnt = Math.min(g.intIn(1, 9), (int) Math.pow(letters, len));
            while (words.size() < cnt) words.add(g.lower(len, letters));
            if (g.intIn(1, 100) <= 80) words.add(end);
            List<String> list = new ArrayList<>(words);
            Collections.shuffle(list, g.r);
            return Judge.diff("begin=" + begin + " end=" + end + " list=" + list,
                    new Brute().ladderLength(begin, end, new ArrayList<>(list)),
                    s.ladderLength(begin, end, new ArrayList<>(list)));
        });

        Gen big = new Gen(19);
        Set<String> ws = new LinkedHashSet<>();
        while (ws.size() < 5000) ws.add(big.lower(5, 6));
        List<String> bigList = new ArrayList<>(ws);
        String bigEnd = bigList.get(4999);
        String bigBegin = bigList.get(0);
        j.perf("5000 words of length 5", 2000, () -> s.ladderLength(bigBegin, bigEnd, new ArrayList<>(bigList)));
        j.finish();
    }
}
