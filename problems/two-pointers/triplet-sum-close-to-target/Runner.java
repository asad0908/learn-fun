import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Triplet Sum Close to Target (medium)");
        Solution s = new Solution();

        j.expect("example 1: [-1,2,1,-4] t=1", 2,
                 s.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        j.expect("example 2: [0,0,0] t=1", 0,
                 s.threeSumClosest(new int[]{0, 0, 0}, 1));
        j.expect("example 3: exact hit available", -2,
                 s.threeSumClosest(new int[]{4, 0, 5, -5, 3, 3, 0, -4, -5}, -2));
        j.expect("edge: exactly 3 elements", 6,
                 s.threeSumClosest(new int[]{1, 2, 3}, 100));
        j.expect("edge: target far below everything", 6,
                 s.threeSumClosest(new int[]{1, 2, 3, 4, 5}, -1000));
        j.expect("edge: target far above everything", 12,
                 s.threeSumClosest(new int[]{1, 2, 3, 4, 5}, 1000));
        j.expect("edge: all identical", -3,
                 s.threeSumClosest(new int[]{-1, -1, -1, -1}, 7));

        // ties are legal, so require: achievable sum AND minimal distance
        j.stress("random arrays vs brute (ties allowed)", 3000, g -> {
            int n = g.intIn(3, 11);
            int[] a = g.array(n, -9, 9);
            int target = g.intIn(-20, 20);
            String in = "nums=" + Fmt.show(a) + " target=" + target;

            int mine = s.threeSumClosest(Fmt.copy(a), target);
            Brute b = new Brute();
            int ref = b.threeSumClosest(Fmt.copy(a), target);
            Set<Integer> achievable = b.allTripletSums(Fmt.copy(a));

            if (!achievable.contains(mine)) {
                return "input    " + in + "\n        yours    " + mine
                       + " is not the sum of any triplet"
                       + "\n        brute    " + ref;
            }
            int dMine = Math.abs(mine - target), dRef = Math.abs(ref - target);
            if (dMine != dRef) {
                return "input    " + in + "\n        brute    " + ref
                       + "  (distance " + dRef + ")"
                       + "\n        yours    " + mine + "  (distance " + dMine + ")";
            }
            return null;
        });

        j.perf("n = 500 (O(n^2) expected)", 1500, () -> {
            int[] a = new Gen(5).array(500, -1000, 1000);
            s.threeSumClosest(a, 37);
        });

        j.finish();
    }
}
