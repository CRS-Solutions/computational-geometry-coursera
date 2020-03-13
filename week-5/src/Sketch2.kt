import Problem2KdTree.KdTree
import Problem2KdTree.Point
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PConstants.LEFT

private class Sketch2(width: Int = 500, height: Int = 500) : PApplet() {
    private val points = mutableListOf<Point>()
    private val scale: Float
    private var tree = KdTree(points);

    init {
        this.width = width
        this.height = height
        scale = 1.0f
    }

    override fun settings() {
        size(500, 500)
        pixelDensity = 2
    }

    override fun setup() {
    }

    override fun draw() {
        background(255)
        translate(0.5f * width, 0.5f * height)
        drawOrigin()
        drawTree(tree)
    }

    override fun mouseClicked() {
        if (mouseButton == PConstants.LEFT) {
            points.add(Point(mouseX - width / 2, mouseY - height / 2))
        } else {
            points.clear()
        }
        tree = KdTree(points)
    }

    private fun drawOrigin() {
        stroke(0.85f)
        strokeWeight(0.5f)
        line(0f, -0.5f * height, 0f, 0.5f * height)
        line(-0.5f * width, 0f, 0.5f * width, 0f)
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

    private fun drawTree(root: KdTree.Node?, xMin: Float, yMin: Float, xMax: Float, yMax: Float, vertical: Boolean = true) {
        root?.run {
            drawPoint(point)
            if (vertical) {
                val x = scale * point.x
                line(x, yMin, x, yMax)
                drawTree(left, xMin, yMin, x, yMax, !vertical)
                drawTree(right, x, yMin, xMax, yMax, !vertical)
            } else {
                val y = scale * point.y
                line(xMin, y, xMax, y)
                drawTree(left, xMin, yMin, xMax, y, !vertical)
                drawTree(right, xMin, y, xMax, yMax, !vertical)
            }
        }
    }

    private fun drawRect(x1: Int, y1: Int, x2: Int, y2: Int, color: Long = 0) {
        drawLine(x1, y1, x1, y2, color)
        drawLine(x1, y2, x2, y2, color)
        drawLine(x2, y2, x2, y1, color)
        drawLine(x2, y1, x1, y1, color)
    }

    private fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int, color: Long = 0) {
        stroke(color.toInt())
        line(scale * x1, scale * y1, scale * x2, scale * y2)
    }

    private fun drawPoint(p: Point, color: Long = 0) {
        drawPoint(p.x, p.y, color)
    }

    private fun drawPoint(x: Int, y: Int, color: Long = 0) {
        stroke(color.toInt())
        fill(color.toInt())
        ellipse(scale * x, scale * y, 2.5f, 2.5f)
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

private sealed class Operation {
    data class Point(val x: Int, val y: Int, val color: Int) : Operation()
    data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int, val color: Int) : Operation()
    data class Rect(val x1: Int, val y1: Int, val x2: Int, val y2: Int, val color: Int) : Operation()
}

fun main() {
    Sketch2().run()
}