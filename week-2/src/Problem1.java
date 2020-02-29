import java.util.Scanner;

public class Problem1 {
    private static final String NOT_CONVEX = "NOT_CONVEX";
    private static final String CONVEX = "CONVEX";

    private static int area2(Point a, Point b, Point c) {
        int vx = b.x - a.x;
        int vy = b.y - a.y;
        int ux = c.x - a.x;
        int uy = c.y - a.y;
        return vx * uy - ux * vy;
    }

    public static String solve(Point[] polygon) {
        final int n = polygon.length;
        Point a = polygon[n - 2];
        Point b = polygon[n - 1];
        for (int i = 0; i < n; ++i) {
            Point c = polygon[i];
            int area2 = area2(a, b, c);
            if (area2 <= 0) {
                return NOT_CONVEX;
            }

            a = b;
            b = c;
        }

        return CONVEX;
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


            System.out.println(solve(polygon));
        }
    }

    public static class Point {
        public final int x;
        public final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
