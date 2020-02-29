import java.util.Scanner;

public class Problem2 {
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

    public static String solve(int ax, int ay, int bx, int by, int cx, int cy, int px, int py) {
        int intersections = 0;
        // a-b
        String state = intersectRay(ax, ay, bx, by, px, py);
        if (state == BORDER) {
            return state;
        } else if (state == INSIDE) {
            intersections += 1;
        }
        // b-c
        state = intersectRay(bx, by, cx, cy, px, py);
        if (state == BORDER) {
            return state;
        } else if (state == INSIDE) {
            intersections += 1;
        }
        // c-a
        state = intersectRay(cx, cy, ax, ay, px, py);
        if (state == BORDER) {
            return state;
        } else if (state == INSIDE) {
            intersections += 1;
        }

        return intersections % 2 == 1 ? INSIDE : OUTSIDE;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int ax = scanner.nextInt();
            int ay = scanner.nextInt();
            int bx = scanner.nextInt();
            int by = scanner.nextInt();
            int cx = scanner.nextInt();
            int cy = scanner.nextInt();
            int n = scanner.nextInt();
            while (n > 0) {
                int px = scanner.nextInt();
                int py = scanner.nextInt();
                System.out.println(solve(ax, ay, bx, by, cx, cy, px, py));
                --n;
            }
        }
    }
}
