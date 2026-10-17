import java.util.*;

class Brute {
    /** relax every edge in both directions until nothing improves */
    int[] shortestPath(int[][] edges, int N, int src) {
        int INF = Integer.MAX_VALUE / 2;
        int[] d = new int[N];
        Arrays.fill(d, INF);
        d[src] = 0;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int[] e : edges) {
                if (d[e[0]] + 1 < d[e[1]]) { d[e[1]] = d[e[0]] + 1; changed = true; }
                if (d[e[1]] + 1 < d[e[0]]) { d[e[0]] = d[e[1]] + 1; changed = true; }
            }
        }
        for (int i = 0; i < N; i++) if (d[i] >= INF) d[i] = -1;
        return d;
    }
}
