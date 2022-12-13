package adventofcode.day12

import adventofcode.shared.getLines

class DayTwelve {

    private val DAY = "day12"
    private val alphabet = "abcdefghijklmnopqrstuvwxyzE"
    private lateinit var myMap: List<CharArray>
    private lateinit var start: Pair<Int, Int>
    private lateinit var arrival: Pair<Int, Int>
    private var visitedNodes = mutableListOf<Node>()

    fun findMyWay(file: String) {
        val lines = file.getLines(DAY)
        findStart(lines)
        findArrival(lines)
        buildMap(lines)
        var endFound = false
        var leaves = listOf(Node(start.first, start.second, 'S', depth = 0))
        while (!endFound && leaves.isNotEmpty()) {
            var buffer = leaves.map { findLeaves(it) }.flatten().distinct()
            leaves = buffer
            endFound = leaves.any{ it.isFinal }
            if (endFound) visitedNodes.addAll(buffer)
        }
        // println(visitedNodes.reversed())
        val shorterPath = visitedNodes.filter { it.isFinal }.map { it.depth }.minOf { it }
        println("Shorter Path is $shorterPath")
    }

    fun findMyWays(file: String) {
        val lines = file.getLines(DAY)
        val lowPoints = findLowPoints(lines)
        findArrival(lines)
        buildMap(lines)
        val shorterPathOfAll = lowPoints.map {
            var endFound = false
            var leaves = listOf(it)
            visitedNodes = mutableListOf()

            while (!endFound && leaves.isNotEmpty()) {
                val buffer = leaves.map { it2 -> findLeaves(it2) }.flatten().distinct()
                leaves = buffer
                endFound = leaves.any{ itX -> itX.isFinal }
                if (endFound) visitedNodes.addAll(buffer)
            }
            //println(visitedNodes.reversed())
            val shorterPath = visitedNodes
                .filter { it3 -> it3.isFinal }
                .map { it4 -> it4.depth }
                .minOfOrNull{ it5 -> it5 }
            println("Shorter Path from $it is $shorterPath")
            shorterPath
        }.filterNotNull()
            .minOfOrNull { it }
        println(shorterPathOfAll)
    }

    private fun findArrival(lines: List<String>) {
        lines
            .mapIndexed { index, s ->
                if (s.contains('E')) arrival = Pair(s.indexOf('E'), index) }
            .count()
        println("Arrival point is $arrival")
    }

    private fun findStart(lines: List<String>) {
        lines
            .mapIndexed { index, s ->
                if (s.contains('S')) start = Pair(s.indexOf('S'), index) }
            .count()
        println("Starting point is $start")
    }

    private fun findLowPoints(lines: List<String>) = lines
            .mapIndexed { indexY, s ->
                s.mapIndexed { indexX, c ->
                    Node(indexX, indexY, c, depth = 0)
                }.filter { it.char == 'a' || it.char == 'S' }
            }.flatten()


    private fun buildMap(lines: List<String>) {
        myMap = lines.map {it.toCharArray() }.toList()
        myMap.forEach { println(String(it)) }
    }

    private fun findLeaves(node: Node): List<Node> {
        visitedNodes.add(node)
        //println("visited nodes are $visitedNodes")
        if (!node.isFinal) {
            val leaves = listOf(node.x to (node.y - 1), node.x to node.y + 1, node.x - 1 to node.y, node.x + 1 to node.y)
                .mapNotNull { (x, y) ->
                    myMap.getOrNull(y)?.getOrNull(x)?.let { Node(x, y, it, it == 'E', node.depth + 1) }
                }
                .map {
                    // println(it)
                    it
                }
                .filter { !visitedNodes.contains(it) }
                .filter { it.char == node.char
                        || (it.char == 'E' && node.char in listOf('y', 'z')
                        || (alphabet.indexOf(node.char) < 26 && it.char == alphabet[alphabet.indexOf(node.char) + 1])
                        || (alphabet.indexOf(node.char) > alphabet.indexOf(it.char))) }
            // println("leaves of $node are $leaves")
            // println("--")
            return leaves
        }
        return emptyList()
    }
}

data class Node(val x: Int, val y: Int, val char: Char, val isFinal: Boolean = false, val depth: Int) {
    override fun equals(other: Any?): Boolean {
        return other is Node && x == other.x && y == other.y
    }

    override fun toString(): String {
        return "Node [($x, $y), $char, $depth]" // has isFinal = $isFinal with depth $depth"
    }
}
