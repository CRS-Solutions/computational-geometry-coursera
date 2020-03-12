import Problem2KdTree.Point
import Problem2KdTree.Rect
import org.junit.Assert
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class Problem2KdTreeTest {
    @Test
    fun test1() {
        val points = parsePoints("-9 6 -8 3 1 7 3 0 8 -4 -2 8 -6 -3 -1 2 2 -2 0 1 6 5 -5 4 -3 -1")
        val rects = parseRects("-10 2 -3 7\n" +
                "-4 -2 -2 -1\n" +
                "4 2 7 4\n" +
                "-9 -4 8 8")
        val expected = parseNumbers("3\n" +
                "1\n" +
                "0\n" +
                "13")
        val actual = Problem2KdTree.solve(points, rects)
        assertArrayEquals(expected, actual)
    }

    private fun parsePoints(s: String): List<Point> {
        val tokens = s.split("\\s+".toRegex()).toTypedArray()
        require(tokens.size % 2 == 0) { "Illegal polygon: $s" }
        val n = tokens.size / 2
        val points: MutableList<Point> = ArrayList(n)
        var i = 0
        var j = 0
        while (i < n) {
            val x = tokens[j++].toInt()
            val y = tokens[j++].toInt()
            points.add(Point(x, y))
            ++i
        }
        return points
    }

    private fun parseRects(s: String): List<Rect> {
        val tokens = s.split("\\s+".toRegex())
        require(tokens.size % 4 == 0) { "Illegal rectangles: $s" }
        val n = tokens.size / 4
        val rects = ArrayList<Rect>(n)
        var i = 0
        var j = 0
        while (i < n) {
            val x1 = tokens[j++].toInt()
            val y1 = tokens[j++].toInt()
            val x2 = tokens[j++].toInt()
            val y2 = tokens[j++].toInt()
            rects.add(Rect(x1, y1, x2, y2))
            ++i
        }
        return rects
    }

    private fun parseNumbers(s: String): IntArray {
        return s.split("\\s+".toRegex()).map { it.toInt() }.toIntArray()
    }
}