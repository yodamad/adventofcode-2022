package adventofcode.day12

class DayTwelveTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //DayTwelveTest().sample()
            DayTwelveTest().map()
        }
    }

    fun sample() {
        DayTwelve().findMyWay("sample")
    }

    fun map() {
        DayTwelve().findMyWay("map")
    }
}