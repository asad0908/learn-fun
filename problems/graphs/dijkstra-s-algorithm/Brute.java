import java.util.*;

class Brute {
    /** relax every edge in both directions until nothing improves */
    int[] dijkstra(int V, int[][] edges, int src) {
        long INF = Long.MAX_VALUE / 4;
        long[] d = new long[V];
        Arrays.fill(d, INF);
        d[src] = 0;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int[] e : edges) {
                if (d[e[0]] + e[2] < d[e[1]]) { d[e[1]] = d[e[0]] + e[2]; changed = true; }
                if (d[e[1]] + e[2] < d[e[0]]) { d[e[0]] = d[e[1]] + e[2]; changed = true; }
            }
        }
        int[] out = new int[V];
        for (int i = 0; i < V; i++) out[i] = d[i] >= INF ? -1 : (int) d[i];
        return out;
    }
}
