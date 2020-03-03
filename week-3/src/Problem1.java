import java.util.Objects;
import java.util.Scanner;

public class Problem1 {

    private static int dotSign(Point a, Point b, Point c, Point d) {
        long x1 = b.x - a.x;
        long y1 = b.y - a.y;
        long x2 = d.x - c.x;
        long y2 = d.y - c.y;
        return sign(x1 * x2 + y1 * y2);
    }

    private static int area2Sign(Point a, Point b, Point c) {
        long vx = b.x - a.x;
        long vy = b.y - a.y;
        long ux = c.x - a.x;
        long uy = c.y - a.y;
        return sign(vx * uy - ux * vy);
    }

    public static Result solve(Point a, Point b, Point c, Point d) {
        // check if points intersect
        if (intersect(a, b, c, d)) {
            final int dx1 = b.x - a.x;
            final int dy1 = b.y - a.y;
            final int dx2 = d.x - c.x;
            final int dy2 = d.y - c.y;

            final float x, y;
            if (dx1 != 0 && dx2 != 0) {
                final float k1 = (float) dy1 / (float) dx1;
                final float b1 = a.y - k1 * a.x;
                final float k2 = (float) dy2 / (float) dx2;
                final float b2 = c.y - k2 * c.x;
                // check if points are collinear
                if (Math.abs(k1 - k2) < 0.00000001f) {
                    final int dot = dotSign(a, b, c, d);
                    if (a.equals(c) && dot < 0 || a.equals(d) && dot > 0) {
                        return new Result.Point(a.x, a.y);
                    }
                    if (b.equals(c) && dot > 0 || b.equals(d) && dot < 0) {
                        return new Result.Point(b.x, b.y);
                    }

                    return Result.Segment;
                } else {
                    x = (b2 - b1) / (k1 - k2);
                    y = k1 * x + b1;
                }
            } else if (dx1 != 0) {
                final float k1 = (float) dy1 / (float) dx1;
                final float b1 = a.y - k1 * a.x;
                x = c.x;
                y = k1 * x + b1;
            } else if (dx2 != 0) {
                final float k2 = (float) dy2 / (float) dx2;
                final float b2 = c.y - k2 * c.x;
                x = a.x;
                y = k2 * x + b2;
            } else {
                final int dot = dotSign(a, b, c, d);
                if (a.equals(c) && dot < 0 || a.equals(d) && dot > 0) {
                    return new Result.Point(a.x, a.y);
                }
                if (b.equals(c) && dot > 0 || b.equals(d) && dot < 0) {
                    return new Result.Point(b.x, b.y);
                }

                return Result.Segment;
            }

            return new Result.Point(Math.round(x), Math.round(y));
        }

        return Result.None;
    }

    private static boolean intersect(Point a, Point b, Point c, Point d) {
        final int abc = area2Sign(a, b, c);
        final int abd = area2Sign(a, b, d);
        final int cda = area2Sign(c, d, a);
        final int cdb = area2Sign(c, d, b);

        // check if points intersect
        return abc != abd && cda != cdb ||
               abc == 0 && isPointOnSegment(a, b, c) ||
               abd == 0 && isPointOnSegment(a, b, d) ||
               cda == 0 && isPointOnSegment(c, d, a) ||
               cdb == 0 && isPointOnSegment(c, d, b);
    }

    private static boolean isPointOnSegment(Point a, Point b, Point p) {
        return sign(p.x - a.x) * sign(p.x - b.x) <= 0 && sign(p.y - a.y) * sign(p.y - b.y) <= 0;
    }

    private static int sign(long val) {
        return val < 0 ? -1 : (val == 0 ? 0 : 1);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final Point a = readPoint(scanner);
            final Point b = readPoint(scanner);
            final Point c = readPoint(scanner);
            final Point d = readPoint(scanner);
            System.out.println(solve(a, b, c, d));
        }
    }

    private static Point readPoint(Scanner scanner) {
        final int x = scanner.nextInt();
        final int y = scanner.nextInt();
        return new Point(x, y);
    }

    public interface Result {
        final class Point implements Result {
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
                return String.format("The intersection point is (%d, %d).", x, y);
            }
        }

        Result Segment = new Result() {
            @Override
            public String toString() {
                return "A common segment of non-zero length.";
            }
        };

        Result None = new Result() {
            @Override
            public String toString() {
                return "No common points.";
            }
        };
    }

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
}
