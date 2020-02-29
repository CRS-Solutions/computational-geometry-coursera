import java.util.Scanner;

public class Problem1 {
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private static final String ON_SEGMENT = "ON_SEGMENT";
    private static final String ON_LINE = "ON_LINE";

    private static int area2(int ax, int ay, int bx, int by, int cx, int cy) {
        int vx = bx - ax;
        int vy = by - ay;
        int ux = cx - ax;
        int uy = cy - ay;
        return vx * uy - ux * vy;
    }

    private static boolean between(int ax, int ay, int bx, int by, int cx, int cy) {
        return cx >= Math.min(ax, bx) && cx <= Math.max(ax, bx) && cy >= Math.min(ay, by) && cy <= Math.max(ay, by);
    }

    public static String solve(int ax, int ay, int bx, int by, int px, int py) {
        int area2 = area2(ax, ay, bx, by, px, py);
        if (area2 > 0) {
            return LEFT;
        } else if (area2 < 0) {
            return RIGHT;
        } else {
            return between(ax, ay, bx, by, px, py) ? ON_SEGMENT : ON_LINE;
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int ax = scanner.nextInt();
            int ay = scanner.nextInt();
            int bx = scanner.nextInt();
            int by = scanner.nextInt();
            int n = scanner.nextInt();
            while (n > 0) {
                int px = scanner.nextInt();
                int py = scanner.nextInt();
                System.out.println(solve(ax, ay, bx, by, px, py));
                --n;
            }
        }
    }
}
