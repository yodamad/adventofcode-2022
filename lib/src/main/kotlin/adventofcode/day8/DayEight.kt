package adventofcode.day8

import adventofcode.shared.getLines
import java.util.stream.IntStream
import kotlin.math.max

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
                print(forest[a.key]!![b.key]!!.height)
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
                    val treeSize = forest[e.key]!![t.key]!!.height
                    var scenicScore = 1

                    // Check left
                    var leftVisible = true
                    IntStream.range(0, x).forEach { tree ->
                        leftVisible = leftVisible && (forest[e.key]!![tree]!!.height < treeSize)
                    }

                    var currentScore = 1
                    if (forest[y]!![x]!!.height > forest[y]!![x-1]!!.height) {
                        var leftView = x-1
                        while (leftView > 0) {
                            if (treeSize > forest[y]!![leftView]!!.height) {
                                currentScore++
                                leftView--
                            } else
                                break
                        }
                    }

                    println("Left score is $currentScore")
                    scenicScore *= currentScore

                    // Check right
                    var rightVisible = true
                    IntStream.range(x+1, colNb).forEach { tree ->
                        rightVisible = rightVisible && (forest[e.key]!![tree]!!.height < treeSize)
                    }

                    currentScore = 1
                    if (forest[y]!![x]!!.height > forest[y]!![x+1]!!.height) {
                        var rightView = x+1
                        while (rightView < colNb-1) {
                            if (treeSize > forest[y]!![rightView]!!.height) {
                                currentScore++
                                rightView++
                            } else
                                break
                        }
                    }
                    println("Right score is $currentScore")
                    scenicScore *= currentScore

                    // Check top
                    var topVisible = true
                    IntStream.range(0, y).forEach { tree ->
                        topVisible = topVisible && (forest[tree]!![x]!!.height < treeSize)
                    }

                    currentScore = 1
                    if (forest[y-1]!![x]!!.height < forest[y]!![x]!!.height) {
                        var topView = y-1
                        while (topView > 0) {
                            if (treeSize > forest[topView]!![x]!!.height) {
                                currentScore++
                                topView--
                            } else
                                break
                        }
                    }
                    println("Top score is $currentScore")
                    scenicScore *= currentScore

                    // Check bottom
                    var bottomVisible = true
                    IntStream.range(y+1, rowNb).forEach { tree ->
                        bottomVisible = bottomVisible && (forest[tree]!![x]!!.height < treeSize)
                    }

                    currentScore = 1
                    if (forest[y]!![x]!!.height > forest[y+1]!![x]!!.height) {
                        var bottomView = y+1
                        while (bottomView < rowNb-1) {
                            if (treeSize > forest[bottomView]!![x]!!.height) {
                                currentScore++
                                bottomView++
                            } else
                                break
                        }
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

data class Tree(val x: Int, val y: Int, val height: Int) {
    override fun toString(): String {
        return "Current tree ($x,$y) is $height"
    }
}