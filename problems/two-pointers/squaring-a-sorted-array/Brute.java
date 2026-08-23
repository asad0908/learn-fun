import java.util.*;

/** Obviously-correct oracle: square everything, then sort. O(n log n). */
class Brute {
    int[] sortedSquares(int[] nums) {
        int[] out = new int[nums.length];
        for (int i = 0; i < nums.length; i++) out[i] = nums[i] * nums[i];
        Arrays.sort(out);
        return out;
    }
}
