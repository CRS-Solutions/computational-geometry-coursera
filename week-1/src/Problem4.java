import java.util.Scanner;

public final class Problem4 {
    private static final String INSIDE = "INSIDE";
    private static final String OUTSIDE = "OUTSIDE";
    private static final String BORDER = "BORDER";

    private final Point z;
    private final Wedge[] wedges;

    public Problem4(int[] polygon) {
        z = calculateInteriorPoint(polygon);
        wedges = calculateWedges(polygon, z);
    }

    public String solve(int px, int py) {
        float dx = z.x - px;
        float dy = z.y - py;
        float angle = MathUtils.polarAngle(dx, dy);
        Wedge wedge = find(angle);
        int area2 = MathUtils.area2(wedge.ax, wedge.ay, wedge.bx, wedge.by, px, py);
        if (area2 > 0) {
            return INSIDE;
        } else if (area2 < 0) {
            return OUTSIDE;
        } else {
            return BORDER;
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int[] polygon = new int[2 * n];
            for (int i = 0; i < polygon.length; ++i) {
                polygon[i] = scanner.nextInt();
            }

            Problem4 problem = new Problem4(polygon);

            int m = scanner.nextInt();
            while (m > 0) {
                int px = scanner.nextInt();
                int py = scanner.nextInt();
                System.out.println(problem.solve(px, py));
                --m;
            }
        }
    }

    //region Helpers

    private Wedge find(float angle) {
        int low = 0;
        int high = wedges.length - 1;

        while (low <= high) {
            int mid = low + high >>> 1;
            Wedge wedge = wedges[mid];
            float a = wedge.angle;
            if (a < angle) {
                low = mid + 1;
            } else {
                if (a <= angle) {
                    return wedge;
                }
                high = mid - 1;
            }
        }

        return wedges[low % wedges.length];
    }

    private static Point calculateInteriorPoint(int[] polygon) {
        // a <- point[0]
        int ax = polygon[0];
        int ay = polygon[1];
        // b <- point[1]
        int bx = polygon[2];
        int by = polygon[3];
        // c <- point[2]
        int c = 4;
        // skip all collinear points
        while (c < polygon.length && MathUtils.area2(ax, ay, bx, by, polygon[c], polygon[c + 1]) == 0) {
            c += 2;
        }
        if (c == polygon.length) {
            throw new IllegalArgumentException("Polygon should be simple and convex");
        }

        int cx = polygon[c];
        int cy = polygon[c + 1];

        float mul = 1f / 3f;
        float zx = mul * (ax + bx + cx);
        float zy = mul * (ay + by + cy);
        return new Point(zx, zy);
    }

    private static Wedge[] calculateWedges(int[] polygon, Point z) {
        int prevX = polygon[polygon.length - 2];
        int prevY = polygon[polygon.length - 1];
        int i = 0, j = 0;
        Wedge[] wedges = new Wedge[polygon.length / 2];
        while (i < polygon.length) {
            int currX = polygon[i++];
            int currY = polygon[i++];
            float dx = z.x - currX;
            float dy = z.y - currY;
            float angle = MathUtils.polarAngle(dx, dy);
            wedges[j++] = new Wedge(angle, prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }
        return wedges;
    }

    //endregion
}

final class Wedge {
    public final float angle;
    public final int ax;
    public final int ay;
    public final int bx;
    public final int by;

    Wedge(float angle, int ax, int ay, int bx, int by) {
        this.angle = angle;
        this.ax = ax;
        this.ay = ay;
        this.bx = bx;
        this.by = by;
    }
}

final class Point {
    public final float x;
    public final float y;

    Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

final class MathUtils {
    private static final float PI = 3.141592653589793f;
    private static final float TOLERANCE = 0.000001f;

    public static float polarAngle(float dx, float dy) {
        if (Math.abs(dx) < TOLERANCE) {
            return dy > 0 ? 0.5f * PI : 1.5f * PI;
        } else if (Math.abs(dy) < TOLERANCE) {
            return dx > 0 ? 0 : PI;
        }
        float alpha = (float) Math.atan2(dy, dx);
        return alpha > 0.0f ? alpha : 2 * PI + alpha;
    }

    public static int area2(int ax, int ay, int bx, int by, int cx, int cy) {
        int vx = bx - ax;
        int vy = by - ay;
        int ux = cx - ax;
        int uy = cy - ay;
        return vx * uy - ux * vy;
    }
}
