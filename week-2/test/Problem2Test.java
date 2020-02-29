import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Problem2Test {
    @Test
    public void test1() {
        final Problem2.Point[] polygon = {
                new Problem2.Point(-10, 2),
                new Problem2.Point(-7, 2),
                new Problem2.Point(-4, 3),
                new Problem2.Point(-6, 5),
                new Problem2.Point(-8, 7),
                new Problem2.Point(-2, 8),
                new Problem2.Point(2, 3),
                new Problem2.Point(4, 5),
                new Problem2.Point(6, 1),
                new Problem2.Point(7, -5),
                new Problem2.Point(2, -7),
                new Problem2.Point(-4, -4),
                new Problem2.Point(-8, -6),
                new Problem2.Point(-7, -1),
                new Problem2.Point(2, 6),
        };
        final List<Problem2.Point> hull = Arrays.asList(
                new Problem2.Point(-10, 2),
                new Problem2.Point(-8, -6),
                new Problem2.Point(2, -7),
                new Problem2.Point(7, -5),
                new Problem2.Point(6, 1),
                new Problem2.Point(4, 5),
                new Problem2.Point(-2, 8),
                new Problem2.Point(-8, 7)
        );
        assertArrayEquals(hull.toArray(), Problem2.solve(polygon).toArray());

    }

    @Test
    public void test2() {
        final Problem2.Point[] polygon = {
                new Problem2.Point(-3, 11),
                new Problem2.Point(-7, 10),
                new Problem2.Point(-9, 7),
                new Problem2.Point(-10, 0),
                new Problem2.Point(-11, -12),
                new Problem2.Point(4, -6),
                new Problem2.Point(6, -5),
                new Problem2.Point(7, 4),
                new Problem2.Point(6, 7),
                new Problem2.Point(4, 9),
        };
        final List<Problem2.Point> hull = Arrays.asList(
                new Problem2.Point(-11, -12),
                new Problem2.Point(4, -6),
                new Problem2.Point(6, -5),
                new Problem2.Point(7, 4),
                new Problem2.Point(6, 7),
                new Problem2.Point(4, 9),
                new Problem2.Point(-3, 11),
                new Problem2.Point(-7, 10),
                new Problem2.Point(-9, 7),
                new Problem2.Point(-10, 0)
        );
        assertArrayEquals(hull.toArray(), Problem2.solve(polygon).toArray());
    }

    @Test
    public void test3() {
        final Problem2.Point[] polygon = {
                new Problem2.Point(0, 0),
                new Problem2.Point(1, 0),
                new Problem2.Point(2, 0),
                new Problem2.Point(2, 1),
                new Problem2.Point(2, 2),
                new Problem2.Point(1, 2),
                new Problem2.Point(0, 2),
                new Problem2.Point(0, 1),
                new Problem2.Point(1, 1),
        };
        final List<Problem2.Point> hull = Arrays.asList(
                new Problem2.Point(0, 0),
                new Problem2.Point(2, 0),
                new Problem2.Point(2, 2),
                new Problem2.Point(0, 2)
        );
        assertArrayEquals(hull.toArray(), Problem2.solve(polygon).toArray());
    }
}