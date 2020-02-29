import org.junit.Test;

import static java.lang.Math.PI;
import static org.junit.Assert.*;

public class MathUtilsTest {
    private static final float DELTA = 0.000001f;

    @Test
    public void polarAngle() {
        assertEquals(0, MathUtils.polarAngle(1, 0), DELTA);
        assertEquals(PI / 6, MathUtils.polarAngle(0.86602540378f, 0.5f), DELTA);
        assertEquals(PI / 3, MathUtils.polarAngle(0.5f, 0.86602540378f), DELTA);
        assertEquals(PI / 2, MathUtils.polarAngle(0, 1), DELTA);
        assertEquals(2 * PI / 3, MathUtils.polarAngle(-0.5f, 0.86602540378f), DELTA);
        assertEquals(5 * PI / 6, MathUtils.polarAngle(-0.86602540378f, 0.5f), DELTA);
        assertEquals(PI, MathUtils.polarAngle(-1, 0), DELTA);
        assertEquals(7 * PI / 6, MathUtils.polarAngle(-0.86602540378f, -0.5f), DELTA);
        assertEquals(4 * PI / 3, MathUtils.polarAngle(-0.5f, -0.86602540378f), DELTA);
        assertEquals(3 * PI / 2, MathUtils.polarAngle(0, -1), DELTA);
        assertEquals(5 * PI / 3, MathUtils.polarAngle(0.5f, -0.86602540378f), DELTA);
        assertEquals(11 * PI / 6, MathUtils.polarAngle(0.86602540378f, -0.5f), DELTA);

    }

    @Test
    public void area2() {
        assertEquals(-13, MathUtils.area2(2, 3, 6, 4, 7, 1));
    }
}