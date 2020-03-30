package by.popkov.homework3.kotlin_task_1

import kotlin.math.PI
import kotlin.math.pow

class Circle(private val radius: Double) : Shape {
    override fun getArea(): Double = radius.pow(2) * PI
}