public class Runner {
    static char[][] run(Solution s, String... rows) {
        char[][] b = Fmt.grid(rows);
        s.solve(b);
        return b;
    }

    public static void main(String[] args) {
        Judge j = new Judge("Surrounded Regions");
        Solution s = new Solution();

        j.expect("example 1", Fmt.grid("XXXX", "XXXX", "XXXX", "XOXX"),
                run(s, "XXXX", "XOOX", "XXOX", "XOXX"));
        j.expect("example 2: single X", Fmt.grid("X"), run(s, "X"));
        j.expect("example 3: all O's on the border chain", Fmt.grid("OOO", "OXO", "OOO"),
                run(s, "OOO", "OXO", "OOO"));
        j.expect("edge: single O stays (it is on the border)", Fmt.grid("O"), run(s, "O"));
        j.expect("edge: 1 x n row never captures", Fmt.grid("OXOO"), run(s, "OXOO"));
        j.expect("edge: n x 1 column never captures", Fmt.grid("O", "X", "O"), run(s, "O", "X", "O"));
        j.expect("edge: lone inner O is captured", Fmt.grid("XXX", "XXX", "XXX"),
                run(s, "XXX", "XOX", "XXX"));
        j.expect("edge: inner O connected to border survives", Fmt.grid("XOX", "XOX", "XXX"),
                run(s, "XOX", "XOX", "XXX"));
        j.expect("edge: diagonal contact does not rescue", Fmt.grid("XXXX", "XXXX", "XXXO", "XXXX"),
                run(s, "XXXX", "XOXX", "XXXO", "XXXX"));
        j.expect("edge: big enclosed ring + hole", Fmt.grid("XXXXX", "XXXXX", "XXXXX", "XXXXX", "XXXXX"),
                run(s, "XXXXX", "XOOOX", "XOXOX", "XOOOX", "XXXXX"));

        j.stress("random boards vs brute", 4000, g -> {
            char[][] b = g.charGrid(g.intIn(1, 7), g.intIn(1, 7), g.bool() ? "XO" : "XOO");
            char[][] mine = Fmt.copy(b), oracle = Fmt.copy(b);
            s.solve(mine);
            new Brute().solve(oracle);
            return Judge.diff(Fmt.show(b), oracle, mine);
        });

        char[][] big = new Gen(9).charGrid(200, 200, "XXO");
        j.perf("200x200 board", 1500, () -> s.solve(Fmt.copy(big)));
        j.finish();
    }
}
