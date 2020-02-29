import org.junit.Test;

import static org.junit.Assert.*;

public class Problem1Test {
    @Test
    public void test1() {
        Problem1.Point[] polygon = {new Problem1.Point(-3,-1), new Problem1.Point(3,-1), new Problem1.Point(3,5), new Problem1.Point(0,2), new Problem1.Point(-3,4)};
        assertEquals("NOT_CONVEX", Problem1.solve(polygon));
    }

    @Test
    public void test2() {
        Problem1.Point[] polygon = {new Problem1.Point(-2,-3), new Problem1.Point(1,-4), new Problem1.Point(3,-2), new Problem1.Point(2,1), new Problem1.Point(-2,1)};
        assertEquals("CONVEX", Problem1.solve(polygon));
    }
}