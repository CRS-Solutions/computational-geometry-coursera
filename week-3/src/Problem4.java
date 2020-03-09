import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Problem4 {
    public static List<Segment> solve(List<Segment> segments) {
        EventQueue eventQueue = createEventQueue(segments);
        SegmentStatus status = new ListSegmentStatus();
        List<Segment> result = new ArrayList<>();
        int i = 0;
        for (EventPoint p : eventQueue) {
            if (p.type == EventPoint.Type.Start) {
                p.s1.number = ++i;
            }
        }

        float[] z = new float[2];
        while (eventQueue.size() > 0) {
            final EventPoint event = eventQueue.poll();
            switch (event.type) {
                case Start: {
                    final Segment curr = event.s1;
                    status.add(event.x, curr);

                    Segment prev = status.prev(curr);
                    if (getIntersection(prev, curr, event.x, event.y, z)) {
                        eventQueue.add(EventPoint.intersection(z[0], z[1], prev, curr));
                    }

                    Segment next = status.next(curr);
                    if (getIntersection(curr, next, event.x, event.y, z)) {
                        eventQueue.add(EventPoint.intersection(z[0], z[1], curr, next));
                    }
                    break;
                }
                case End: {
                    final Segment curr = event.s1;
                    Segment prev = status.prev(curr);
                    Segment next = status.next(curr);
                    status.remove(curr);

                    if (getIntersection(prev, next, event.x, event.y, z)) {
                        eventQueue.add(EventPoint.intersection(z[0], z[1], prev, next));
                    }
                    break;
                }
                case Intersection:
                    final Segment s1 = event.s1;
                    final Segment s2 = event.s2;

                    result.add(s1);
                    result.add(s2);

                    Segment prev = status.prev(s1);
                    if (prev == s2) {
                        prev = status.prev(s2);
                    }

                    if (getIntersection(prev, s2, event.x, event.y, z)) {
                        eventQueue.add(EventPoint.intersection(z[0], z[1], prev, s2));
                    }

                    Segment next = status.next(s2);
                    if (next == s1) {
                        next = status.next(s1);
                    }

                    if (getIntersection(s1, next, event.x, event.y, z)) {
                        eventQueue.add(EventPoint.intersection(z[0], z[1], s1, next));
                    }

                    status.remove(s1);
                    status.remove(s2);

                    status.add(event.x, s2);
                    status.add(event.x, s1);
                    break;
            }
        }

        return result;
    }

    private static String report(Iterable<?> segments) {
        StringBuilder result = new StringBuilder();
        for (Object s : segments) {
            if (result.length() > 0) {
                result.append(",");
            }
            if (s instanceof Segment) {
                result.append(((Segment) s).number);
            } else if (s instanceof EventPoint) {
                final EventPoint p = (EventPoint) s;
                result.append(String.format("%s: (%d,%d)", p.type, p.s1.number, p.s2.number));
            }

        }
        return result.toString();
    }

    private static boolean getIntersection(Segment s1, Segment s2, float evtX, float evtY, float[] out) {
        if (s1 != null && s2 != null && intersect(s1, s2)) {
            final int dx1 = s1.end.x - s1.start.x;
            final int dy1 = s1.end.y - s1.start.y;
            final int dx2 = s2.end.x - s2.start.x;
            final int dy2 = s2.end.y - s2.start.y;

            final float x, y;
            if (dx1 != 0 && dx2 != 0) {
                final float k1 = (float) dy1 / (float) dx1;
                final float b1 = s1.start.y - k1 * s1.start.x;
                final float k2 = (float) dy2 / (float) dx2;
                final float b2 = s2.start.y - k2 * s2.start.x;
                // check if points are collinear
                if (Math.abs(k1 - k2) < 0.00000001f) {
                    throw new IllegalArgumentException("Segments are collinear");
                }
                x = (b2 - b1) / (k1 - k2);
                y = k1 * x + b1;
            } else if (dx1 != 0) {
                final float k1 = (float) dy1 / (float) dx1;
                final float b1 = s1.start.y - k1 * s1.start.x;
                x = s2.start.x;
                y = k1 * x + b1;
            } else if (dx2 != 0) {
                final float k2 = (float) dy2 / (float) dx2;
                final float b2 = s2.start.y - k2 * s2.start.x;
                x = s1.start.x;
                y = k2 * x + b2;
            } else {
                throw new IllegalArgumentException("Both segments are vertical");
            }

            // should intersect below event line
            if (y < evtY) {
                out[0] = x;
                out[1] = y;
                return true;
            }
        }
        return false;
    }

    private static boolean intersect(Segment s1, Segment s2) {
        final int s11 = area2Sign(s1, s2.start);
        final int s12 = area2Sign(s1, s2.end);
        if (s11 == s12) {
            return false;
        }

        final int s21 = area2Sign(s2, s1.start);
        final int s22 = area2Sign(s2, s1.end);
        return s21 != s22;
    }

    private static int area2Sign(Segment s, Point p) {
        long vx = s.end.x - s.start.x;
        long vy = s.end.y - s.start.y;
        long ux = p.x - s.start.x;
        long uy = p.y - s.start.y;
        return sign(vx * uy - ux * vy);
    }

    private static int sign(long val) {
        return val < 0 ? -1 : (val == 0 ? 0 : 1);
    }

    private static EventQueue createEventQueue(List<Segment> segments) {
        EventQueue queue = new TreeSetEventQueue();
        for (Segment s : segments) {
            queue.add(EventPoint.start(s));
            queue.add(EventPoint.end(s));
        }

        return queue;
    }

    public static void main(String[] args) {
        try (FastScanner scanner = new FastScanner(System.in)) {
            int n = scanner.nextInt();

            List<Segment> segments = new ArrayList<>(n);
            for (int i = 0; i < n; ++i) {
                segments.add(readSegment(scanner));
            }
            List<Segment> intersections = solve(segments);
            print(intersections);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Segment readSegment(FastScanner scanner) throws IOException {
        Point a = readPoint(scanner);
        Point b = readPoint(scanner);
        return new Segment(a, b);
    }

    private static Point readPoint(FastScanner scanner) throws IOException {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        return new Point(x, y);
    }

    private static void print(List<Segment> segments) {
        if (segments.size() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        System.out.println(segments.size() / 2);
        for (int i = 0; i < segments.size(); ) {
            Segment s1 = segments.get(i++);
            Segment s2 = segments.get(i++);
            System.out.printf("%d %d %d %d %d %d %d %d\n", s1.start.x, s1.start.y, s1.end.x, s1.end.y, s2.start.x, s2.start.y, s2.end.x, s2.end.y);
        }
    }

    public static class EventPoint implements Comparable<EventPoint> {
        public enum Type {
            Start,
            Intersection,
            End
        }

        public final Type type;
        public final float x;
        public final float y;
        public final Segment s1;
        public final Segment s2;

        private EventPoint(Type type, float x, float y, Segment s1, Segment s2) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.s1 = s1;
            this.s2 = s2;
        }

        public static EventPoint start(Segment segment) {
            final Point p;
            if (segment.start.y > segment.end.y) {
                p = segment.start;
            } else {
                p = segment.end;
            }

            return new EventPoint(Type.Start, p.x, p.y, segment, segment);
        }

        public static EventPoint end(Segment segment) {
            final Point p;
            if (segment.start.y < segment.end.y) {
                p = segment.start;
            } else {
                p = segment.end;
            }

            return new EventPoint(Type.End, p.x, p.y, segment, segment);
        }

        public static EventPoint intersection(float x, float y, Segment s1, Segment s2) {
            return new EventPoint(Type.Intersection, x, y, s1, s2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EventPoint that = (EventPoint) o;
            return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, x, y, s1, s2);
        }

        @Override
        public int compareTo(EventPoint other) {
            // reverse y-order
            int cmp = Float.compare(other.y, y);
            if (cmp != 0) {
                return cmp;
            }

            return Float.compare(x, other.x);
        }
    }

    public interface EventQueue extends Iterable<EventPoint> {
        void add(EventPoint point);

        EventPoint poll();

        int size();
    }

    public static final class TreeSetEventQueue implements EventQueue {
        private final TreeSet<EventPoint> set;

        public TreeSetEventQueue() {
            set = new TreeSet<>();
        }

        @Override
        public void add(EventPoint point) {
            set.add(point);
        }

        @Override
        public EventPoint poll() {
            return set.pollFirst();
        }

        @Override
        public int size() {
            return set.size();
        }

        @Override
        public Iterator<EventPoint> iterator() {
            return set.iterator();
        }
    }

    public interface SegmentStatus extends Iterable<Segment> {
        void add(float x, Segment segment);

        void remove(Segment segment);

        void swap(Segment s1, Segment s2);

        Segment prev(Segment segment);

        Segment next(Segment segment);
    }

    public static final class ListSegmentStatus implements SegmentStatus {
        @Override
        public Iterator<Segment> iterator() {
            return segments().iterator();
        }

        private static class Node {
            float x;
            Segment segment;

            Node(float x, Segment segment) {
                this.x = x;
                this.segment = segment;
            }
        }

        private final List<Node> list = new ArrayList<>();

        @Override
        public void add(float x, Segment segment) {
            int pos = findInsertPos(x, segment);
            list.add(pos, new Node(x, segment));
        }

        @Override
        public void remove(Segment segment) {
            int index = indexOf(segment);
            list.remove(index);
        }

        @Override
        public void swap(Segment s1, Segment s2) {
            int index1 = indexOf(s1);
            int index2 = indexOf(s2);
            list.get(index1).segment = s2;
            list.get(index2).segment = s1;
        }

        @Override
        public Segment prev(Segment segment) {
            int index = indexOf(segment);
            return index > 0 ? list.get(index - 1).segment : null;
        }

        @Override
        public Segment next(Segment segment) {
            int index = indexOf(segment);
            return index < list.size() - 1 ? list.get(index + 1).segment : null;
        }

        public List<Segment> segments() {
            return list.stream().map(node -> node.segment).collect(Collectors.toList());
        }

        private int findInsertPos(float x, Segment segment) {
            int pos = 0;
            while (pos < list.size() && (list.get(pos).x < x || Float.compare(list.get(pos).x, x) == 0 && cmp(list.get(pos).segment, segment) < 0)) {
                pos += 1;
            }

            return pos;
        }

        private int cmp(Segment s1, Segment s2) {
            float k1 = slope(s1);
            float k2 = slope(s2);
            int cmp = Float.compare(k2, k1);
            if (cmp != 0) {
                return cmp;
            }
            return Integer.compare(s2.start.y, s1.start.y);
        }

        private float slope(Segment s) {
            int dx = s.start.x - s.end.x;
            int dy = s.start.y - s.end.y;
            return (float) dy / (float) dx;
        }

        private int indexOf(Segment segment) {
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).segment == segment) {
                    return i;
                }
            }
            throw new IllegalArgumentException("Segment is not in the list: " + segment);
        }
    }

    public static final class Segment {
        public final Point start;
        public final Point end;
        public int number;

        public Segment(int x1, int y1, int x2, int y2) {
            this(new Point(x1, y1), new Point(x2, y2));
        }

        public Segment(Point start, Point end) {
            if (start.y > end.y) {
                this.start = start;
                this.end = end;
            } else {
                this.start = end;
                this.end = start;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Segment segment = (Segment) o;
            return start.equals(segment.start) && end.equals(segment.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        @Override
        public String toString() {
            return String.format("%d:[%s,%s]", number, start, end);
        }
    }

    public static final class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", x, y);
        }
    }

    public static class FastScanner implements Closeable {
        private final InputStreamReader stream;
        private final BufferedReader reader;
        private String line;
        private int pos;

        public FastScanner(InputStream in) {
            stream = new InputStreamReader(in);
            reader = new BufferedReader(stream);
            line = "";
        }

        public int nextInt() throws IOException {
            // check if we need to read another line
            if (pos == line.length()) {
                readLine();
            }

            // skip all whitespaces
            while (pos < line.length() && isWhitespace(line.charAt(pos))) {
                pos += 1;

                // read another line (if necessary)
                if (pos == line.length()) {
                    readLine();
                }
            }

            int num = 0;
            int sign = 1;
            if (line.charAt(pos) == '-') {
                sign = -1;
                pos += 1;
            }

            // check if we reached the end of line
            if (pos == line.length()) {
                throw new NumberFormatException();
            }

            // next character must be digit
            if (!isDigit(line.charAt(pos))) {
                throw new NumberFormatException();
            }

            // read next number
            while (pos < line.length()) {
                final char c = line.charAt(pos);
                if (!isDigit(c)) {
                    break;
                }
                num = 10 * num + (c - '0');
                pos++;
            }
            return sign * num;
        }

        private void readLine() throws IOException {
            line = reader.readLine();
            if (line == null) {
                throw new EOFException();
            }
            pos = 0;
        }

        private static boolean isDigit(char c) {
            return c >= '0' && c <= '9';
        }

        private static boolean isWhitespace(char c) {
            return c == ' ' || c == '\n' || c == '\t';
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }
    }

    private static class MathUtil {
        public static boolean approxEqual(float a, float b) {
            return Math.abs(a - b) < 0.0001f;
        }
    }
}