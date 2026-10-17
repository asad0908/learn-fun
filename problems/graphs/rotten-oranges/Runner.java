public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Rotten Oranges");
        Solution s = new Solution();

        j.expect("example 1", 4, s.orangesRotting(new int[][]{{2,1,1},{1,1,0},{0,1,1}}));
        j.expect("example 2: unreachable", -1, s.orangesRotting(new int[][]{{2,1,1},{0,1,1},{1,0,1}}));
        j.expect("example 3: nothing fresh", 0, s.orangesRotting(new int[][]{{0,2}}));
        j.expect("edge: empty grid", 0, s.orangesRotting(new int[][]{{0}}));
        j.expect("edge: only fresh, no rot", -1, s.orangesRotting(new int[][]{{1}}));
        j.expect("edge: only rotten", 0, s.orangesRotting(new int[][]{{2,2},{2,2}}));
        j.expect("edge: fresh with no rot anywhere", -1, s.orangesRotting(new int[][]{{1,1},{1,0}}));
        j.expect("edge: two sources race, answer is the slowest cell", 2,
                s.orangesRotting(new int[][]{{2,1,1,1,2}}));
        j.expect("edge: single row chain", 4, s.orangesRotting(new int[][]{{2,1,1,1,1}}));
        j.expect("edge: wall of empties blocks", -1, s.orangesRotting(new int[][]{{2,0,1}}));

        j.stress("random grids vs brute", 3000, g -> {
            int[][] gr = g.matrix(g.intIn(1, 6), g.intIn(1, 6), 0, 3);
            for (int[] row : gr) for (int k = 0; k < row.length; k++) if (row[k] == 3) row[k] = 1;
            return Judge.diff(Fmt.show(gr), new Brute().orangesRotting(Fmt.copy(gr)),
                    s.orangesRotting(Fmt.copy(gr)));
        });

        j.stress("mostly fresh, few sources (long spreads)", 2000, g -> {
            int[][] gr = new int[g.intIn(1, 8)][g.intIn(1, 8)];
            for (int[] row : gr) for (int k = 0; k < row.length; k++) row[k] = g.intIn(1, 100) <= 85 ? 1 : 0;
            gr[g.intIn(0, gr.length - 1)][g.intIn(0, gr[0].length - 1)] = 2;
            return Judge.diff(Fmt.show(gr), new Brute().orangesRotting(Fmt.copy(gr)),
                    s.orangesRotting(Fmt.copy(gr)));
        });
        j.finish();
    }
}
