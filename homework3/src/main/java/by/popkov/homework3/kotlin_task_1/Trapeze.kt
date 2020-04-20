package by.popkov.homework3.kotlin_task_1

class Trapeze(private val bottom: Double, private val top: Double, private val height: Double) : Shape {
    override fun getArea(): Double = ((bottom + top) / 2.0) * height
}