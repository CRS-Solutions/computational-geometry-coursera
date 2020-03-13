import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.min;
import static java.lang.Math.abs;

public class Problem3KdTree {
    public static int[] solve(List<Point> points, List<Point> query) {
        final KdTree tree = new KdTree(points);
        int[] result = new int[query.size()];
        int i = 0;
        for (Point q : query) {
            result[i++] = solve(tree, q);
        }
        return result;
    }

    private static int solve(KdTree tree, Point q) {
        final Point p = tree.nearestNeighbour(q);
        return KdTree.dist(p, q);
    }

    public static void main(String[] args) throws IOException {
        try (FastScanner scanner = new FastScanner(System.in)) {
            final List<Point> points = readPoints(scanner);
            final List<Point> query = readPoints(scanner);
            int[] counts = solve(points, query);
            print(counts);
        }
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
        private static final Comparator<Point>[] COMPARATORS = new Comparator[] { X_COMPARATOR, Y_COMPARATOR};

        public static final class Node {
            public final Point point;
            public Node left;
            public Node right;

            public Node(Point point) {
                this.point = point;
            }
        }

        public final Node root;
        private final List<Point> points;

        public KdTree(List<Point> points) {
            this.points = points;
            root = buildTree(points);
        }

        public Point nearestNeighbourBruteForce(Point point) {
            Point best = null;
            long bestDist = Long.MAX_VALUE;
            for (Point p : points) {
                long dist = dist(p, point);
                if (dist < bestDist) {
                    bestDist = dist;
                    best = p;
                }
            }
            return best;
        }

        public Point nearestNeighbour(Point point) {
            return nearestNeighbour(root, point, 0);
        }

        private Point nearestNeighbour(Node root, Point query, int depth) {
            if (root == null) {
                return null;
            }

            final Point point = root.point;
            final int axis = depth % 2;

            final Node next, opposite;
            if (query.get(axis) < point.get(axis)) { // query point on the left of p
                next = root.left;
                opposite = root.right;
            } else {
                next = root.right;
                opposite = root.left;
            }

            Point best = closerPoint(query, point, nearestNeighbour(next, query, depth + 1));
            if (dist(query, best) > abs(query.get(axis) - point.get(axis))) {
                best = closerPoint(query, best, nearestNeighbour(opposite, query, depth + 1));
            }

            return best;
        }

        private Point closerPoint(Point q, Point p1, Point p2) {
            if (p1 == null) {
                return p2;
            }
            if (p2 == null) {
                return p1;
            }
            return dist(q, p1) < dist(q, p2) ? p1 : p2;
        }

        static int dist(Point p, Point q) {
            final int dx = abs(p.x - q.x);
            final int dy = abs(p.y - q.y);
            return min(dx, dy) + abs(dx - dy);
        }

        private static Node buildTree(List<Point> points) {
            return buildTree(new ArrayList<>(points), 0);
        }

        private static Node buildTree(List<Point> points, int depth) {
            if (points.isEmpty()) {
                return null;
            }
            final int axis = depth % 2;
            points.sort(COMPARATORS[axis]);
            final int mid = points.size() / 2;
            Node node = new Node(points.get(mid));
            node.left = buildTree(points.subList(0, mid), depth + 1);
            node.right = buildTree(points.subList(mid + 1, points.size()), depth + 1);
            return node;
        }
    }

    public static final class Point {
        public static final int X = 0;
        public static final int Y = 1;

        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int get(int axis) {
            if (axis == X) {
                return x;
            }
            if (axis == Y) {
                return y;
            }

            throw new IllegalArgumentException("Illegal axis: " + axis);
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
