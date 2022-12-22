package adventofcode.day15

import java.time.Instant.now
import kotlin.math.abs

class Day15Test {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day15Test().testSample()
            Day15Test().testSensors()
        }
    }

    fun testSample() {
        Day15().sensors("sample", 10, 20)
    }

    fun testSensors() {
        Day15().sensors("sensors", 2_000_000, 4_000_000)
    }
}