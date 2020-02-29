import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Problem4Test {
    @Test
    public void test1() {
        int[] polygon = {-2, -3, 1, -4, 3, -2, 2, 1, -2, 1};
        Problem4 problem = new Problem4(polygon);
        assertEquals("BORDER", problem.solve(2, -3));
        assertEquals("OUTSIDE", problem.solve(3, 0));
        assertEquals("INSIDE", problem.solve(0, 0));
        assertEquals("INSIDE", problem.solve(2, -1));
    }

    @Test
    public void test2() {
        int[] polygon = {7, 5, 3, 5, 3, 1, 7, 1};
        Problem4 problem = new Problem4(polygon);
        assertEquals("INSIDE", problem.solve(6, 3));
    }
}