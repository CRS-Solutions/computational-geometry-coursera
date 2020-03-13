import Problem3KdTree.KdTree
import Problem3KdTree.Point
import processing.core.PApplet
import processing.core.PMatrix2D
import processing.core.PVector

private class Sketch3(points: List<Point>, val query: Point, val expected: Point, val actual: Point, width: Int = 500, height: Int = 500) : PApplet() {
    private val matrix: PMatrix2D
    private val matrixInv: PMatrix2D
    private var tree = KdTree(points)

    init {
        this.width = width
        this.height = height
        val (min, max) = bounds(points)
        val scaleX = 0.8f * width / (max.x - min.x)
        val scaleY = 0.8f * height / (max.y - min.y)
        val scale = min(scaleX, scaleY)

        matrix = PMatrix2D()
        matrix.translate(0.5f * width, 0.5f * height)
        matrix.scale(scale, -scale)
        matrixInv = PMatrix2D(matrix)
        matrixInv.invert()
    }

    override fun settings() {
        size(width, height)
        pixelDensity = 2
    }

    override fun setup() {
    }

    override fun draw() {
        background(255)
        drawOrigin()

        drawTree(tree)
        drawQuery()
    }

    private fun drawQuery() {
        drawPoint(query, color = 0xff0000ff)
        drawPoint(expected, color = 0xff00ff00)
        drawLine(query, expected, color = 0xff00ff00)
        drawPoint(actual, color = 0xffff0000)
        drawLine(query, actual, color = 0xffff0000)
    }

    private fun drawOrigin() {
        stroke(0.85f)
        strokeWeight(0.5f)
        drawLine(0f, -0.5f * height, 0f, 0.5f * height)
        drawLine(-0.5f * width, 0f, 0.5f * width, 0f)
        strokeWeight(1.0f)
    }

    private fun drawPoints(points: Iterable<Point>, color: Long = 0xffff0000) {
        for (curr in points) {
            drawPoint(curr, color)
        }
    }

    private fun drawTree(tree: KdTree) {
        val xMin = -0.5f * width
        val yMin = -0.5f * height
        val xMax = 0.5f * width
        val yMax = 0.5f * height
        drawTree(tree.root, xMin, yMin, xMax, yMax)
    }

    private fun drawTree(root: KdTree.Node?, xMin: Float, yMin: Float, xMax: Float, yMax: Float, depth: Int = 0) {
        root?.run {
            drawPoint(point)
            if (depth % 2 == 0) {
                val x = point.x.toFloat()
                drawLine(x, yMin, x, yMax)
                drawTree(left, xMin, yMin, x, yMax, depth + 1)
                drawTree(right, x, yMin, xMax, yMax, depth + 1)
            } else {
                val y = point.y.toFloat()
                drawLine(xMin, y, xMax, y)
                drawTree(left, xMin, yMin, xMax, y, depth + 1)
                drawTree(right, xMin, y, xMax, yMax, depth + 1)
            }
        }
    }

//    private fun drawRect(x1: Int, y1: Int, x2: Int, y2: Int, color: Long = 0) {
//        drawLine(x1, y1, x1, y2, color)
//        drawLine(x1, y2, x2, y2, color)
//        drawLine(x2, y2, x2, y1, color)
//        drawLine(x2, y1, x1, y1, color)
//    }

    private fun drawLine(p1: Point, p2: Point, color: Long = 0) {
        drawLine(p1.x.toFloat(), p1.y.toFloat(), p2.x.toFloat(), p2.y.toFloat(), color)
    }
    private fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float, color: Long = 0) {
        stroke(color.toInt())
        val v1 = matrix.mult(PVector(x1, y1), null)
        val v2 = matrix.mult(PVector(x2, y2), null)
        line(v1.x, v1.y, v2.x, v2.y)
    }

    private fun drawPoint(p: Point, color: Long = 0) {
        drawPoint(p.x, p.y, color)
    }

    private fun drawPoint(x: Int, y: Int, color: Long = 0) {
        stroke(color.toInt())
        fill(color.toInt())
        val v = matrix.mult(PVector(x.toFloat(), y.toFloat()), null)
        ellipse(v.x, v.y, 2.5f, 2.5f)
    }

    private companion object {
        fun bounds(points: List<Point>): Pair<Point, Point> {
            var minX = points[0].x
            var minY = points[0].y
            var maxX = points[0].x
            var maxY = points[0].y
            for (p in points) {
                minX = min(minX, p.x)
                minY = min(minY, p.y)
                maxX = max(maxX, p.x)
                maxY = max(maxY, p.y)
            }

            return Pair(Point(minX, minY), Point(maxX, maxY))
        }
    }
}

fun main() {
    val points = parsePoints("14 95 -5 -34 -34 -9 -4 -70 -87 -46 32 -42 2 -14 59 -76 92 5 -84 -57")
    val query = Point(-73, -53)
    val expected = Point(-84, -57)
    val actual = Point(-34, -9)
    Sketch3(points, query, expected, actual).run()
}

fun parsePoints(s: String): List<Point> {
    val tokens = s.split("\\s+".toRegex()).toTypedArray()
    require(tokens.size % 2 == 0)
    return List(tokens.size / 2) {
        val x = tokens[2 * it].toInt()
        val y = tokens[2 * it + 1].toInt()
        Point(x, y)
    }
}
