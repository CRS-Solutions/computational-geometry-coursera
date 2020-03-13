import org.junit.Assert
import org.junit.Test

class Problem3KdTreeTest {
    @Test
    fun solve() {
        val points = parsePoints("-9 6 -8 3 1 7 3 0 8 -4 -2 8 -6 -3 -1 2 2 -2 0 1 6 5 -5 4 -3 -1")
        val query = parsePoints("0 2\n" +
                "3 2\n" +
                "6 5\n" +
                "10 0\n" +
                "-2 10")
        val expected = parseNumbers("1\n" +
                "2\n" +
                "0\n" +
                "4\n" +
                "2")
        val actual = Problem3KdTree.solve(points, query)
        Assert.assertArrayEquals(expected, actual)
    }

    private fun parseNumbers(s: String): IntArray {
        return s.split("\\s+".toRegex()).map { it.toInt() }.toIntArray()
    }
}