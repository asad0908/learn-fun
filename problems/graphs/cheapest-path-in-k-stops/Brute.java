class Brute {
    /** try every walk of at most k+1 flights */
    int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        best = Integer.MAX_VALUE;
        go(flights, src, dst, k + 1, 0);
        return best == Integer.MAX_VALUE ? -1 : best;
    }

    private int best;

    private void go(int[][] flights, int at, int dst, int flightsLeft, int cost) {
        if (at == dst) { best = Math.min(best, cost); return; }
        if (flightsLeft == 0) return;
        for (int[] f : flights) if (f[0] == at) go(flights, f[1], dst, flightsLeft - 1, cost + f[2]);
    }
}
