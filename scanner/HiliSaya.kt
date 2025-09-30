package scanner

fun main() {
    print("Check your input: ")
    val source = readLine() ?: ""

    val tokens = mutableListOf<Token>()
    var i = 0
    var line = 1

    while (i < source.length) {
        val c = source[i]

        if (c == ' ' || c == '\r' || c == '\t') {
            i++
            continue
        }
        if (c == '\n') {
            line++
            i++
            continue
        }

        if (i + 2 < source.length && source.substring(i, i + 3) == "///") {
            val closing = source.indexOf("///", i + 3)
            val endComment = if (closing != -1) closing + 3 else source.length
            val lexeme = source.substring(i, endComment)
            line += lexeme.count { it == '\n' }
            i = endComment
            continue
        }

        if (i + 1 < source.length && source.substring(i, i + 2) == "//") {
            val closing = source.indexOf("//", i + 2)
            val endComment = if (closing != -1) closing + 2 else source.length
            val lexeme = source.substring(i, endComment)
            i = endComment
            continue
        }

        val (litType, litLen) = scanLiterals(source, i, line)
        if (litType != null) {
            val lexeme = source.substring(i, i + litLen)
            val literal = when (litType) {
                TokenType.NUMBER -> lexeme.toDouble()
                TokenType.STRING -> lexeme.substring(1, lexeme.length - 1)
                else -> null
            }
            tokens.add(Token(litType, lexeme, literal, line))
            i += litLen
            continue
        }

        val (symType, symLen) = scanToken(source, i)
        if (symType != null) {
            val lexeme = source.substring(i, i + symLen)
            tokens.add(Token(symType, lexeme, null, line))
        }
        i += symLen
    }

    tokens.add(Token(TokenType.EOF, "", null, line))

    for (token in tokens) {
        println(token)
    }
}
