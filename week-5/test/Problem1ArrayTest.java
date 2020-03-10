import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Problem1ArrayTest {
    @Test
    public void test1() {
        final int[] points = parsePoints("-7 3 8 12 -3 -10");
        final int[] query = parsePoints("-12\n" +
                "13\n" +
                "-5\n" +
                "-3\n" +
                "5\n" +
                "-2\n" +
                "0");
        final int[] expected = parsePoints("-10\n" +
                "12\n" +
                "-3\n" +
                "-3\n" +
                "3\n" +
                "-3\n" +
                "-3");
        final int[] actual = Problem1Array.solve(points, query);
        assertArrayEquals(expected, actual);
    }

    private int[] parsePoints(String s) {
        String[] tokens = s.split("\\s+");
        int n = tokens.length;
        int[] points = new int[n];
        for (int i = 0; i < n; ++i) {
            points[i] = Integer.parseInt(tokens[i]);
        }
        return points;
    }
}