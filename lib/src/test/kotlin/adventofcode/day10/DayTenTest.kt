package adventofcode.day10

class DayTenTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //println("tiny = " + DayTenTest().tiny())
            println("13140 = " + DayTenTest().sample())
            println(DayTenTest().computeCommands())
        }
    }

    fun tiny(): Int {
        return DayTen().anotherCompute("tiny", 6)
    }

    fun sample(): Int {
        return DayTen().anotherCompute("sample", 230)
    }

    fun computeCommands(): Int {
        return DayTen().anotherCompute("commands", 230)
    }
}