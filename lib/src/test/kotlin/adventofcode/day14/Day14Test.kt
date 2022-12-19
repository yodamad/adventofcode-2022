package adventofcode.day14

class Day14Test {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day14Test().sample()
            Day14Test().sampleWithFloor()
            //Day14Test().map()
        }
    }

    fun sample() {
        Day14().fillWithSand("sample", false)
    }

    fun sampleWithFloor() {
        Day14().fillWithSand("sample", true)
    }

    fun map() {
        Day14().fillWithSand("map", true)
    }
}