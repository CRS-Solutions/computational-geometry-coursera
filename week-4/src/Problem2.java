import java.util.*;

public class Problem2 {
    public static List<Point> solve(List<Point> points) {
        List<Point> result = new ArrayList<>();

        Polygon polygon = new Polygon(points);
        Vertex vertex = polygon.head();
        while (polygon.size() > 3) {
            final Vertex next = vertex.next;
            if (vertex.ear) {
                result.add(vertex.prev.point);
                result.add(vertex.point);
                result.add(vertex.next.point);
                polygon.delete(vertex);
            }

            vertex = next;
        }

        if (polygon.size() == 3) {
            result.add(polygon.head.point);
            result.add(polygon.head.next.point);
            result.add(polygon.head.next.next.point);
        }

        return result;
    }



    //region Math

    /**
     * @return the signed of the area of the triangle formed by points a, b, c in counter clockwise order.
     */
    public static int area2Sign(Point a, Point b, Point c) {
        final long vx = b.x - a.x;
        final long vy = b.y - a.y;
        final long ux = c.x - a.x;
        final long uy = c.y - a.y;
        final long area = vx * uy - ux * vy;
        return area < 0 ? -1 : (area == 0 ? 0 : 1);
    }

    /**
     * @return true, if ab x ac forms a left turn.
     */
    public static boolean isLeft(Point a, Point b, Point c) {
        return area2Sign(a, b, c) > 0;
    }

    /**
     * @return true, if ab x ac forms a left turn or collinear.
     */
    public static boolean isLeftOn(Point a, Point b, Point c) {
        return area2Sign(a, b, c) >= 0;
    }

    //endregion

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final int n = scanner.nextInt();
            final List<Point> points = new ArrayList<>(n);
            for (int i = 0; i < n; ++i) {
                points.add(readPoint(scanner));
            }
            List<Point> triangles = solve(points);
            print(triangles);
        }
    }

    private static Point readPoint(Scanner scanner) {
        final int x = scanner.nextInt();
        final int y = scanner.nextInt();
        return new Point(x, y);
    }

    private static void print(List<Point> points) {
        if (points.size() % 3 != 0) {
            throw new IllegalArgumentException("Invalid triangle list");
        }

        System.out.println(points.size() / 3);

        int i = 0;
        while (i < points.size()) {
            Point a = points.get(i++);
            Point b = points.get(i++);
            Point c = points.get(i++);
            System.out.printf("%d %d %d %d %d %d\n", a.x, a.y, b.x, b.y, c.x, c.y);
        }
    }

    /**
     * Immutable polygon
     */
    public static class Polygon {
        private Vertex head;
        private int size;

        public Polygon(List<Point> points) {
            if (points.size() < 3) {
                throw new IllegalArgumentException("Polygon should contain at least 3 points");
            }
            size = points.size();
            head = createVertices(points);
        }

        public Vertex head() {
            return head;
        }

        public void delete(Vertex v) {
            if (size == 3) {
                throw new IllegalStateException("Can't remove vertex");
            }
            final Vertex prev = v.prev;
            final Vertex next = v.next;
            prev.next = next;
            next.prev = prev;

            if (head == v) {
                head = next;
            }

            prev.ear = isEar(prev);
            next.ear = isEar(next);

            v.prev = v.next = null;
            --size;
        }

        private static Vertex createVertices(List<Point> points) {
            Vertex head = null, tail = null;
            for (Point p : points) {
                Vertex vertex = new Vertex(p);
                if (tail != null) {
                    tail.next = vertex;
                    vertex.prev = tail;
                } else {
                    head = vertex;
                }
                tail = vertex;
            }
            tail.next = head;
            head.prev = tail;

            Vertex vertex = head;
            do {
                vertex.ear = isEar(vertex);
                vertex = vertex.next;
            } while (vertex != head);

            return head;
        }

        private static boolean isEar(Vertex vertex) {
            final Point a = vertex.prev.point;
            final Point b = vertex.next.point;

            Vertex curr = vertex;
            do {
                final Point c = vertex.point;
                final Point d = vertex.next.point;
                if (a != c && a != d && b != c && b != d && intersect(a, b, c, d)) {
                    return false;
                }
                vertex = vertex.next;
            } while (curr != vertex);

            return isInternalDiagonal(vertex.prev, vertex.next);
        }

        private static boolean intersect(Point a, Point b, Point c, Point d) {
            return isLeft(a, b, c) != isLeft(a, b, d) && isLeft(c, d, a) != isLeft(c, d, b);
        }

        /**
         * "Computational Geometry in C Second Edition", Joseph O'Rourke
         *  1.6.1. Diagonals, Internal or External
         */
        private static boolean isInternalDiagonal(Vertex v1, Vertex v2) {
            final Point a = v1.point;
            final Point b = v2.point;
            final Point aPrev = v1.prev.point;
            final Point aNext = v1.next.point;

            if (isLeftOn(a, aNext, aPrev)) {
                return isLeft(a, b, aPrev) && isLeftOn(b, a, aNext);
            }

            return !(isLeftOn(a, b, aNext) && isLeftOn(b, a, aPrev));
        }

        public int size() {
            return size;
        }
    }

    public static final class Vertex {
        public final Point point;
        public Vertex next;
        public Vertex prev;
        public boolean ear;

        public Vertex(Point point) {
            this.point = point;
        }
    }

    /**
     * Immutable point
     */
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
}
