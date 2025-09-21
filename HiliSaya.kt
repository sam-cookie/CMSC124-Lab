enum class TokenType {

    // single token
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, PLUS, MINUS, TIMES, DIVIDE, EQUALS, 
    COMMA, DASH, COLON, LESS_THAN_EQUAL, GREATER_THAN_EQUAL, LESS_THAN, GREATER_THAN, EQUALTO, DOUBLE_QUOTE,

    // literals 
    IDENTIFIER, NUMBER, STRING,

    // keywords
    VAR,

    // Comments
    EOF
}

data class Token(
    val type: TokenType,
    val lexeme: String,
    val literal: Any?, 
    val line: Int
)


fun scanToken(source: String, index: Int): Pair<TokenType?, Int> {
    val c = source[index]
    val next = if (index + 1 < source.length) source[index + 1] else '\u0000'

    return if (c == '(') {
        TokenType.LEFT_PAREN to 1
    } else if (c == ')') {
        TokenType.RIGHT_PAREN to 1
    } else if (c == '{') {
        TokenType.LEFT_BRACE to 1
    } else if (c == '}') {
        TokenType.RIGHT_BRACE to 1
    } else if (c == '+') {
        TokenType.PLUS to 1
    } else if (c == '-') {
        TokenType.MINUS to 1
    } else if (c == '*') {
        TokenType.TIMES to 1
    } else if (c == '/') {
        TokenType.DIVIDE to 1
    } else if (c == '=') {
        if (next == '=') TokenType.EQUALTO to 2
        else TokenType.EQUALS to 1
    } else if (c == ',') {
        TokenType.COMMA to 1
    } else if (c == ':') {
        TokenType.COLON to 1
    } else if (c == '<') {
        if (next == '=') TokenType.LESS_THAN_EQUAL to 2
        else TokenType.LESS_THAN to 1
    } else if (c == '>') {
        if (next == '=') TokenType.GREATER_THAN_EQUAL to 2
        else TokenType.GREATER_THAN to 1
    } else if (c == '"') { //di ko pa to naayos di ko gets? 
        TokenType.DOUBLE_QUOTE to 1
    } else {
        null to 1 
    }
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

        val (type, length) = scanToken(source, i)
        if (type != null) {
            val lexeme = source.substring(i, i + length)
            tokens.add(Token(type, lexeme, null, line))
        }
        i += length
    }

    for (token in tokens) {
        println(token)
    }
}