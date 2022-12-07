package adventofcode.day6

import adventofcode.shared.getLines

class DaySixTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DaySixTest().test1()
            DaySixTest().test2()
            DaySixTest().test3()
            DaySixTest().test4()
            DaySixTest().findIndex()
            DaySixTest().findBigIndex()
        }
    }

    fun test1() {
        DaySix().readBuffer("bvwbjplbgvbhsrlpgdmjqwftvncz")
    }

    fun test2() {
        DaySix().readBuffer("nppdvjthqldpwncqszvftbrmjlhg")
    }

    fun test3() {
        DaySix().readBuffer("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
    }

    fun test4() {
        DaySix().readBuffer("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")
    }

    fun findIndex() {
        DaySix().readBuffer("input".getLines("day6").first())
    }

    fun findBigIndex() {
        DaySix().readBuffer("input".getLines("day6").first(), 14)
    }
}