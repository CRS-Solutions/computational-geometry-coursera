import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Problem1 {
    public static List<DiagonalResult> solve(List<Point> points) {
        final List<DiagonalResult> result = new ArrayList<>();
        for (int i = 0; i < points.size(); ++i) {
            final Point a = points.get(i);
            // skip adjusted points
            final int lo = i + 2;
            final int hi = points.size() - (i > 0 ? 1 : 2);
            for (int j = lo; j <= hi; ++j) {
                final Point b = points.get(j);
                final DiagonalType type = getDiagonalType(points, i, j);
                result.add(new DiagonalResult(a, b, type));
            }
        }
        return result;
    }

    private static DiagonalType getDiagonalType(List<Point> points, int i, int j) {
        final Point a = points.get(i);
        final Point b = points.get(j);

        for (int k = 0; k < points.size(); ++k) {
            final Point c = k > 0 ? points.get(k - 1) : points.get(points.size() - 1);
            final Point d = points.get(k);

            if (a == c || a == d || b == c || b == d) {
                continue;
            }

            if (intersect(a, b, c, d)) {
                return DiagonalType.INTERSECT;
            }
        }

        return isInternalDiagonal(points, i, j) ? DiagonalType.INTERNAL : DiagonalType.EXTERNAL;
    }

    /**
     * Return true, if segments ab and cd have a single point of intersection.
     */
    private static boolean intersect(Point a, Point b, Point c, Point d) {
        return area2Sign(a, b, c) != area2Sign(a, b, d) && area2Sign(c, d, a) != area2Sign(c, d, b);
    }

    /**
     * "Computational Geometry in C Second Edition", Joseph O'Rourke
     *  1.6.1. Diagonals, Internal or External
     */
    private static boolean isInternalDiagonal(List<Point> points, int i, int j) {
        final Point a = points.get(i);
        final Point b = points.get(j);
        final Point aPrev = i > 0 ? points.get(i - 1) : points.get(points.size() - 1);
        final Point aNext = i < points.size() - 1 ? points.get(i + 1) : points.get(0);

        if (isLeftOn(a, aNext, aPrev)) {
            return isLeft(a, b, aPrev) && isLeftOn(b, a, aNext);
        }

        return !(isLeftOn(a, b, aNext) && isLeftOn(b, a, aPrev));
    }

    public static void main(String[] args) throws IOException {
        try (FastScanner scanner = new FastScanner(System.in)) {
            final int n = scanner.nextInt();
            List<Point> points = new ArrayList<>(n);
            for (int i = 0; i < n; ++i) {
                points.add(readPoint(scanner));
            }
            final List<DiagonalResult> result = solve(points);
            print(result);
        }
    }

    private static void print(List<DiagonalResult> result) {
        System.out.println(result.size());
        for (DiagonalResult r : result) {
            System.out.printf("%d %d %d %d %s\n", r.a.x, r.a.y, r.b.x, r.b.y, r.type);
        }
    }

    private static Point readPoint(FastScanner scanner) throws IOException {
        final int x = scanner.nextInt();
        final int y = scanner.nextInt();
        return new Point(x, y);
    }

    //region Math

    /**
     * @return 2 x signed area of the triangle formed by points a, b, c in counter clockwise order.
     */
    public static long area2(Point a, Point b, Point c) {
        final long vx = b.x - a.x;
        final long vy = b.y - a.y;
        final long ux = c.x - a.x;
        final long uy = c.y - a.y;
        return vx * uy - ux * vy;
    }

    /**
     * @return the signed of the area of the triangle formed by points a, b, c in counter clockwise order.
     */
    public static int area2Sign(Point a, Point b, Point c) {
        final long area = area2(a, b, c);
        return area < 0 ? -1 : (area == 0 ? 0 : 1);
    }

    /**
     * @return true, if ab x ac forms a left turn.
     */
    public static boolean isLeft(Point a, Point b, Point c) {
        return area2(a, b, c) > 0;
    }

    /**
     * @return true, if ab x ac forms a left turn or ab and ac are collinear.
     */
    public static boolean isLeftOn(Point a, Point b, Point c) {
        return area2(a, b, c) >= 0;
    }

    /**
     * @return true, if ab x ac forms a right turn.
     */
    public static boolean isRight(Point a, Point b, Point c) {
        return area2(a, b, c) < 0;
    }

    /**
     * @return true, if ab x ac forms a right turn or ab and ac are collinear.
     */
    public static boolean isRightOn(Point a, Point b, Point c) {
        return area2(a, b, c) <= 0;
    }

    //endregion

    //region Classes

    public static final class Point {
        public final int x;
        public final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
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

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public enum DiagonalType {
        INTERNAL,
        INTERSECT,
        EXTERNAL
    }

    public static final class DiagonalResult {
        public final Point a;
        public final Point b;
        public final DiagonalType type;

        public DiagonalResult(Point a, Point b, DiagonalType type) {
            this.a = a;
            this.b = b;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DiagonalResult that = (DiagonalResult) o;
            return a.equals(that.a) && b.equals(that.b) && type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, type);
        }

        @Override
        public String toString() {
            return String.format("%d %d %d %d %s", a.x, a.y, b.x, b.y, type);
        }
    }

    //endregion

    //region I/O

    private static class FastScanner implements Closeable {
        private final InputStreamReader stream;
        private final BufferedReader reader;
        private String line;
        private int pos;

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

    //endregion
}
