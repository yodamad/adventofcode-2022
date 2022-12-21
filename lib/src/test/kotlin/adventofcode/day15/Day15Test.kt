package adventofcode.day15

import kotlin.math.abs

class Day15Test {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day15Test().testSensors()
        }
    }

    fun testSample() {
        Day15().sensors("sample", 10)
    }

    fun testSensors() {
        Day15().sensors("sensors", 2000000)
    }
}