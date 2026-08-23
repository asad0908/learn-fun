import java.util.*;

/** Obviously-correct oracle: remember every value seen. */
class Brute {
    boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && seen.add(n)) n = next(n);
        return n == 1;
    }

    /** sum of the squares of the digits */
    static int next(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            sum += d * d;
            n /= 10;
        }
        return sum;
    }
}
