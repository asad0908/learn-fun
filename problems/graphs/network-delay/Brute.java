import java.util.*;

class Brute {
    /** relax every edge until nothing improves, then take the slowest node */
    int networkDelayTime(int[][] times, int n, int k) {
        int INF = Integer.MAX_VALUE / 2;
        int[] d = new int[n + 1];
        Arrays.fill(d, INF);
        d[k] = 0;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int[] e : times)
                if (d[e[0]] + e[2] < d[e[1]]) { d[e[1]] = d[e[0]] + e[2]; changed = true; }
        }
        int worst = 0;
        for (int i = 1; i <= n; i++) {
            if (d[i] >= INF) return -1;
            worst = Math.max(worst, d[i]);
        }
        return worst;
    }
}
