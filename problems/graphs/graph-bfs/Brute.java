import java.util.*;

class Brute {
    /** level by level: each pass turns the current frontier into the next one */
    List<Integer> bfsOfGraph(List<List<Integer>> adj) {
        boolean[] seen = new boolean[adj.size()];
        List<Integer> order = new ArrayList<>();
        List<Integer> frontier = new ArrayList<>();
        frontier.add(0);
        seen[0] = true;
        while (!frontier.isEmpty()) {
            List<Integer> next = new ArrayList<>();
            for (int u : frontier) {
                order.add(u);
                for (int v : adj.get(u)) if (!seen[v]) { seen[v] = true; next.add(v); }
            }
            frontier = next;
        }
        return order;
    }
}
