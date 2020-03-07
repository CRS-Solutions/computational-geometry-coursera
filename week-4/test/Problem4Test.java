import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Problem4Test {
    @Test
    public void test1() {
        assertEquals(14, Problem4.solve(6));
    }

    @Test
    public void test2() {
        assertEquals(1, Problem4.solve(3));
    }
}