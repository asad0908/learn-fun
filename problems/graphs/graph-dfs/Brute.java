import java.util.*;

class Brute {
    List<Integer> dfsOfGraph(List<List<Integer>> adj) {
        List<Integer> order = new ArrayList<>();
        go(0, adj, new boolean[adj.size()], order);
        return order;
    }

    private void go(int u, List<List<Integer>> adj, boolean[] seen, List<Integer> order) {
        seen[u] = true;
        order.add(u);
        for (int v : adj.get(u)) if (!seen[v]) go(v, adj, seen, order);
    }
}
