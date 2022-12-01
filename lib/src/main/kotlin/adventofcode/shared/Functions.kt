package adventofcode.shared

import java.io.File

fun String.getLines(day: String) = File("./lib/src/test/resources/$day/$this").readLines()
