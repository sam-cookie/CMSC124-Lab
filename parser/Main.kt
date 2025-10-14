package parser
import scanner.*
import parser.*

fun main() {
    println(" HiliSaya Parser  ")

    while (true) {
        print("> ")
        val line = readlnOrNull() ?: break
        if (line.trim() == "humana") break
        if (line.isBlank()) continue

        val scanner = Scanner(line)
        val tokens = scanner.scanTokens()


        val parser = Parser(tokens)
        val expression = parser.parse()

        println(printAst(expression))
    }
}
