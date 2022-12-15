package adventofcode.day13

class Day13Test {
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            //Day13Test().sample()
            //Day13Test().debug()
            Day13Test().analyzePairs()
        }
    }

    fun sample() {
        Day13().computePairs("sample")
    }

    fun debug() {
        Day13().computePairs("debug")
    }

    fun analyzePairs() {
        Day13().computePairs("allpairs")
    }
}