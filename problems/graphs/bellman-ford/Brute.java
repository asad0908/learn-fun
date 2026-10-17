import java.util.*;

class Brute {
    /** dp[k][v] = cheapest walk src -> v using at most k edges. With no reachable
     *  negative cycle the table stops changing by k = V-1; a change at k = V
     *  proves one exists. */
    int[] bellmanFord(int V, int[][] edges, int src) {
        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[V + 1][V];
        for (long[] row : dp) Arrays.fill(row, INF);
        dp[0][src] = 0;
        for (int k = 1; k <= V; k++) {
            dp[k] = dp[k - 1].clone();
            for (int[] e : edges)
                if (dp[k - 1][e[0]] < INF && dp[k - 1][e[0]] + e[2] < dp[k][e[1]])
                    dp[k][e[1]] = dp[k - 1][e[0]] + e[2];
        }
        for (int v = 0; v < V; v++) if (dp[V][v] != dp[V - 1][v]) return new int[]{-1};
        int[] out = new int[V];
        for (int v = 0; v < V; v++) out[v] = dp[V][v] >= INF ? 100000000 : (int) dp[V][v];
        return out;
    }
}
