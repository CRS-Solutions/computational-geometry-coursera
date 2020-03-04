import java.util.*;

/**
 * Sutherland-Holman convex polygon intersection implementation.
 */
public class Problem2SutherlandHolman {
    private static final int LEFT_TURN = 1;
    private static final int RIGHT_TURN = -1;
    private static final int NO_TURN = 0;

    /**
     * Returns the "turn" direction between vectors ab and ac
     */
    private static int turn(Point a, Point b, Point c) {
        final float vx = b.x - a.x;
        final float vy = b.y - a.y;
        final float ux = c.x - a.x;
        final float uy = c.y - a.y;
        final float area2 = vx * uy - ux * vy;
        return area2 < 0 ? RIGHT_TURN : (area2 == 0 ? NO_TURN : LEFT_TURN);
    }

    /**
     * @return intersection polygon of convex polygons p and q (or null if no intersection).
     */
    public static Polygon solve(Polygon p, Polygon q) {
        List<Point> points = q.getPoints();

        for (int i = 0; i < p.size() && !points.isEmpty(); ++i) {
            final Point a = i > 0 ? p.get(i - 1) : p.get(p.size() - 1);
            final Point b = p.get(i);

            final List<Point> temp = new ArrayList<>(points.size());
            for (int j = 0; j < points.size(); ++j) {
                final Point c = j > 0 ? points.get(j - 1) : points.get(points.size() - 1);
                final Point d = points.get(j);

                final int abc = turn(a, b, c);
                final int abd = turn(a, b, d);
                if (abc == RIGHT_TURN && abd == RIGHT_TURN) { // points c and d lay outside of half-plane ab
                    continue;
                } else if (abc == LEFT_TURN && abd == LEFT_TURN) { // points c and d lay inside of half-plane ab
                    temp.add(d);
                } else if (abc != abd) { // points c and d lay on different sides of half-plane ab
                    temp.add(getIntersection(a, b, c, d));
                    if (abd == LEFT_TURN) {
                        temp.add(d);
                    }
                } else { // points c and d lay on the border of half-plane ab
                    assert abc == NO_TURN && abd == NO_TURN;
                    temp.add(d);
                }
            }
            points = temp;
        }

        List<Point> polygonPoints = cleanPolygonPoints(points);
        return polygonPoints.size() < 3 ? null : new Polygon(polygonPoints);
    }

    /**
     * Removes degenerated points from the polygon points.
     */
    private static List<Point> cleanPolygonPoints(List<Point> points) {
        if (points.isEmpty()) {
            return points;
        }
        /*
        The problem statement claims that all the intersection points are integers so we "round" each
        point to avoid floating point errors.
        */

        List<Point> result = new ArrayList<>(points.size());
        result.add(points.get(0).round());
        result.add(points.get(1).round());
        for (int i = 2; i < points.size(); ++i) {
            Point c = points.get(i).round();
            while (result.size() >= 2) {
                Point a = result.get(result.size() - 2);
                Point b = result.get(result.size() - 1);
                if (turn(a, b, c) == LEFT_TURN) {
                    break;
                }
                result.remove(result.size() - 1);
            }
            result.add(c);
        }

        // the last point might be duplicate, too
        if (result.size() > 0 && result.get(0).equals(result.get(result.size() - 1))) {
            result.remove(result.size() - 1);
        }

        return result;
    }

    /**
     * Returns and intersection point of non-collinear lines ab and cd.
     */
    private static Point getIntersection(Point a, Point b, Point c, Point d) {
        final float dx1 = b.x - a.x;
        final float dy1 = b.y - a.y;
        final float dx2 = d.x - c.x;
        final float dy2 = d.y - c.y;

        final float x, y;
        if (dx1 != 0 && dx2 != 0) {
            final float k1 = dy1 / dx1;
            final float k2 = dy2 / dx2;
            if (MathUtil.approxEqual(k1, k2)) {
                throw new IllegalArgumentException("Lines are collinear");
            }
            final float b1 = a.y - k1 * a.x;
            final float b2 = c.y - k2 * c.x;
            x = (b2 - b1) / (k1 - k2);
            y = k1 * x + b1;
        } else if (dx1 != 0) {
            final float k1 = dy1 / dx1;
            final float b1 = a.y - k1 * a.x;
            x = c.x;
            y = k1 * x + b1;
        } else if (dx2 != 0) {
            final float k2 = dy2 / dx2;
            final float b2 = c.y - k2 * c.x;
            x = a.x;
            y = k2 * x + b2;
        } else {
            throw new IllegalArgumentException("Lines are collinear");
        }

        return new Point(x, y);
    }


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Polygon p = readPolygon(scanner);
            Polygon q = readPolygon(scanner);
            Polygon intersection = solve(p, q);
            print(intersection);
        }
    }

    private static Polygon readPolygon(Scanner scanner) {
        int n = scanner.nextInt();
        List<Point> points = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new Point(x, y));
        }
        return new Polygon(points);
    }

    private static void print(Polygon polygon) {
        if (polygon != null) {
            System.out.println(polygon.size());
            System.out.println(polygon);
        } else {
            System.out.println(0);
        }
    }

    /**
     * Immutable polygon
     */
    public static class Polygon implements Iterable<Point> {
        private final List<Point> points;

        public Polygon(List<Point> points) {
            if (points.size() < 3) {
                throw new IllegalArgumentException("Polygon should contain at least 3 points");
            }
            this.points = new ArrayList<>(points);
        }

        public Point get(int i) {
            return points.get(i);
        }

        public List<Point> getPoints() {
            return new ArrayList<>(points);
        }

        public int size() {
            return points.size();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Polygon points1 = (Polygon) o;
            return Objects.equals(points, points1.points);
        }

        @Override
        public int hashCode() {
            return Objects.hash(points);
        }

        @Override
        public Iterator<Point> iterator() {
            return points.iterator();
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            for (Point p : points) {
                if (result.length() > 0) {
                    result.append(' ');
                }
                result.append(Math.round(p.x));
                result.append(' ');
                result.append(Math.round(p.y));
            }
            return result.toString();
        }
    }

    /**
     * Immutable point
     */
    public static class Point {
        public final float x;
        public final float y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return new point with rounded coordinates
         */
        public Point round() {
            return new Point(Math.round(x), Math.round(y));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return MathUtil.approxEqual(x, point.x) && MathUtil.approxEqual(y, point.y);
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

    private static class MathUtil {
        public static boolean approxEqual(float a, float b) {
            return Math.abs(a - b) < 0.0001f;
        }
    }
}
