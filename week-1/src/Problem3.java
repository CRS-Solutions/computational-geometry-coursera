import java.util.Scanner;

public class Problem3 {
    private static final String INSIDE = "INSIDE";
    private static final String OUTSIDE = "OUTSIDE";
    private static final String BORDER = "BORDER";

    private static int area2(int ax, int ay, int bx, int by, int cx, int cy) {
        int vx = bx - ax;
        int vy = by - ay;
        int ux = cx - ax;
        int uy = cy - ay;
        return vx * uy - ux * vy;
    }

    public static String intersectRay(int ax, int ay, int bx, int by, int px, int py) {
        int topX, topY, bottomX, bottomY;
        if (ay < by) {
            topX = bx;
            topY = by;
            bottomX = ax;
            bottomY = ay;
        } else {
            topX = ax;
            topY = ay;
            bottomX = bx;
            bottomY = by;
        }
        if (py >= bottomY && py <= topY) {
            int area = area2(bottomX, bottomY, topX, topY, px, py);
            if (area > 0) {
                return OUTSIDE;
            } else if (area < 0) {
                return INSIDE;
            } else {
                return BORDER;
            }
        }

        return OUTSIDE;
    }

    public static String solve(int[] polygon, int px, int py) {
        int intersections = 0;
        int prevX = polygon[polygon.length - 2];
        int prevY = polygon[polygon.length - 1];
        int i = 0;
        while (i < polygon.length) {
            int currX = polygon[i++];
            int currY = polygon[i++];

            String state = intersectRay(prevX, prevY, currX, currY, px, py);
            if (state == BORDER) {
                return state;
            } else if (state == INSIDE) {
                intersections += 1;
            }

            prevX = currX;
            prevY = currY;
        }

        return intersections % 2 == 1 ? INSIDE : OUTSIDE;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int[] polygon = new int[2 * n];
            for (int i = 0; i < polygon.length; ++i) {
                polygon[i] = scanner.nextInt();
            }

            int m = scanner.nextInt();
            while (m > 0) {
                int px = scanner.nextInt();
                int py = scanner.nextInt();
                System.out.println(solve(polygon, px, py));
                --m;
            }
        }
    }
}
