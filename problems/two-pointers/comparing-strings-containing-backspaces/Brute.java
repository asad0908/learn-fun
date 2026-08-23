/** Obviously-correct oracle: actually build both strings. O(n) time, O(n) space. */
class Brute {
    boolean backspaceCompare(String s, String t) {
        return typed(s).equals(typed(t));
    }

    private String typed(String x) {
        StringBuilder sb = new StringBuilder();
        for (char c : x.toCharArray()) {
            if (c == '#') {
                if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
