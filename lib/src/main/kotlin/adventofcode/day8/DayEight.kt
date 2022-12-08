package adventofcode.day8

import adventofcode.shared.getLines
import java.util.stream.IntStream

class DayEight {

    val DAY = "day8"
    val forest = mutableMapOf<Int, MutableMap<Int, Tree>>()
    var rowNb = 0
    var colNb = 0

    fun discoverForest(file: String) {
        val lines = file.getLines(DAY)
        val edge = lines[0].length * lines.size

        buildTheForest(lines)
        rowNb = lines.size
        colNb = forest[0]!!.size
        printForest()
        exploreTheForest()
    }

    private fun buildTheForest(lines: List<String>) =
        lines.forEachIndexed { y, s ->
            val trees = s.toCharArray()
            forest[y] = mutableMapOf()
            trees.forEachIndexed { x, c ->
                forest[y]!![x] = Tree(x, y, c.digitToInt())
            }
        }

    private fun printForest() {
        forest.forEach { a ->
            a.value.forEach{b ->
                print(forest[a.key]!![b.key]!!.size)
            }
            println()
        }
    }

    private fun exploreTheForest() {
        var visibles = 0
        var globalScenicScore = 0
        forest.forEach { e ->
            e.value.forEach { t ->
                val y = e.key
                val x = t.key
                var isVisible: Boolean
                if (x == 0 || y == 0 || x == (colNb-1) || y == (rowNb-1)) {
                    isVisible = true
                } else {
                    println(forest[e.key]!![t.key]!!)
                    val treeSize = forest[e.key]!![t.key]!!.size
                    var scenicScore = 1

                    // Check left
                    var currentScore = 1
                    var leftVisible = true
                    IntStream.range(0, x).forEach { tree ->
                        leftVisible = leftVisible && (forest[e.key]!![tree]!!.size < treeSize)
                    }
                    var leftView = x-1
                    if (forest[e.key]!![leftView]!!.size > forest[e.key]!![x]!!.size) leftView = 0
                    while (leftView > 0) {
                        if (forest[e.key]!![leftView]!!.size <= forest[e.key]!![leftView-1]!!.size) {
                            currentScore++
                            leftView--
                        } else
                            leftView = 0
                    }
                    println("Left score is $currentScore")
                    scenicScore *= currentScore

                    // Check right
                    currentScore = 1
                    var rightVisible = true
                    IntStream.range(x+1, colNb).forEach { tree ->
                        rightVisible = rightVisible && (forest[e.key]!![tree]!!.size < treeSize)
                    }
                    var rightView = x+1
                    if (forest[e.key]!![rightView]!!.size > forest[e.key]!![x]!!.size) rightView = colNb
                    while (rightView < colNb-1) {
                        if (forest[e.key]!![rightView]!!.size <= forest[e.key]!![rightView+1]!!.size) {
                            currentScore++
                            rightView++
                        } else
                            rightView = colNb
                    }
                    println("Right score is $currentScore")
                    scenicScore *= currentScore

                    // Check top
                    currentScore = 1
                    var topVisible = true
                    IntStream.range(0, y).forEach { tree ->
                        topVisible = topVisible && (forest[tree]!![x]!!.size < treeSize)
                    }
                    var topView = y-1
                    if (forest[topView]!![x]!!.size > forest[y]!![x]!!.size) topView = 0
                    while (topView > 0) {
                        if (forest[topView]!![x]!!.size <= forest[topView-1]!![x]!!.size) {
                            currentScore++
                            topView--
                        } else
                            topView = 0
                    }
                    println("Top score is $currentScore")
                    scenicScore *= currentScore

                    // Check bottom
                    currentScore = 1
                    var bottomVisible = true
                    IntStream.range(y+1, rowNb).forEach { tree ->
                        bottomVisible = bottomVisible && (forest[tree]!![x]!!.size < treeSize)
                    }
                    var bottomView = y+1
                    if (forest[bottomView]!![x]!!.size > forest[y]!![x]!!.size) bottomView = rowNb
                    while (bottomView < rowNb-1) {
                        if (forest[bottomView]!![x]!!.size <= forest[bottomView+1]!![x]!!.size) {
                            currentScore++
                            bottomView++
                        } else
                            bottomView = rowNb
                    }
                    println("Bottom score is $currentScore")
                    scenicScore *= currentScore
                    println("Scenic score is $scenicScore")

                    isVisible = leftVisible || rightVisible || topVisible || bottomVisible

                    if (scenicScore > globalScenicScore) globalScenicScore = scenicScore
                }
                if (isVisible) {
                    //println("($x,$y) is visible")
                    visibles++
                }
            }
        }
        println("$visibles trees are visible")
        println("Scenic score is $globalScenicScore")
    }
}

data class Tree(val x: Int, val y: Int, val size: Int) {
    override fun toString(): String {
        return "Current tree ($x,$y) is $size"
    }
}