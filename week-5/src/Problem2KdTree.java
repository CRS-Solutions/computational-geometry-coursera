import java.io.*;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Problem2KdTree {
    public static int[] solve(List<Point> points, List<Rect> query) {
        final KdTree tree = new KdTree(points);
        int[] counts = new int[query.size()];
        int i = 0;
        for (Rect r : query) {
            counts[i] = tree.countPoints(r.x1, r.y1, r.x2, r.y2);
            ++i;
        }

        return counts;
    }

    public static void main(String[] args) throws IOException {
        try (FastScanner scanner = new FastScanner(System.in)) {
            final List<Point> points = readPoints(scanner);
            final List<Rect> query = readRects(scanner);
            int[] counts = solve(points, query);
            print(counts);
        }
    }

    private static List<Rect> readRects(FastScanner scanner) throws IOException {
        final int n = scanner.nextInt();
        List<Rect> rects = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            int x1 = scanner.nextInt();
            int y1 = scanner.nextInt();
            int x2 = scanner.nextInt();
            int y2 = scanner.nextInt();
            rects.add(new Rect(x1, y1, x2, y2));
        }
        return rects;
    }

    private static List<Point> readPoints(FastScanner scanner) throws IOException {
        final int n = scanner.nextInt();
        final List<Point> points = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            points.add(readPoint(scanner));
        }
        return points;
    }

    private static Point readPoint(FastScanner scanner) throws IOException {
        final int x = scanner.nextInt();
        final int y = scanner.nextInt();
        return new Point(x, y);
    }

    private static void print(int[] counts) {
        for (int i = 0; i < counts.length; ++i) {
            System.out.println(counts[i]);
        }
    }

    public static final class KdTree {
        private static final Comparator<Point> X_COMPARATOR = (p1, p2) -> Integer.compare(p1.x, p2.x);
        private static final Comparator<Point> Y_COMPARATOR = (p1, p2) -> Integer.compare(p1.y, p2.y);

        public static final class Node {
            public final Point point;
            public Node left;
            public Node right;

            public Node(Point point) {
                this.point = point;
            }
        }

        public final Node root;

        public KdTree(List<Point> points) {
            root = buildTree(points);
        }

        public int countPoints(int x1, int y1, int x2, int y2) {
            final int xMin = min(x1, x2);
            final int yMin = min(y1, y2);
            final int xMax = max(x1, x2);
            final int yMax = max(y1, y2);
            return countPoints(root, xMin, yMin, xMax, yMax, true);
        }

        private int countPoints(Node root, int xMin, int yMin, int xMax, int yMax, boolean vertical) {
            if (root == null) {
                return 0;
            }

            final Point p = root.point;
            int count = contains(xMin, yMin, xMax, yMax, p) ? 1 : 0;

            if (vertical) {
                final int x = p.x;
                if (x >= xMin)
                    count += countPoints(root.left, xMin, yMin, min(x, xMax), yMax, false);
                if (x <= xMax)
                    count += countPoints(root.right, max(x, xMin), yMin, xMax, yMax, false);
            } else {
                final int y = p.y;
                if (y >= yMin)
                    count += countPoints(root.left, xMin, yMin, xMax, min(y, yMax), true);
                if (y <= yMax)
                    count += countPoints(root.right, xMin, max(y, yMin), xMax, yMax, true);
            }

            return count;
        }

        private static boolean contains(int xMin, int yMin, int xMax, int yMax, Point p) {
            final int x = p.x;
            final int y = p.y;
            return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
        }

        private static Node buildTree(List<Point> points) {
            return buildTree(new ArrayList<>(points), true);
        }

        private static Node buildTree(List<Point> points, boolean vertical) {
            if (points.isEmpty()) {
                return null;
            }
            points.sort(vertical ? X_COMPARATOR : Y_COMPARATOR);
            final int mid = points.size() / 2;
            Node node = new Node(points.get(mid));
            node.left = buildTree(points.subList(0, mid), !vertical);
            node.right = buildTree(points.subList(mid + 1, points.size()), !vertical);
            return node;
        }
    }

    public static final class Rect {
        public final int x1, y1, x2, y2;

        public Rect(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public static final class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
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

    //region I/O

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

    //endregion
}
