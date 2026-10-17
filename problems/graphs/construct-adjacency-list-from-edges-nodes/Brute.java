import java.util.*;

class Brute {
    List<List<Integer>> printGraph(int V, int[][] edges) {
        boolean[][] m = new boolean[V][V];
        for (int[] e : edges) { m[e[0]][e[1]] = true; m[e[1]][e[0]] = true; }
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < V; j++) if (m[i][j]) row.add(j);
            out.add(row);
        }
        return out;
    }
}
