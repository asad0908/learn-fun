import java.util.*;

/** Obviously-correct oracle: sort a copy, compare from both ends. O(n log n). */
class Brute {
    int findUnsortedSubarray(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi && nums[lo] == sorted[lo]) lo++;
        while (hi > lo && nums[hi] == sorted[hi]) hi--;
        return hi - lo + 1 <= 0 ? 0 : hi - lo + 1;
    }
}
