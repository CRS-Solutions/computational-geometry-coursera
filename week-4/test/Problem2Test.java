import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Problem2Test {
    @Test
    public void test1() {
        final List<Problem2.Point> points = parsePoints("-7 1 -5 -2 1 1 4 -1 8 5 3 3 1 6 -2 0 -5 2 -5 6");
        final List<Problem2.Point> expected = parsePoints("1 1 4 -1 8 5\n" +
                "1 1 8 5 3 3\n" +
                "1 1 3 3 1 6\n" +
                "1 1 1 6 -2 0\n" +
                "-5 2 -5 6 -7 1\n" +
                "-5 2 -7 1 -5 -2\n" +
                "-5 -2 1 1 -2 0\n" +
                "-5 -2 -2 0 -5 2");
        final List<Problem2.Point> actual = Problem2.solve(points);
        assertEquals(expected, actual);
    }

    private List<Problem2.Point> parsePoints(String s) {
        String[] tokens = s.split("\\s+");
        if (tokens.length % 2 != 0) {
            throw new IllegalArgumentException("Illegal polygon: " + s);
        }
        int n = tokens.length / 2;
        List<Problem2.Point> points = new ArrayList<>(n);
        for (int i = 0, j = 0; i < n; ++i) {
            int x = Integer.parseInt(tokens[j++]);
            int y = Integer.parseInt(tokens[j++]);
            points.add(new Problem2.Point(x, y));
        }
        return points;
    }
}