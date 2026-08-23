public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Happy Number (medium)");
        Solution s = new Solution();

        j.expect("example 1: n=19", true, s.isHappy(19));
        j.expect("example 2: n=2", false, s.isHappy(2));
        j.expect("example 3: n=1", true, s.isHappy(1));
        j.expect("example 4: n=7", true, s.isHappy(7));
        j.expect("edge: n=10 (a power of ten)", true, s.isHappy(10));
        j.expect("edge: n=4 (the classic loop entry)", false, s.isHappy(4));
        j.expect("edge: n=100", true, s.isHappy(100));
        j.expect("edge: n=Integer.MAX_VALUE", false, s.isHappy(2147483647));
        j.expect("edge: n=1111111111 (ten 1s -> 10 -> 1)", true,
                 s.isHappy(1111111111));

        j.stress("every n from 1 to 2000 vs brute", 1, g -> {
            for (int n = 1; n <= 2000; n++) {
                boolean ref = new Brute().isHappy(n), mine = s.isHappy(n);
                if (ref != mine) {
                    return "input    n=" + n + "\n        brute    " + ref
                           + "\n        yours    " + mine;
                }
            }
            return null;
        });

        j.stress("random large n vs brute", 3000, g -> {
            int n = g.intIn(1, Integer.MAX_VALUE);
            return Judge.diff("n=" + n, new Brute().isHappy(n), s.isHappy(n));
        });

        j.finish();
    }
}
