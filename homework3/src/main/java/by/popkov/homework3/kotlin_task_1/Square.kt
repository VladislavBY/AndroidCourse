package by.popkov.homework3.kotlin_task_1

import kotlin.math.pow


class Square(private val side: Double) : Shape {
    override fun getArea(): Double = side.pow(2)

}