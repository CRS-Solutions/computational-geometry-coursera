import org.junit.Test;

import static org.junit.Assert.*;

public class Problem2Test {
    @Test
    public void test1() {
        int ax = 0, ay = 0, bx = 5, by = 5, cx = 0, cy = 6;
        assertEquals("BORDER", Problem2.solve(ax, ay, bx, by, cx, cy, 3, 3));
        assertEquals("OUTSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 6, 6));
        assertEquals("INSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 2, 3));
    }

    @Test
    public void test2() {
        int ax = 1, ay = 0, bx = 0, by = 3, cx = 3, cy = 3;
        assertEquals("OUTSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 0, 0));
        assertEquals("OUTSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 0, 1));
        assertEquals("OUTSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 0, 2));
        assertEquals("BORDER", Problem2.solve(ax, ay, bx, by, cx, cy, 0, 3));
        assertEquals("BORDER", Problem2.solve(ax, ay, bx, by, cx, cy, 1, 0));
        assertEquals("INSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 1, 1));
        assertEquals("INSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 1, 2));
        assertEquals("BORDER", Problem2.solve(ax, ay, bx, by, cx, cy, 1, 3));
        assertEquals("OUTSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 2, 0));
        assertEquals("OUTSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 2, 1));
        assertEquals("INSIDE", Problem2.solve(ax, ay, bx, by, cx, cy, 2, 2));
        assertEquals("BORDER", Problem2.solve(ax, ay, bx, by, cx, cy, 2, 3));
    }
}