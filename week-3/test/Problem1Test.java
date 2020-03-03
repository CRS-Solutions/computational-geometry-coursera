import org.junit.Test;

import static org.junit.Assert.*;

public class Problem1Test {
    @Test
    public void test1() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(5,5);
        final Problem1.Point c = new Problem1.Point(0,6);
        final Problem1.Point d = new Problem1.Point(6,0);
        assertPoints(new Problem1.Result.Point(3, 3), a, b, c, d);
    }

    @Test
    public void test2() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(5,5);
        final Problem1.Point c = new Problem1.Point(3,3);
        final Problem1.Point d = new Problem1.Point(6,0);
        assertPoints(new Problem1.Result.Point(3, 3), a, b, c, d);
    }

    @Test
    public void test3() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(5,5);
        final Problem1.Point c = new Problem1.Point(0,6);
        final Problem1.Point d = new Problem1.Point(3,3);
        assertPoints(new Problem1.Result.Point(3, 3), a, b, c, d);
    }

    @Test
    public void test4() {
        final Problem1.Point a = new Problem1.Point(1,6);
        final Problem1.Point b = new Problem1.Point(1,1);
        final Problem1.Point c = new Problem1.Point(-1,5);
        final Problem1.Point d = new Problem1.Point(3,3);
        assertPoints(new Problem1.Result.Point(1, 4), a, b, c, d);
    }

    @Test
    public void test5() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(1,3);
        final Problem1.Point c = new Problem1.Point(1,3);
        final Problem1.Point d = new Problem1.Point(3,9);
        assertPoints(new Problem1.Result.Point(1, 3), a, b, c, d);
    }

    @Test
    public void test6() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(1,3);
        final Problem1.Point c = new Problem1.Point(0,0);
        final Problem1.Point d = new Problem1.Point(3,9);
        assertPoints(Problem1.Result.Segment, a, b, c, d);
    }

    @Test
    public void test7() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(3,9);
        final Problem1.Point c = new Problem1.Point(0,0);
        final Problem1.Point d = new Problem1.Point(3,9);
        assertPoints(Problem1.Result.Segment, a, b, c, d);
    }

    @Test
    public void test8() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(1,3);
        final Problem1.Point c = new Problem1.Point(2,6);
        final Problem1.Point d = new Problem1.Point(3,9);
        assertPoints(Problem1.Result.None, a, b, c, d);
    }

    @Test
    public void test9() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(1,3);
        final Problem1.Point c = new Problem1.Point(2,6);
        final Problem1.Point d = new Problem1.Point(1,8);
        assertPoints(Problem1.Result.None, a, b, c, d);
    }

    @Test
    public void test10() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(0,3);
        final Problem1.Point c = new Problem1.Point(0,3);
        final Problem1.Point d = new Problem1.Point(0,8);
        assertPoints(new Problem1.Result.Point(0, 3), a, b, c, d);
    }

    @Test
    public void test11() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(0,3);
        final Problem1.Point c = new Problem1.Point(0,2);
        final Problem1.Point d = new Problem1.Point(0,8);
        assertPoints(Problem1.Result.Segment, a, b, c, d);
    }

    @Test
    public void test12() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(0,3);
        final Problem1.Point c = new Problem1.Point(0,4);
        final Problem1.Point d = new Problem1.Point(0,8);
        assertPoints(Problem1.Result.None, a, b, c, d);
    }

    @Test
    public void test13() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(0,3);
        final Problem1.Point c = new Problem1.Point(0,0);
        final Problem1.Point d = new Problem1.Point(0,3);
        assertPoints(Problem1.Result.Segment, a, b, c, d);
    }

    @Test
    public void test14() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(0,3);
        final Problem1.Point c = new Problem1.Point(1,0);
        final Problem1.Point d = new Problem1.Point(1,3);
        assertPoints(Problem1.Result.None, a, b, c, d);
    }


    @Test
    public void test15() {
        final Problem1.Point a = new Problem1.Point(1,0);
        final Problem1.Point b = new Problem1.Point(1,3);
        final Problem1.Point c = new Problem1.Point(1,0);
        final Problem1.Point d = new Problem1.Point(2,8);
        assertPoints(new Problem1.Result.Point(1, 0), a, b, c, d);
    }

    @Test
    public void test16() {
        final Problem1.Point a = new Problem1.Point(1,0);
        final Problem1.Point b = new Problem1.Point(1,3);
        final Problem1.Point c = new Problem1.Point(2,3);
        final Problem1.Point d = new Problem1.Point(3,3);
        assertPoints(Problem1.Result.None, a, b, c, d);
    }

    @Test
    public void test17() {
        final Problem1.Point a = new Problem1.Point(1,0);
        final Problem1.Point b = new Problem1.Point(1,3);
        final Problem1.Point c = new Problem1.Point(2,3);
        final Problem1.Point d = new Problem1.Point(3,3);
        assertPoints(Problem1.Result.None, a, b, c, d);
    }

    @Test
    public void test18() {
        final Problem1.Point a = new Problem1.Point(-7906,130);
        final Problem1.Point b = new Problem1.Point(-6304,-3048);
        final Problem1.Point c = new Problem1.Point(6308,-8491);
        final Problem1.Point d = new Problem1.Point(-7906,130);
        assertPoints(new Problem1.Result.Point(-7906, 130), a, b, c, d);
    }

    @Test
    public void test20() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(5,5);
        final Problem1.Point c = new Problem1.Point(5,0);
        final Problem1.Point d = new Problem1.Point(3,2);
        assertPoints(Problem1.Result.None, a, b, c, d);
    }

    @Test
    public void test30() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(10,0);
        final Problem1.Point c = new Problem1.Point(1,0);
        final Problem1.Point d = new Problem1.Point(2,0);
        assertPoints(Problem1.Result.Segment, a, b, c, d);
    }

    @Test
    public void test40() {
        final Problem1.Point a = new Problem1.Point(0,0);
        final Problem1.Point b = new Problem1.Point(10,0);
        final Problem1.Point c = new Problem1.Point(5,0);
        final Problem1.Point d = new Problem1.Point(15,0);
        assertPoints(Problem1.Result.Segment, a, b, c, d);
    }

    private void assertPoints(Problem1.Result expected, Problem1.Point a, Problem1.Point b, Problem1.Point c, Problem1.Point d) {
        assertEquals(expected, Problem1.solve(a, b, c, d));
        assertEquals(expected, Problem1.solve(a, b, d, c));
        assertEquals(expected, Problem1.solve(b, a, c, d));
        assertEquals(expected, Problem1.solve(b, a, d, c));
        assertEquals(expected, Problem1.solve(c, d, a, b));
        assertEquals(expected, Problem1.solve(c, d, b, a));
        assertEquals(expected, Problem1.solve(d, c, a, b));
        assertEquals(expected, Problem1.solve(d, c, b, a));
    }
}