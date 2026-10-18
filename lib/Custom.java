import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * `dsa run` -- the LeetCode "custom testcase" box, but with an oracle.
 *
 * Reads inputs you wrote yourself, calls your Solution on each, and calls
 * Brute on the same input so you also see the *expected* answer -- something
 * LeetCode only gives you after a submission. Works for every scaffolded
 * problem without per-problem code: the parameter types are read by
 * reflection and each value is parsed to match.
 *
 * Input format (input.txt), deliberately forgiving:
 *
 *     [2,7,11,15]                        one argument per line (LeetCode box)
 *     9
 *     numbers = [2,7,11,15], target = 9  or all on one line, names optional
 *     Input:  grid = ["101","010"]       or pasted straight from problem.md
 *     [3,2,0,-4] pos=1                   linked list whose tail links to index 1
 *     [1,null,2,3]                       binary tree, level order
 *
 * Arguments stream across lines and are grouped by the method's parameter
 * count. `#` and `//` start comments; anything after `->` is ignored.
 *
 * Usage: java Custom <methodName> <inputFile>
 */
public class Custom {
    static final long YOUR_TIMEOUT_MS = 5000, BRUTE_TIMEOUT_MS = 10000;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("usage: Custom <method> <inputFile>");
            System.exit(2);
        }
        String methodName = args[0];
        Method mine = find(Class.forName("Solution"), methodName);
        Method brute = null;
        try {
            brute = find(Class.forName("Brute"), methodName);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            // no oracle -- still useful, just no "expected" line
        }

        Parameter[] params = mine.getParameters();
        System.out.println("signature  " + signature(mine));

        List<Item> items;
        try {
            items = Reader.items(Files.readAllLines(Paths.get(args[1]), StandardCharsets.UTF_8));
        } catch (ParseError e) {
            System.out.println("\nINPUT ERROR  " + e.getMessage());
            System.exit(3);
            return;
        }
        if (items.isEmpty()) {
            System.out.println("\nno input -- add a case to input.txt (one value per parameter)");
            System.exit(3);
        }
        if (params.length > 0 && items.size() % params.length != 0) {
            System.out.println("\nINPUT ERROR  " + items.size() + " value(s) found, but the method takes "
                    + params.length + " per case -- the last case is incomplete");
            System.exit(3);
        }

        int cases = params.length == 0 ? 1 : items.size() / params.length;
        int bad = 0, badInput = 0;
        for (int c = 0; c < cases; c++) {
            List<Item> group = order(items.subList(c * params.length, (c + 1) * params.length), params);
            System.out.println();
            System.out.println("case " + (c + 1));
            try {
                if (!runCase(mine, brute, params, group)) bad++;
            } catch (ParseError e) {
                System.out.println("  INPUT ERROR  " + e.getMessage());
                badInput++;
            }
        }
        System.out.println();
        System.exit(badInput > 0 ? 3 : bad > 0 ? 1 : 0);
    }

    /** returns true when yours agrees with brute (or there is no brute) */
    static boolean runCase(Method mine, Method brute, Parameter[] params, List<Item> group) {
        for (int i = 0; i < params.length; i++) {
            Item it = group.get(i);
            // convert even when not shown: a bad value should fail here, not mid-call
            Object shown = Convert.to(it.value, params[i].getParameterizedType(), it.pos);
            String text = it.pos >= 0 ? Fmt.show(it.value) + "   (tail -> index " + it.pos + ")"
                                      : Fmt.show(shown);
            System.out.println("  " + params[i].getName() + " = " + text);
        }

        Outcome yours = call(mine, params, group, YOUR_TIMEOUT_MS);
        Outcome ref = brute == null ? null : call(brute, params, group, BRUTE_TIMEOUT_MS);

        System.out.println("  yours     " + yours.text + yours.timing());
        for (String m : yours.mutations) System.out.println("            " + m);
        if (ref == null) {
            System.out.println("  (no Brute.java -- nothing to compare against)");
            return true;
        }
        System.out.println("  expected  " + ref.text + "   (Brute)");

        if (ref.error != null) {
            System.out.println("  ??    Brute could not answer this input -- is it outside the constraints in problem.md?");
            return yours.error == null;
        }
        if (yours.error != null) {
            System.out.println("  FAIL  your code did not return");
            return false;
        }
        if (yours.text.equals(ref.text)) {
            System.out.println("  ok    matches");
            return true;
        }
        if (yours.canonical != null && yours.canonical.equals(ref.canonical)) {
            System.out.println("  ok    same elements, different order (fine if problem.md says any order)");
            return true;
        }
        System.out.println("  DIFF  differs from Brute -- unless problem.md accepts several answers, this is a bug");
        return false;
    }

    // ------------------------------------------------------------- invoking

    static class Outcome {
        String text, canonical, error;
        long nanos = -1;
        List<String> mutations = new ArrayList<>();

        String timing() {
            return nanos < 0 ? "" : String.format("   %.2f ms", nanos / 1e6);
        }
    }

    /** Builds a fresh copy of every argument (cyclic lists can't be copied,
     *  and either side may mutate its input), then calls with a timeout on a
     *  big-stack thread so deep recursion behaves like it would on a judge. */
    static Outcome call(Method m, Parameter[] params, List<Item> group, long timeoutMs) {
        Outcome o = new Outcome();
        Object[] argv = new Object[params.length];
        String[] before = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            Item it = group.get(i);
            argv[i] = Convert.to(it.value, params[i].getParameterizedType(), it.pos);
            before[i] = it.pos >= 0 ? null : Fmt.show(argv[i]);
        }

        Object[] result = new Object[1];
        Throwable[] thrown = new Throwable[1];
        long[] took = new long[1];
        Thread t = new Thread(null, () -> {
            try {
                Object inst = newInstance(m.getDeclaringClass());
                long t0 = System.nanoTime();
                result[0] = m.invoke(inst, argv);
                took[0] = System.nanoTime() - t0;
            } catch (InvocationTargetException e) {
                thrown[0] = e.getCause();
            } catch (Throwable e) {
                thrown[0] = e;
            }
        }, "custom", 1L << 29);
        t.setDaemon(true);
        t.start();
        try {
            t.join(timeoutMs);
        } catch (InterruptedException ignored) {
        }

        if (t.isAlive()) {
            o.error = o.text = "TIMEOUT after " + timeoutMs + " ms (infinite loop, or far too slow)";
            return o;
        }
        if (thrown[0] != null) {
            o.error = o.text = describe(thrown[0], m.getDeclaringClass().getName());
            return o;
        }
        o.nanos = took[0];

        ListNode inputHead = null;
        boolean cyclic = false;
        for (int i = 0; i < params.length; i++) {
            if (argv[i] instanceof ListNode && inputHead == null) {
                inputHead = (ListNode) argv[i];
                cyclic = group.get(i).pos >= 0;
            }
        }

        if (m.getReturnType() == void.class) {
            // in-place problems: the answer IS the mutated first argument
            o.text = cyclic ? "(cyclic list)" : Fmt.show(argv.length > 0 ? argv[0] : null);
            o.canonical = canonical(argv.length > 0 ? argv[0] : null);
            return o;
        }
        Object r = result[0];
        if (r instanceof ListNode && inputHead != null) {
            int idx = Fmt.indexOfNode(inputHead, (ListNode) r);
            if (cyclic) {
                o.text = idx >= 0 ? "node at index " + idx + " (val " + ((ListNode) r).val + ")"
                                  : "a node not from the input list (val " + ((ListNode) r).val + ")";
            } else {
                o.text = Fmt.show(r) + (idx >= 0 ? "   [node at index " + idx + "]" : "");
            }
        } else {
            o.text = Fmt.show(r);
        }
        o.canonical = canonical(r);
        for (int i = 0; i < params.length; i++) {
            if (before[i] == null) continue;
            String after = Fmt.show(argv[i]);
            if (!after.equals(before[i])) o.mutations.add(params[i].getName() + " is now " + after);
        }
        return o;
    }

    static Object newInstance(Class<?> c) throws Exception {
        Constructor<?> k = c.getDeclaredConstructor();
        k.setAccessible(true);
        return k.newInstance();
    }

    /** exception plus the line in *their* file where it happened */
    static String describe(Throwable e, String cls) {
        String s = "threw " + e.getClass().getSimpleName()
                + (e.getMessage() == null ? "" : ": " + e.getMessage());
        if (e instanceof StackOverflowError) s += " (recursion never bottoms out?)";
        for (StackTraceElement f : e.getStackTrace()) {
            if (f.getClassName().equals(cls) || f.getClassName().startsWith(cls + "$")) {
                s += "   at " + f.getFileName() + ":" + f.getLineNumber();
                break;
            }
        }
        return s;
    }

    /** order-insensitive fingerprint for collections, so [[1,2],[3]] ~ [[3],[2,1]] */
    static String canonical(Object o) {
        if (o instanceof Collection<?>) {
            List<String> parts = new ArrayList<>();
            for (Object e : (Collection<?>) o) parts.add(canonical(e));
            Collections.sort(parts);
            return parts.toString();
        }
        if (o instanceof Object[]) return canonical(Arrays.asList((Object[]) o));
        return Fmt.show(o);
    }

    static Method find(Class<?> c, String name) throws NoSuchMethodException {
        Method hit = null;
        for (Method m : c.getDeclaredMethods()) {
            if (!m.getName().equals(name) || m.isSynthetic()) continue;
            if (hit != null && hit.getParameterCount() != m.getParameterCount())
                throw new NoSuchMethodException(c.getName() + "." + name + " is overloaded");
            hit = m;
        }
        if (hit == null) throw new NoSuchMethodException(c.getName() + "." + name);
        hit.setAccessible(true);
        return hit;
    }

    static String signature(Method m) {
        StringJoiner j = new StringJoiner(", ", m.getName() + "(", ")");
        for (Parameter p : m.getParameters())
            j.add(simple(p.getParameterizedType()) + " " + p.getName());
        return simple(m.getGenericReturnType()) + " " + j;
    }

    static String simple(Type t) {
        return t.getTypeName().replace("java.util.", "").replace("java.lang.", "");
    }

    /** if every value in the case was given by name, line them up by name */
    static List<Item> order(List<Item> group, Parameter[] params) {
        Map<String, Item> byName = new HashMap<>();
        for (Item it : group) if (it.name != null) byName.put(it.name, it);
        if (byName.size() != params.length) return group;
        List<Item> out = new ArrayList<>();
        for (Parameter p : params) {
            Item it = byName.get(p.getName());
            if (it == null) return group;     // names don't match -- trust position
            out.add(it);
        }
        return out;
    }

    // -------------------------------------------------------------- parsing

    static class ParseError extends RuntimeException {
        ParseError(String msg) { super(msg); }
    }

    /** one argument value as written: name (optional), parsed value, cycle pos */
    static class Item {
        String name;
        Object value;
        int pos = -1;
    }

    /** a bare word like  hit  -- a string that wasn't quoted */
    static class Bare {
        final String s;
        Bare(String s) { this.s = s; }
        public String toString() { return s; }
    }

    static class Reader {
        final String src;
        final int lineNo;
        int i = 0;

        Reader(String src, int lineNo) { this.src = src; this.lineNo = lineNo; }

        static List<Item> items(List<String> lines) {
            List<Item> out = new ArrayList<>();
            for (int n = 0; n < lines.size(); n++) {
                String line = clean(lines.get(n));
                if (line.isEmpty()) continue;
                Reader r = new Reader(line, n + 1);
                r.line(out);
            }
            return out;
        }

        /** drop "Input:", comments, and anything after "->" (all outside quotes) */
        static String clean(String line) {
            String s = line.strip();
            String low = s.toLowerCase(Locale.ROOT);
            if (low.startsWith("output:") || low.startsWith("explanation:")) return "";
            if (low.startsWith("input:")) s = s.substring(6);
            char quote = 0;
            for (int k = 0; k < s.length(); k++) {
                char ch = s.charAt(k);
                if (quote != 0) {
                    if (ch == '\\') k++;
                    else if (ch == quote) quote = 0;
                    continue;
                }
                if (ch == '"' || ch == '\'') quote = ch;
                else if (ch == '#'
                        || s.startsWith("//", k)
                        || s.startsWith("->", k)) return s.substring(0, k).strip();
            }
            return s.strip();
        }

        void line(List<Item> out) {
            while (true) {
                ws();
                if (i >= src.length()) return;
                Item it = new Item();
                int save = i;
                String name = ident();
                ws();
                if (name != null && peek() == '=' && !src.startsWith("==", i)) {
                    i++;
                    it.name = name;
                } else {
                    i = save;
                }
                ws();
                it.value = value();
                // "[3,2,0,-4] pos=1" or "[3,2,0,-4], pos = 1": cycle position for
                // the list just read, unless the method really has a "pos" param
                ws();
                if (peek() == ',') { i++; ws(); }
                int mark = i;
                String maybePos = ident();
                ws();
                if ("pos".equals(maybePos) && peek() == '=') {
                    i++;
                    ws();
                    Object v = value();
                    if (!(v instanceof Long)) throw err("pos must be an integer");
                    it.pos = (int) (long) (Long) v;
                    ws();
                    if (peek() == ',') i++;
                } else {
                    i = mark;
                }
                out.add(it);
            }
        }

        Object value() {
            ws();
            char ch = peek();
            if (ch == '[') return list();
            if (ch == '"' || ch == '\'') return string();
            int start = i;
            while (i < src.length() && ",]".indexOf(src.charAt(i)) < 0) i++;
            String word = src.substring(start, i).strip();
            if (word.isEmpty()) throw err("expected a value");
            if (word.equals("null")) return null;
            if (word.equals("true") || word.equals("false")) return Boolean.valueOf(word);
            try {
                return Long.parseLong(word);
            } catch (NumberFormatException ignored) {
            }
            try {
                if (word.matches("-?\\d*\\.\\d+([eE][-+]?\\d+)?|-?\\d+[eE][-+]?\\d+"))
                    return Double.parseDouble(word);
            } catch (NumberFormatException ignored) {
            }
            return new Bare(word);
        }

        List<Object> list() {
            i++;                                   // [
            List<Object> out = new ArrayList<>();
            ws();
            if (peek() == ']') { i++; return out; }
            while (true) {
                out.add(value());
                ws();
                char ch = peek();
                if (ch == ',') { i++; continue; }
                if (ch == ']') { i++; return out; }
                throw err("expected ',' or ']'");
            }
        }

        String string() {
            char q = src.charAt(i++);
            StringBuilder sb = new StringBuilder();
            while (i < src.length() && src.charAt(i) != q) {
                char ch = src.charAt(i++);
                if (ch == '\\' && i < src.length()) {
                    char e = src.charAt(i++);
                    sb.append(e == 'n' ? '\n' : e == 't' ? '\t' : e);
                } else {
                    sb.append(ch);
                }
            }
            if (i >= src.length()) throw err("unterminated string");
            i++;
            return sb.toString();
        }

        String ident() {
            int start = i;
            if (i < src.length() && Character.isJavaIdentifierStart(src.charAt(i))) {
                while (i < src.length() && Character.isJavaIdentifierPart(src.charAt(i))) i++;
                return src.substring(start, i);
            }
            return null;
        }

        void ws() { while (i < src.length() && Character.isWhitespace(src.charAt(i))) i++; }

        char peek() { return i < src.length() ? src.charAt(i) : '\0'; }

        ParseError err(String msg) {
            return new ParseError("line " + lineNo + ", col " + (i + 1) + ": " + msg + "\n             " + src);
        }
    }

    // ----------------------------------------------------------- converting

    static class Convert {
        static Object to(Object v, Type t, int pos) {
            if (t instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) t;
                Class<?> raw = (Class<?>) pt.getRawType();
                Type el = pt.getActualTypeArguments()[0];
                Collection<Object> out;
                if (raw.isAssignableFrom(ArrayList.class)) out = new ArrayList<>();
                else if (raw.isAssignableFrom(LinkedHashSet.class)) out = new LinkedHashSet<>();
                else if (raw.isAssignableFrom(ArrayDeque.class)) out = new ArrayDeque<>();
                else throw new ParseError("parameter type " + simple(t) + " is not supported by dsa run");
                for (Object e : list(v, t)) out.add(to(e, el, -1));
                return out;
            }
            Class<?> c = (Class<?>) t;
            if (c == int.class || c == Integer.class) return (int) integer(v, Integer.MIN_VALUE, Integer.MAX_VALUE, t);
            if (c == long.class || c == Long.class) return integer(v, Long.MIN_VALUE, Long.MAX_VALUE, t);
            if (c == double.class || c == Double.class) {
                if (v instanceof Number) return ((Number) v).doubleValue();
                throw mismatch(v, t);
            }
            if (c == boolean.class || c == Boolean.class) {
                if (v instanceof Boolean) return v;
                throw mismatch(v, t);
            }
            if (c == char.class || c == Character.class) {
                String s = str(v, t);
                if (s.length() != 1) throw mismatch(v, t);
                return s.charAt(0);
            }
            if (c == String.class) return str(v, t);
            if (c == ListNode.class) {
                int[] vals = (int[]) to(v, int[].class, -1);
                if (pos >= vals.length) throw new ParseError("pos=" + pos + " but the list has only " + vals.length + " node(s)");
                return Fmt.listWithCycle(vals, pos);
            }
            if (c == TreeNode.class) {
                List<Object> l = list(v, t);
                Integer[] vals = new Integer[l.size()];
                for (int k = 0; k < vals.length; k++)
                    vals[k] = l.get(k) == null ? null : (Integer) to(l.get(k), Integer.class, -1);
                return Fmt.tree(vals);
            }
            if (c == char[].class && (v instanceof String || v instanceof Bare)) return str(v, t).toCharArray();
            if (c.isArray()) {
                List<Object> l = list(v, t);
                Class<?> el = c.getComponentType();
                Object arr = Array.newInstance(el, l.size());
                for (int k = 0; k < l.size(); k++) Array.set(arr, k, to(l.get(k), el, -1));
                return arr;
            }
            throw new ParseError("parameter type " + simple(t) + " is not supported by dsa run");
        }

        static long integer(Object v, long lo, long hi, Type t) {
            if (!(v instanceof Long)) throw mismatch(v, t);
            long x = (Long) v;
            if (x < lo || x > hi) throw new ParseError(x + " does not fit in " + simple(t));
            return x;
        }

        static String str(Object v, Type t) {
            if (v instanceof String || v instanceof Bare || v instanceof Long) return v.toString();
            throw mismatch(v, t);
        }

        @SuppressWarnings("unchecked")
        static List<Object> list(Object v, Type t) {
            if (v instanceof List) return (List<Object>) v;
            throw mismatch(v, t);
        }

        static ParseError mismatch(Object v, Type t) {
            String got = v == null ? "null" : v instanceof List ? "a list " + Fmt.show(v)
                    : v instanceof String ? "the string \"" + v + "\"" : String.valueOf(v);
            return new ParseError("expected " + simple(t) + ", got " + got);
        }
    }
}
