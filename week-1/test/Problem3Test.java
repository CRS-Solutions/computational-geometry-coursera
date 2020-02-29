import org.junit.Test;

import static org.junit.Assert.*;

public class Problem3Test {

    @Test
    public void test1() {
        int[] polygon = {-3, -1, 3, -1, 3, 5, 0, 2, -3, 4};
        assertEquals("BORDER", Problem3.solve(polygon, 1, 3));
        assertEquals("OUTSIDE", Problem3.solve(polygon, -1, 3));
        assertEquals("INSIDE", Problem3.solve(polygon, 0, 0));
        assertEquals("INSIDE", Problem3.solve(polygon, -2, 3));
    }
}