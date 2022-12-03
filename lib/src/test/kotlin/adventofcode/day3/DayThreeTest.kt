package adventofcode.day3

class DayThreeTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(DayThreeTest().badges())
        }
    }

    fun testSample(): Int {
        return DayThree().findLetterInRushpacks("sample")
    }

    fun rushpacks(): Int {
        return DayThree().findLetterInRushpacks("rushpacks")
    }

    fun badgesSample(): Int {
        return DayThree().findBadges("sample")
    }

    fun badges(): Int {
        return DayThree().findBadges("rushpacks")
    }
}