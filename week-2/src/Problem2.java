import java.util.*;

public class Problem2 {
    private static final Comparator<Point> COMPARATOR = (p1, p2) -> {
        // sort by x-coordinate first
        final int cmp = Integer.compare(p1.x, p2.x);
        if (cmp != 0) {
            return cmp;
        }

        // sort by y-coordinate second
        return Integer.compare(p1.y, p2.y);
    };

    private static boolean isLeftTurn(Point a, Point b, Point c) {
        long vx = b.x - a.x;
        long vy = b.y - a.y;
        long ux = c.x - a.x;
        long uy = c.y - a.y;
        return vx * uy - ux * vy > 0;
    }

    public static List<Point> solve(Point[] polygon) {
        Arrays.sort(polygon, COMPARATOR);

        // lower hull
        Stack<Point> lowerHull = new Stack<>();
        lowerHull.push(polygon[0]);
        lowerHull.push(polygon[1]);
        for (int i = 2; i < polygon.length; ++i) {
            Point c = polygon[i];
            while (lowerHull.size() >= 2) {
                Point a = lowerHull.get(lowerHull.size() - 2);
                Point b = lowerHull.get(lowerHull.size() - 1);
                if (isLeftTurn(a, b, c)) {
                    break;
                }
                lowerHull.pop();
            }
            lowerHull.push(c);
        }
        // remove last vertex (since it's gonna be the first vertex of the upper hull)
        lowerHull.pop();

        // upper hull
        Stack<Point> upperHull = new Stack<>();
        upperHull.push(polygon[polygon.length - 1]);
        upperHull.push(polygon[polygon.length - 2]);
        for (int i = polygon.length - 3; i >= 0; --i) {
            Point c = polygon[i];
            while (upperHull.size() >= 2) {
                Point a = upperHull.get(upperHull.size() - 2);
                Point b = upperHull.get(upperHull.size() - 1);
                if (isLeftTurn(a, b, c)) {
                    break;
                }
                upperHull.pop();
            }
            upperHull.push(c);
        }
        // remove last vertex (since it's gonna be the first vertex of the lower hull)
        upperHull.pop();

        // concat hulls
        List<Point> hull = new ArrayList<>(lowerHull.size() + upperHull.size());
        hull.addAll(lowerHull);
        hull.addAll(upperHull);
        return hull;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final int n = scanner.nextInt();
            final Point[] polygon = new Point[n];
            for (int i = 0; i < polygon.length; ++i) {
                final int x = scanner.nextInt();
                final int y = scanner.nextInt();
                polygon[i] = new Point(x, y);
            }

            List<Point> hull = solve(polygon);
            printHull(hull);
        }
    }

    private static void printHull(List<Point> hull) {
        StringBuilder result = new StringBuilder();
        for (Point p : hull) {
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(p.x);
            result.append(' ');
            result.append(p.y);
        }

        System.out.println(hull.size());
        System.out.println(result);
    }

    public static class Point {
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
            return x == point.x &&
                    y == point.y;
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
