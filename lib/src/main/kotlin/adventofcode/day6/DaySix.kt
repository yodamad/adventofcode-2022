package adventofcode.day6

class DaySix {

    fun readBuffer(line: String, bufferSize: Int = 4) {
        var nbElements = 0
        var index = 0
        do {
            nbElements = line.toCharArray().slice(index until index + bufferSize).distinct().size
            index++
        } while (nbElements < bufferSize)
        println(index+bufferSize-1)
    }
}