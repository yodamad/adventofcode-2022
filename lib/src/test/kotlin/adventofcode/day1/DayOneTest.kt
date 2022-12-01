package adventofcode.day1

class DayOneTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(DayOneTest().top3())
        }
    }

    fun sample(): Int? {
        return DayOne().getStrongest("sample")
    }

    fun stepOne(): Int? {
        return DayOne().getStrongest("step1")
    }

    fun top3sample() : Int {
        return DayOne().getTop3("sample")
    }

    fun top3() : Int {
        return DayOne().getTop3("step1")
    }
}