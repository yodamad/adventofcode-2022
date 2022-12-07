package adventofcode.day7

import adventofcode.shared.getLines

class DaySeven {

    val DAY = "day7"

    private val directories = mutableMapOf<String, Directory>()
    fun commands(file: String) {

        var currentDir = Directory("/", 0)
        directories["/"] = currentDir

        file.getLines(DAY).forEach { cmd ->
            //println(">>>>>> Command $cmd in $currentDir")
            if (cmd.equals("$ cd ..")) {
                currentDir = currentDir.parent!!
            } else if (cmd.startsWith("$ cd")) {
                val path = cmd.split(" ")[2]
                if (path != "/") {
                    val currentPath = "${currentDir.fullPath()}$path/"
                    currentDir = directories[currentPath]!!
                }
            } else if (cmd.startsWith("$ ls")) {
                // Do nothing
            } else if (cmd.startsWith("dir")) {
                val dirName = cmd.split(" ")[1]
                val newDir = Directory(dirName, 0, currentDir)
                directories[newDir.fullPath()] = newDir
                currentDir.children.add(newDir)
            } else {
                currentDir.size += cmd.split(" ")[0].toInt()
            }
        }
        println(directories)
        println(filterDirectories().sumOf { it.fullSize() })
        val newLimit = 70000000 - directories["/"]!!.fullSize()
        println("newLimit is $newLimit")
        println(filterBigDirectories(30000000 - newLimit).minOf { it.fullSize() })
    }

    private fun filterDirectories(limit: Int = 100000) = directories.filter { it.value.fullSize() <= limit }.map { it.value }.toList()
    private fun filterBigDirectories(limit: Int = 100000) = directories.filter { it.value.fullSize() >= limit }.map { it.value }.toList()
}

data class Directory(val name: String, var size: Int, val parent: Directory? = null, val children: MutableList<Directory> = mutableListOf()) {
    override fun toString(): String {
        return "Directory $name has size ${fullSize()}"
    }

    fun fullPath(): String =
        if (parent == null) {
            "/"
        } else {
            "${parent?.fullPath()}$name/"
        }

    fun fullSize(): Int {
        return size + children.sumOf { it.fullSize() }
    }
}