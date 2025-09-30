package scanner

fun main() {
    while (true) {
        print("Check your input: ")
        val source = readLine() ?: ""

        val tokens = mutableListOf<Token>()
        var index = 0
        var line = 1
        var length = 0
        val scanner = Scanner(source, index, line, length)
        scanner.scanOtherCharacters(source, index, line, length)

    }
}