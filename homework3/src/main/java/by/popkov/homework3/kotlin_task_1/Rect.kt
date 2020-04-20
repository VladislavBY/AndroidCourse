package by.popkov.homework3.kotlin_task_1

class Rect(private val height: Double, private val width: Double) : Shape {
    override fun getArea(): Double = height * width
}