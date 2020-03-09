import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Problem4Test {
    @Test
    public void testStatus1() {
        Problem4.SegmentStatus status = new Problem4.ListSegmentStatus();
        Problem4.Segment s1 = parseSegment(1, "-8 89 -3 18");
        Problem4.Segment s2 = parseSegment(2, "-68 75 28 -5");
        Problem4.Segment s3 = parseSegment(3, "-8 69 -30 -49");
        Problem4.Segment s4 = parseSegment(4, "-37 62 10 0");

        addSegment(status, s1);
        assertStatus(status, s1);

        addSegment(status, s2);
        assertStatus(status, s2, s1);

        addSegment(status, s3);
        assertStatus(status, s2, s3, s1);

        addSegment(status, s4);
        assertStatus(status, s2, s4, s3, s1);
    }

    @Test
    public void testStatus2() {
        Problem4.SegmentStatus status = new Problem4.ListSegmentStatus();
        Problem4.Segment s1 = parseSegment(1, "1 0 3 4 ");
        Problem4.Segment s2 = parseSegment(2, "5 4 3 0");
        Problem4.Segment s3 = parseSegment(3, "1 2 6 2");

        addSegment(status, s1);
        assertStatus(status, s1);

        addSegment(status, s2);
        assertStatus(status, s1, s2);

        addSegment(status, s3);
        assertStatus(status, s3, s1, s2);
    }

    @Test
    public void testStatus3() {
        Problem4.SegmentStatus status = new Problem4.ListSegmentStatus();
        Problem4.Segment s1 = parseSegment(1, "-83 34 3 98");
        Problem4.Segment s2 = parseSegment(2, "74 -9 -14 87");
        Problem4.Segment s3 = parseSegment(3, "-65 9 -89 86");
        Problem4.Segment s4 = parseSegment(4, "91 25 -98 83");
        Problem4.Segment s5 = parseSegment(5, "-85 -57 -16 17");
        Problem4.Segment s6 = parseSegment(6, "-7 -66 98 14");
        Problem4.Segment s7 = parseSegment(7, "-57 -12 38 -4");
        Problem4.Segment s8 = parseSegment(8, "93 -90 -95 -39");

        addSegment(status, s1);
        assertStatus(status, s1);

        addSegment(status, s2);
        assertStatus(status, s2, s1);

        update(status, s1, s2, -13.10023f, 86.01843f);
        assertStatus(status, s1, s2);

        addSegment(status, s3);
        assertStatus(status, s3, s1, s2);

        addSegment(status, s4);
        assertStatus(status, s4, s3, s1, s2);

        update(status, s3, s4, -87.01414f, 79.62869f);
        assertStatus(status, s3, s4, s1, s2);

        float[] cord = new float[2];
        Problem4.getIntersection(s3, s4, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, cord);
        System.out.println(Arrays.toString(cord));
    }

    private void update(Problem4.SegmentStatus status, Problem4.Segment s1, Problem4.Segment s2, float x, float y) {
        status.remove(s1);
        status.remove(s2);
        status.add(y, s1);
        status.add(y, s2);
    }

    private void assertStatus(Problem4.SegmentStatus status, Problem4.Segment... segments) {
        List<Problem4.Segment> actual = new ArrayList<>();
        for (Problem4.Segment s : status) {
            actual.add(s);
        }
        List<Problem4.Segment> expected = Arrays.asList(segments);
        assertEquals(expected, actual);
    }

    private void addSegment(Problem4.SegmentStatus status, Problem4.Segment segment) {
        status.add(segment.start.y, segment);
    }

    @Test
    public void test1() {
        List<Problem4.Segment> segments = parseSegments("-7 -3 2 6 -7 2 9 -2 3 -3 -3 6");
        List<Problem4.Segment> expected = parseSegments("-7 2 9 -2 -7 -3 2 6 -3 6 3 -3 -7 -3 2 6 -3 6 3 -3 -7 2 9 -2");
        List<Problem4.Segment> actual = Problem4.solve(segments);
        assertEqualsIgnoreOrder(expected, actual);
    }

    @Test
    public void test2() {
        List<Problem4.Segment> segments = parseSegments("29 48 30 13 49 57 -68 -24 17 -30 55 16 19 -61 91 -2 44 -22 21 73 -68 -38 -77 90 -74 84 55 -32 26 72 -91 64 -21 -41 -3 75");
        List<Problem4.Segment> expected = parseSegments("-77 90 -68 -38 -91 64 26 72 -74 84 55 -32 -91 64 26 72 -68 -24 49 57 -21 -41 -3 75 -74 84 55 -32 -21 -41 -3 75 -91 64 26 72 -21 -41 -3 75 -74 84 55 -32 -68 -24 49 57 21 73 44 -22 -91 64 26 72 21 73 44 -22 -68 -24 49 57 29 48 30 13 -68 -24 49 57 29 48 30 13 21 73 44 -22 -74 84 55 -32 17 -30 55 16 21 73 44 -22 17 -30 55 16 -74 84 55 -32 19 -61 91 -2");
        List<Problem4.Segment> actual = Problem4.solve(segments);
        assertEqualsIgnoreOrder(expected, actual);
    }

    @Test
    public void test3() {
        List<Problem4.Segment> segments = parseSegments("-87 83 69 70 -31 -17 -8 72 35 -4 -37 37 -80 -36 96 43 16 -95 42 90 20 -76 48 -55 99 0 37 -28 -33 -85 4 -45 47 2 16 -9");
        List<Problem4.Segment> expected = parseSegments("-87 83 69 70 16 -95 42 90 -37 37 35 -4 -31 -17 -8 72 16 -95 42 90 -80 -36 96 43 -37 37 35 -4 -80 -36 96 43 -37 37 35 -4 16 -95 42 90 -37 37 35 -4 16 -9 47 2 16 -95 42 90 16 -9 47 2 -31 -17 -8 72 -80 -36 96 43");
        List<Problem4.Segment> actual = Problem4.solve(segments);
        assertEqualsIgnoreOrder(expected, actual);
    }

    @Test
    public void test4() {
        List<Problem4.Segment> segments = parseSegments("84 -59 39 -38 35 19 -32 -11 -34 -82 -100 28 29 0 42 -68 75 -90 97 48 -21 -27 84 -9 -15 -24 -34 87 15 -18 8 -42 -36 -53 -10 -38 31 39 -11 -6 71 -71 66 57 70 83 -71 23");
        List<Problem4.Segment> expected = parseSegments("-34 87 -15 -24 -71 23 70 83 -11 -6 31 39 -32 -11 35 19 -34 87 -15 -24 -32 -11 35 19 66 57 71 -71 -21 -27 84 -9 29 0 42 -68 -21 -27 84 -9 8 -42 15 -18 -21 -27 84 -9 39 -38 84 -59 66 57 71 -71 39 -38 84 -59 75 -90 97 48");
        List<Problem4.Segment> actual = Problem4.solve(segments);
        assertEqualsIgnoreOrder(expected, actual);
    }

    @Test
    public void test5() {
        List<Problem4.Segment> segments = parseSegments("-68 75 28 -5 48 -88 -4 -9 -63 39 32 60 -8 89 -3 18 93 50 -48 42 -83 14 -46 32 -8 69 -30 -49 -50 -90 -76 -36 -37 62 10 0");
        List<Problem4.Segment> expected = parseSegments("-8 89 -3 18 32 60 -63 39 -8 69 -30 -49 32 60 -63 39 -37 62 10 0 32 60 -63 39 -68 75 28 -5 32 60 -63 39 -8 89 -3 18 93 50 -48 42 -8 69 -30 -49 93 50 -48 42 -37 62 10 0 93 50 -48 42 -68 75 28 -5 93 50 -48 42 -37 62 10 0 -8 69 -30 -49 -68 75 28 -5 -8 69 -30 -49 -68 75 28 -5 -37 62 10 0 -68 75 28 -5 -8 89 -3 18");
        List<Problem4.Segment> actual = Problem4.solve(segments);
        assertEqualsIgnoreOrder(expected, actual);
    }

    @Test
    public void test6() {
        List<Problem4.Segment> segments = parseSegments("1 0 3 4 1 2 6 2 5 4 3 0");
        List<Problem4.Segment> expected = parseSegments("1 2 6 2 3 4 1 0 1 2 6 2 5 4 3 0");
        List<Problem4.Segment> actual = Problem4.solve(segments);
        assertEqualsIgnoreOrder(expected, actual);
    }

    @Test
    public void test7() {
        List<Problem4.Segment> segments = parseSegments("74 -9 -14 87\n" +
                "93 -90 -95 -39\n" +
                "-16 17 -85 -57\n" +
                "-57 -12 38 -4\n" +
                "3 98 -83 34\n" +
                "98 14 -7 -66\n" +
                "-98 83 91 25\n" +
                "-65 9 -89 86");
        List<Problem4.Segment> expected = parseSegments("-14 87 74 -9 3 98 -83 34 -98 83 91 25 -89 86 -65 9 -98 83 91 25 3 98 -83 34 -98 83 91 25 -14 87 74 -9 -89 86 -65 9 3 98 -83 34 -14 87 74 -9 98 14 -7 -66 -16 17 -85 -57 38 -4 -57 -12 -95 -39 93 -90 -16 17 -85 -57 -95 -39 93 -90 98 14 -7 -66");
        List<Problem4.Segment> actual = Problem4.solve(segments);
        assertEqualsIgnoreOrder(expected, actual);
    }

    @Test
    public void test8() {
        List<Problem4.Segment> segments = parseSegments("2 0 4 4\n" +
                "4 0 2 4\n" +
                "4 1 2 1\n" +
                "4 3 2 3");
        List<Problem4.Segment> expected = parseSegments("2 3 4 3 2 4 4 0 2 3 4 3 4 4 2 0 2 4 4 0 4 4 2 0 2 1 4 1 4 4 2 0 2 1 4 1 2 4 4 0");
        List<Problem4.Segment> actual = Problem4.solve(segments);
        assertEqualsIgnoreOrder(expected, actual);
    }

    private void assertEqualsIgnoreOrder(List<Problem4.Segment> expected, List<Problem4.Segment> actual) {
        String message = String.format("\nExpected :%s\nActual   :%s", join(expected), join(actual));
        assertEquals(message, expected.size(), actual.size());
        List<Problem4.Segment> temp = new ArrayList<>(actual);
        for (Problem4.Segment t : expected) {
            temp.remove(t);
        }
        assertTrue(message, temp.isEmpty());
    }

    private String join(List<Problem4.Segment> list) {
        StringBuilder result = new StringBuilder();
        result.append('[');
        int index = 0;
        for (Problem4.Segment s : list) {
            if (index > 0) {
                result.append(" ");
            }
            result.append(String.format("%d %d %d %d", s.start.x, s.start.y, s.end.x, s.end.y));
            ++index;
        }
        result.append(']');
        return result.toString();
    }

    private List<Problem4.Segment> parseSegments(String s) {
        String[] tokens = s.split("\\s+");
        if (tokens.length % 4 != 0) {
            throw new IllegalArgumentException("Illegal segments: " + s);
        }
        int n = tokens.length / 4;
        List<Problem4.Segment> segments = new ArrayList<>(n);
        for (int i = 0; i < tokens.length; ) {
            int x1 = Integer.parseInt(tokens[i++]);
            int y1 = Integer.parseInt(tokens[i++]);
            int x2 = Integer.parseInt(tokens[i++]);
            int y2 = Integer.parseInt(tokens[i++]);
            segments.add(new Problem4.Segment(new Problem4.Point(x1, y1), new Problem4.Point(x2, y2)));
        }
        return segments;
    }

    private Problem4.Segment parseSegment(int num, String s) {
        String[] tokens = s.split(" ");
        if (tokens.length != 4) {
            throw new IllegalArgumentException("Illegal segment: " + s);
        }
        int i = 0;
        int x1 = Integer.parseInt(tokens[i++]);
        int y1 = Integer.parseInt(tokens[i++]);
        int x2 = Integer.parseInt(tokens[i++]);
        int y2 = Integer.parseInt(tokens[i++]);
        final Problem4.Segment segment = new Problem4.Segment(new Problem4.Point(x1, y1), new Problem4.Point(x2, y2));
        segment.number = num;
        return segment;
    }
}