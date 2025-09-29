enum class TokenType {

    // single token
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, PLUS, MINUS, TIMES, DIVIDE, EQUALS, 
    COMMA, DASH, COLON, LESS_THAN_EQUAL, GREATER_THAN_EQUAL, LESS_THAN, GREATER_THAN, EQUALTO, DOUBLE_QUOTE,

    // literals 
    IDENTIFIER, NUMBER, STRING,

    // keywords
    VAR, CLASS, FUNCTION, RETURN, PRINT,

    //End
    EOF
}

data class Token(
    val type: TokenType,
    val lexeme: String,
    val literal: Any?, 
    val line: Int
)

val keywords = mapOf (
        "var" to TokenType.VAR,
        "class" to TokenType.CLASS,
        "func" to TokenType.FUNCTION,
        "balik" to TokenType.RETURN,
        "gawas" to TokenType.PRINT

    )


fun scanToken(source: String, index: Int): Pair<TokenType?, Int> {
    val c = source[index]
    val next = if (index + 1 < source.length) source[index + 1] else '\u0000'

    return when (c) {
        '(' -> TokenType.LEFT_PAREN to 1
        ')' -> TokenType.RIGHT_PAREN to 1
        '{' -> TokenType.LEFT_BRACE to 1
        '}' -> TokenType.RIGHT_BRACE to 1
        '+' -> TokenType.PLUS to 1
        '-' -> TokenType.MINUS to 1
        '*' -> TokenType.TIMES to 1
        '/' -> TokenType.DIVIDE to 1
        '=' -> if (next == '=') TokenType.EQUALTO to 2 else TokenType.EQUALS to 1
        ',' -> TokenType.COMMA to 1
        ':' -> TokenType.COLON to 1
        '<' -> if (next == '=') TokenType.LESS_THAN_EQUAL to 2 else TokenType.LESS_THAN to 1
        '>' -> if (next == '=') TokenType.GREATER_THAN_EQUAL to 2 else TokenType.GREATER_THAN to 1
        '"' -> TokenType.DOUBLE_QUOTE to 1 // still placeholder for string handling
        else -> null to 1
    }
}


fun scanLiterals(source: String, index: Int): Pair<TokenType?, Int> {
    val c = source[index]

    // number literal
    if (c.isDigit()) {
        var length = 1
        while (index + length < source.length && source[index + length].isDigit()) {
            length++
        }
        return TokenType.NUMBER to length
    }

    // identifier or keyword
    if (c.isLetter() || c == '_') {
        var length = 1
        while (index + length < source.length &&
            (source[index + length].isLetterOrDigit() || source[index + length] == '_')
        ) {
            length++
        }
        val lexeme = source.substring(index, index + length)
        val type = keywords[lexeme] ?: TokenType.IDENTIFIER
        return type to length
    }

    // string literal
    if (c == '"') {
        var length = 1
        while (index + length < source.length && source[index + length] != '"') {
            length++
        }
        return if (index + length < source.length) {
            length++ // include closing "
            TokenType.STRING to length
        } else {
            // unterminated string
            null to 1
        }
    }

    return null to 1
}

fun main() {
    val source = readLine() ?: ""

    val tokens = mutableListOf<Token>()
    var i = 0
    var line = 1

    while (i < source.length) {
        val c = source[i]

        // whitespace handling
        if (c == ' ' || c == '\r' || c == '\t') {
            i++
            continue
        }
        if (c == '\n') {
            line++
            i++
            continue
        }

        // block comments
        if (i + 2 < source.length && source.substring(i, i + 3) == "///") {
            val closing = source.indexOf("///", i + 3) 
            val endComment = if (closing != -1) closing + 3 else source.length

            val lexeme = source.substring (i, endComment)

            // count new lines
            line += lexeme.count {it == '\n'}

            i = endComment
            continue
        }

         // line comments
        if (i + 1 < source.length && source.substring(i, i + 2) == "//") {
            val closing = source.indexOf("//", i + 2) 
            val endComment = if (closing != -1) closing + 2 else source.length

            val lexeme = source.substring (i, endComment)

            i = endComment
            continue
        }

        val (litType, litLen) = scanLiterals(source, i)
        if (litType != null) {
            val lexeme = source.substring(i, i + litLen)
            tokens.add(Token(litType, lexeme, null, line))
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

    // print results
    for (token in tokens) {
        println(token)
    }
}