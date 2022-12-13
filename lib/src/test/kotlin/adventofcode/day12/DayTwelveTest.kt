package adventofcode.day12

class DayTwelveTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //DayTwelveTest().sample()
            //DayTwelveTest().map()
            //DayTwelveTest().sampleShort()
            DayTwelveTest().shortPath()
        }
    }

    fun sample() {
        DayTwelve().findMyWay("sample")
    }

    fun map() {
        DayTwelve().findMyWay("map")
    }

    fun sampleShort() {
        DayTwelve().findMyWays("sample")
    }

    fun shortPath() {
        DayTwelve().findMyWays("map")
    }
}