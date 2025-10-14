
package scanner

fun main() {
    while (true) {
        println("Check your input: ")
        val source = readLine() ?: ""

        val tokens = mutableListOf<Token>()
        var index = 0
        var line = 1
        var length = 0

        if (source.equals("")) {
            println("[Line $line] Error at end: Expect expression")
        } else if (source.equals("humana")) break
        

        val scanner = Scanner(source, index, line, length)
        scanner.scanOtherCharacters(source, index, line, length)

        
    }
}
