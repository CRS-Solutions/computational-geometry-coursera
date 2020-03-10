import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Problem3Test {
    @Test
    public void test1() {
        List<Problem3.Point> points = parsePoints("0 8 -5 6 -2 0 1 -8 5 -1 11 7");
        List<Problem3.Point> expected = parsePoints("-5 6 11 7\n" +
                "-2 0 11 7\n" +
                "5 -1 -2 0");
        List<Problem3.Point> actual = Problem3.solve(points);
        assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        List<Problem3.Point> points = parsePoints("-17 12 -6 5 0 -7 -30 -13 3 -14 21 -12 34 -10 26 11");
        List<Problem3.Point> expected = parsePoints("-6 5 26 11\n" +
                "0 -7 26 11\n" +
                "34 -10 0 -7\n" +
                "21 -12 0 -7\n" +
                "-30 -13 21 -12");
        List<Problem3.Point> actual = Problem3.solve(points);
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        List<Problem3.Point> points = parsePoints("0 0 2 7 5 8 2 10");
        List<Problem3.Point> expected = parsePoints("2 7 2 10");
        List<Problem3.Point> actual = Problem3.solve(points);
        assertEquals(expected, actual);
    }

    @Test
    public void test4() {
        List<Problem3.Point> points = parsePoints("0 0 1 3 5 4 2 5 6 6 2 8");
        List<Problem3.Point> expected = parsePoints("2 5 2 8\n" +
                "1 3 2 5\n" +
                "1 3 2 8");
        List<Problem3.Point> actual = Problem3.solve(points);
        assertEquals(expected, actual);
    }

    private List<Problem3.Point> parsePoints(String s) {
        String[] tokens = s.split("\\s+");
        if (tokens.length % 2 != 0) {
            throw new IllegalArgumentException("Illegal points: " + s);
        }
        int n = tokens.length / 2;
        List<Problem3.Point> points = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            int x = Integer.parseInt(tokens[2 * i]);
            int y = Integer.parseInt(tokens[2 * i + 1]);
            points.add(new Problem3.Point(i, x, y));
        }
        return points;
    }
}