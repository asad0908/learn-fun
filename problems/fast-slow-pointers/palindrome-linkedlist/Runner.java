public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Palindrome LinkedList (medium)");
        Solution s = new Solution();

        j.expect("example 1: [1,2,2,1]", true,
                 s.isPalindrome(Fmt.list(1, 2, 2, 1)));
        j.expect("example 2: [1,2]", false, s.isPalindrome(Fmt.list(1, 2)));
        j.expect("example 3: single node", true, s.isPalindrome(Fmt.list(1)));
        j.expect("example 4: [1,2,3,2,1] odd length", true,
                 s.isPalindrome(Fmt.list(1, 2, 3, 2, 1)));
        j.expect("example 5: [1,2,2,2,1]", true,
                 s.isPalindrome(Fmt.list(1, 2, 2, 2, 1)));
        j.expect("edge: two equal nodes", true, s.isPalindrome(Fmt.list(4, 4)));
        j.expect("edge: differs only at the pivot", false,
                 s.isPalindrome(Fmt.list(1, 2, 3, 4, 1)));
        j.expect("edge: differs only at the very end", false,
                 s.isPalindrome(Fmt.list(1, 2, 3, 2, 9)));
        j.expect("edge: all identical, even length", true,
                 s.isPalindrome(Fmt.list(5, 5, 5, 5)));
        j.expect("edge: all identical, odd length", true,
                 s.isPalindrome(Fmt.list(5, 5, 5)));

        // Solution is allowed to mutate the list, so build it fresh per call.
        j.stress("random lists over {0,1,2} -- some palindromes by luck", 3000, g -> {
            int[] vals = g.array(g.intIn(1, 10), 0, 2);
            return Judge.diff("vals=" + Fmt.show(vals),
                    new Brute().isPalindrome(Fmt.list(vals)),
                    s.isPalindrome(Fmt.list(vals)));
        });

        j.stress("deliberately built palindromes, sometimes broken", 3000, g -> {
            int half = g.intIn(1, 6);
            boolean odd = g.bool();
            int[] left = g.array(half, 0, 3);
            int n = half * 2 + (odd ? 1 : 0);
            int[] vals = new int[n];
            for (int i = 0; i < half; i++) {
                vals[i] = left[i];
                vals[n - 1 - i] = left[i];
            }
            if (odd) vals[half] = g.intIn(0, 3);
            // break it a third of the time so the true/false mix stays honest
            if (g.intIn(1, 3) == 1) vals[g.intIn(0, n - 1)] = g.intIn(0, 3);
            return Judge.diff("vals=" + Fmt.show(vals),
                    new Brute().isPalindrome(Fmt.list(vals)),
                    s.isPalindrome(Fmt.list(vals)));
        });

        j.perf("100000 nodes, a true palindrome", 400, () -> {
            int n = 100000;
            int[] vals = new int[n];
            for (int i = 0; i < n / 2; i++) {
                vals[i] = i % 10;
                vals[n - 1 - i] = i % 10;
            }
            s.isPalindrome(Fmt.list(vals));
        });

        j.finish();
    }
}
