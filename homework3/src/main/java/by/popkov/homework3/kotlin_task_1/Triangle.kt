package by.popkov.homework3.kotlin_task_1

class Triangle(private val base: Double, private val height: Double) : Shape {
    override fun getArea(): Double = base * height / 2
}

fun main() {
    print(Circle(10.1).getArea())
}