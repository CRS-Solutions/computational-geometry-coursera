import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class EventQueueTest {
    @Test
    public void test1() {
        Problem4.Segment s1 = new Problem4.Segment(0, 0, 4, 4);
        Problem4.Segment s2 = new Problem4.Segment(5, -1, 1, 3);
        Problem4.EventQueue queue = new Problem4.TreeSetEventQueue();
        queue.add(Problem4.EventPoint.start(s1));
        queue.add(Problem4.EventPoint.end(s1));
        queue.add(Problem4.EventPoint.start(s2));
        queue.add(Problem4.EventPoint.end(s2));
        queue.add(Problem4.EventPoint.intersection(2, 2, s1, s2));
        queue.add(Problem4.EventPoint.intersection(2, 2, s1, s2));

        List<Problem4.EventPoint> expected = Arrays.asList(
            Problem4.EventPoint.start(s1),
            Problem4.EventPoint.start(s2),
            Problem4.EventPoint.intersection(2, 2, s1, s2),
            Problem4.EventPoint.end(s1),
            Problem4.EventPoint.end(s2)
        );
        final List<Problem4.EventPoint> actual = StreamSupport.stream(queue.spliterator(), false).collect(Collectors.toList());

        assertEquals(expected, actual);
    }
}