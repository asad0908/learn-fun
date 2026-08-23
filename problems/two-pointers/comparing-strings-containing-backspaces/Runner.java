public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Comparing Strings containing Backspaces (medium)");
        Solution s = new Solution();

        j.expect("example 1: ab#c vs ad#c", true, s.backspaceCompare("ab#c", "ad#c"));
        j.expect("example 2: ab## vs c#d#", true, s.backspaceCompare("ab##", "c#d#"));
        j.expect("example 3: a#c vs b", false, s.backspaceCompare("a#c", "b"));
        j.expect("example 4: ###a vs a", true, s.backspaceCompare("###a", "a"));
        j.expect("edge: all backspaces vs all backspaces", true,
                 s.backspaceCompare("###", "#"));
        j.expect("edge: one empties, other does not", false,
                 s.backspaceCompare("a#", "a"));
        j.expect("edge: no backspaces, equal", true, s.backspaceCompare("abc", "abc"));
        j.expect("edge: no backspaces, different lengths", false,
                 s.backspaceCompare("abc", "ab"));
        j.expect("edge: trailing backspace changes the tail", false,
                 s.backspaceCompare("ab#", "ab"));
        j.expect("edge: bygone chars must not resurface", false,
                 s.backspaceCompare("bxj##tw", "bxo#j##tw#"));

        j.stress("random strings over {a,b,#} vs brute", 4000, g -> {
            String x = g.string(g.intIn(1, 10), "ab##");   // '#' twice = ~50% weight
            String y = g.string(g.intIn(1, 10), "ab##");
            return Judge.diff("s=\"" + x + "\" t=\"" + y + "\"",
                    new Brute().backspaceCompare(x, y),
                    s.backspaceCompare(x, y));
        });

        j.stress("random strings biased to equal results", 2000, g -> {
            String x = g.string(g.intIn(1, 8), "aab#");
            String y = g.string(g.intIn(1, 8), "aab#");
            return Judge.diff("s=\"" + x + "\" t=\"" + y + "\"",
                    new Brute().backspaceCompare(x, y),
                    s.backspaceCompare(x, y));
        });

        j.finish();
    }
}
