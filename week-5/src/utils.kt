import processing.core.PApplet

fun PApplet.run() {
    PApplet.runSketch(arrayOf(javaClass.name), this)
}