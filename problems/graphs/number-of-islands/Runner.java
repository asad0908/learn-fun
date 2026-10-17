public class Runner {
    public static void main(String[] args) {
        Judge j = new Judge("Number of Islands");
        Solution s = new Solution();

        j.expect("example 1", 1, s.numIslands(Fmt.grid("11110", "11010", "11000", "00000")));
        j.expect("example 2", 3, s.numIslands(Fmt.grid("11000", "11000", "00100", "00011")));
        j.expect("example 3: checkerboard, diagonals do not join", 5,
                s.numIslands(Fmt.grid("101", "010", "101")));
        j.expect("edge: single land cell", 1, s.numIslands(Fmt.grid("1")));
        j.expect("edge: single water cell", 0, s.numIslands(Fmt.grid("0")));
        j.expect("edge: all water", 0, s.numIslands(Fmt.grid("000", "000")));
        j.expect("edge: all land", 1, s.numIslands(Fmt.grid("111", "111", "111")));
        j.expect("edge: single row", 3, s.numIslands(Fmt.grid("1101011")));
        j.expect("edge: single column", 2, s.numIslands(Fmt.grid("1", "1", "0", "1")));
        j.expect("edge: spiral is one island", 1,
                s.numIslands(Fmt.grid("11111", "00001", "11101", "10001", "11111")));

        j.stress("random grids vs brute", 3000, g -> {
            char[][] gr = g.charGrid(g.intIn(1, 6), g.intIn(1, 6), g.bool() ? "01" : "0111");
            return Judge.diff(Fmt.show(gr), new Brute().numIslands(Fmt.copy(gr)),
                    s.numIslands(Fmt.copy(gr)));
        });

        char[][] big = new Gen(5).charGrid(300, 300, "01");
        j.perf("300x300 random grid", 1500, () -> s.numIslands(Fmt.copy(big)));
        j.finish();
    }
}
