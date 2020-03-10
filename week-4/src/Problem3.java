import java.io.*;
import java.util.*;

public class Problem3 {
    private static final Comparator<Point> COMPARATOR = (p1, p2) -> {
        int cmp = Integer.compare(p2.y, p1.y);
        if (cmp != 0) {
            return cmp;
        }
        return Integer.compare(p1.x, p2.x);
    };

    public static List<Point> solve(List<Point> points) {
        points.sort(COMPARATOR);

        List<Point> result = new ArrayList<>();

        final Stack<Point> S = new Stack<>();
        S.push(points.get(0));
        S.push(points.get(1));
        for (int i = 2; i < points.size() - 1; ++i) {
            Point u = points.get(i);
            Point v = S.peek();
            if (isSameChain(u, v)) {
                Point w1 = S.pop();
                Point w2 = S.peek();
                while (isDiagonal(u, w1, w2)) {
                    result.add(u);
                    result.add(w2);
                    w1 = S.pop();
                    if (S.isEmpty()) {
                        break;
                    }
                    w2 = S.peek();
                }
                S.push(w1);
            } else {
                // add diagonals to each point on the stack besides the first one
                for (int j = S.size() - 1; j > 0; --j) {
                    result.add(u);
                    result.add(S.get(j));
                }
                S.clear();
                S.push(v);
            }
            S.push(u);
        }

        return result;
    }

    private static boolean isDiagonal(Point a, Point b, Point c) {
        return a.index < b.index ? isLeft(a, b, c) : isLeft(b, a, c);
    }

    private static boolean isLeft(Point a, Point b, Point c) {
        final long vx = b.x - a.x;
        final long vy = b.y - a.y;
        final long ux = c.x - a.x;
        final long uy = c.y - a.y;
        return vx * uy - ux * vy > 0L;
    }

    private static boolean isSameChain(Point u, Point v) {
        return Math.abs(u.index - v.index) == 1;
    }

    public static void main(String[] args) throws IOException {
        try (FastScanner scanner = new FastScanner(System.in)) {
            final int n = scanner.nextInt();
            final List<Point> points = new ArrayList<>(n);
            for (int i = 0; i < n; ++i) {
                final int x = scanner.nextInt();
                final int y = scanner.nextInt();
                points.add(new Point(i, x, y));
            }

            List<Point> diagonals = solve(points);
            print(diagonals);
        }
    }

    private static void print(List<Point> points) {
        if (points.size() % 2 != 0) {
            throw new IllegalArgumentException("Invalid triangle list");
        }

        System.out.println(points.size() / 2);

        int i = 0;
        while (i < points.size()) {
            Point a = points.get(i++);
            Point b = points.get(i++);
            System.out.printf("%d %d %d %d\n", a.x, a.y, b.x, b.y);
        }
    }

    public static final class Point {
        public final int index;
        public final int x;
        public final int y;

        public Point(int index, int x, int y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("%d: (%d, %d)", index, x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static class FastScanner implements Closeable {
        private final Reader stream;
        private final BufferedReader reader;
        private String line;
        private int pos;

        public FastScanner(String in) {
            stream = new StringReader(in);
            reader = new BufferedReader(stream);
            line = "";
        }

        public FastScanner(InputStream in) {
            stream = new InputStreamReader(in);
            reader = new BufferedReader(stream);
            line = "";
        }

        public int nextInt() throws IOException {
            // check if we need to read another line
            if (pos == line.length()) {
                readLine();
            }

            // skip all whitespaces
            while (pos < line.length() && isWhitespace(line.charAt(pos))) {
                pos += 1;

                // read another line (if necessary)
                if (pos == line.length()) {
                    readLine();
                }
            }

            int num = 0;
            int sign = 1;
            if (line.charAt(pos) == '-') {
                sign = -1;
                pos += 1;
            }

            // check if we reached the end of line
            if (pos == line.length()) {
                throw new NumberFormatException();
            }

            // next character must be digit
            if (!isDigit(line.charAt(pos))) {
                throw new NumberFormatException();
            }

            // read next number
            while (pos < line.length()) {
                final char c = line.charAt(pos);
                if (!isDigit(c)) {
                    break;
                }
                num = 10 * num + (c - '0');
                pos++;
            }
            return sign * num;
        }

        private void readLine() throws IOException {
            line = reader.readLine();
            if (line == null) {
                throw new EOFException();
            }
            pos = 0;
        }

        private static boolean isDigit(char c) {
            return c >= '0' && c <= '9';
        }

        private static boolean isWhitespace(char c) {
            return c == ' ' || c == '\n' || c == '\t';
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }
    }
}