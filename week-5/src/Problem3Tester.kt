import Problem3KdTree.KdTree
import Problem3KdTree.KdTree.dist
import Problem3KdTree.Point
import java.util.*
import kotlin.random.asKotlinRandom

fun main() {
    val size = 100
    val range = 100

    val trials = 100
    for (i in 0..trials) {
        val points = createPoints(size, -range, range)
        val tree = KdTree(points)
        val query = createPoints(size, -(1.1f * range).toInt(), (1.1f * range).toInt())
        for (q in query) {
            val expected = tree.nearestNeighbourBruteForce(q)
            val actual = tree.nearestNeighbour(q)
            if (dist(q, expected) != dist(q, actual)) {
                println("val points = parsePoints(\"${points.joinToString(" ") { "${it.x} ${it.y}" }}\")")
                println("val query = Point(${q.x}, ${q.y})")
                println("val expected = Point(${expected.x}, ${expected.y})")
                println("val actual = Point(${actual.x}, ${actual.y})")
            }
        }
    }
}

private fun createPoints(size: Int, lo: Int, hi: Int): List<Point> {
    val random = Random().asKotlinRandom()
    return List(size) {
        val x = random.nextInt(lo, hi)
        val y = random.nextInt(lo, hi)
        Point(x, y)
    }
}