import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

sealed class Line
object Comment : Line()
data class Command(val cmdType: String, val n: Int) : Line()

fun main(args: Array<String>) {
    val lines = linesFromFile("./src/ackermann.txt")
    parse(lines)
}

private fun linesFromFile(url: String): List<Line> {
    return Files.readAllLines(Paths.get(url))
        .map { lineOf(it) }
}

fun lineOf(str: String): Line {
    val trimmed = str.trim()
    if (trimmed.isEmpty() || trimmed.startsWith("#")) {
        return Comment
    }
    val (type, n) = trimmed.split(" ").filter { it.isNotEmpty() }
    return Command(type.toLowerCase(), n.toInt())
}


fun parse(lines: List<Line>) {
    val scanner = Scanner(System.`in`)
    val memory = mutableMapOf<Int, Int>()

    var acc = 0
    var ic = 1

    while (true) {
        val line = lines[ic - 1]
        when (line) {
            is Comment -> {
                ic += 1
            }
            is Command -> {
                val (type, n) = line

                var nextIc: Int? = null

                val value = memory.getOrDefault(n, 0)

                when (type) {
                    "add" -> acc += value
                    "sub" -> acc -= value
                    "mul" -> acc *= value
                    "div" -> acc /= value
                    "lda" -> acc = value
                    "ldk" -> acc = n
                    "sta" -> memory[n] = acc
                    "inp" -> memory[n] = scanner.nextInt()

                    "inc" -> memory[n] = value + 1
                    "dec" -> memory[n] = value - 1

                    "ldi" -> acc = memory.getOrDefault(value, 0)
                    "sti" -> memory[value] = acc

                    "out" -> println(value)
                    "hlt" -> return
                    "jmp" -> nextIc = n
                    "jez" -> if (acc == 0) nextIc = n
                    "jne" -> if (acc != 0) nextIc = n
                    "jlz" -> if (acc < 0) nextIc = n
                    "jle" -> if (acc <= 0) nextIc = n
                    "jgz" -> if (acc > 0) nextIc = n
                    "jge" -> if (acc >= 0) nextIc = n
                    else -> throw IllegalStateException("Unknown command: $type")
                }

                ic = nextIc ?: (ic + 1)
            }
        }
    }
}

