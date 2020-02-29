import org.junit.Test;

import static org.junit.Assert.*;

public class Problem1Test {
    @Test
    public void test1() {
        int ax = -4, ay = 4, bx = 2, by = -2;
        assertEquals("ON_SEGMENT", Problem1.solve(ax, ay, bx, by, 0, 0));
        assertEquals("ON_LINE", Problem1.solve(ax, ay, bx, by, 5, -5));
        assertEquals("LEFT", Problem1.solve(ax, ay, bx, by, 1, 1));
        assertEquals("RIGHT", Problem1.solve(ax, ay, bx, by, -1, -1));
    }

    @Test
    public void test2() {
        int ax = -4, ay = 0, bx = 2, by = -10;
        assertEquals("RIGHT", Problem1.solve(ax, ay, bx, by, -8, 4));
        assertEquals("LEFT", Problem1.solve(ax, ay, bx, by, 2, -7));
        assertEquals("LEFT", Problem1.solve(ax, ay, bx, by, 9, 3));
        assertEquals("LEFT", Problem1.solve(ax, ay, bx, by, -4, 6));
        assertEquals("RIGHT", Problem1.solve(ax, ay, bx, by, -8, -5));
        assertEquals("LEFT", Problem1.solve(ax, ay, bx, by, 7, 1));
        assertEquals("LEFT", Problem1.solve(ax, ay, bx, by, 2, 4));
        assertEquals("RIGHT", Problem1.solve(ax, ay, bx, by, -3, -9));
    }
}