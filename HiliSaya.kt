enum class TokenType {

    // single token and multicharacter tokens 
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, PLUS, MINUS, TIMES, DIVIDE, MODULO, EQUALS,
    COMMA, COLON, NOT, AND, OR, LESS_THAN_EQUAL, GREATER_THAN_EQUAL, LESS_THAN, GREATER_THAN, EQUALTO, NOT_EQUAL, DOUBLE_QUOTE,
    ADD_ASSIGN, MINUS_ASSIGN, TIMES_ASSIGN, DIVIDED_ASSIGN, MODULO_ASSIGN

    // literals 
    IDENTIFIER, NUMBER, STRING, 

    // keywords
    VAR, FUNCTION, RETURN, PRINT, IF, ELSE, WHILE, BREAK, CONTINUE, TRUE, FALSE, NULL,

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
        "func" to TokenType.FUNCTION,
        "balik" to TokenType.RETURN,
        "gawas" to TokenType.PRINT,
        "kung" to TokenType.IF,
        "ugdi" to TokenType.ELSE,
        "samtang" to TokenType.WHILE,
        "untat" to TokenType.BREAK,
        "padayon" to TokenType.CONTINUE,
        "tuod" to TokenType.TRUE,
        "atik" to TokenType.FALSE,
        "waay" to TokenType.NULL
    )


fun scanToken(source: String, index: Int): Pair<TokenType?, Int> {
    val c = source[index]
    val next = if (index + 1 < source.length) source[index + 1] else '\u0000'

    return when (c) {
        '(' -> TokenType.LEFT_PAREN to 1
        ')' -> TokenType.RIGHT_PAREN to 1
        '{' -> TokenType.LEFT_BRACE to 1
        '}' -> TokenType.RIGHT_BRACE to 1
        '+' -> if (next == '=') TokenType.ADD_ASSIGN to 2 else TokenType.PLUS to 1
        '-' -> if (next == '=') TokenType.MINUS_ASSIGN to 2 else TokenType.MINUS to 1
        '*' -> if (next == '=') TokenType.TIMES_ASSIGN to 2 else TokenType.TIMES to 1
        '/' -> if (next == '=') TokenType.DIVIDED_ASSIGN to 2 else TokenType.DIVIDE to 1
        '%' -> if (next == '=') TokenType.MODULO_ASSIGN to 2 else TokenType.MODULO to 1
        '=' -> if (next == '=') TokenType.EQUALTO to 2 else TokenType.EQUALS to 1
        '!' -> if (next == '=') TokenType.NOT_EQUAL to 2 else TokenType.NOT to 1
        '&' -> if (next == '&') TokenType.AND to 2 else null to 1
        '|' -> if (next == '|') TokenType.OR to 2 else null to 1
        ',' -> TokenType.COMMA to 1
        ':' -> TokenType.COLON to 1
        '<' -> if (next == '=') TokenType.LESS_THAN_EQUAL to 2 else TokenType.LESS_THAN to 1
        '>' -> if (next == '=') TokenType.GREATER_THAN_EQUAL to 2 else TokenType.GREATER_THAN to 1
        '"' -> TokenType.DOUBLE_QUOTE to 1
        else -> null to 1
    }
}



fun scanLiterals(source: String, index: Int): Pair<TokenType?, Int> {
    val c = source[index]

    // number literal (supports decimals)
    if (c.isDigit()) {
        var length = 1
        var hasDot = false

        while (index + length < source.length) {
            val ch = source[index + length]
            if (ch.isDigit()) {
                length++
            } else if (ch == '.' && !hasDot && index + length + 1 < source.length && source[index + length + 1].isDigit()) {
                hasDot = true
                length++
            } else {
                break
            }
        }

        val lexeme = source.substring(index, index + length)
        val value = lexeme.toDoubleOrNull()
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
            val lexeme = source.substring(index, index + length)
            val literalValue = lexeme.substring(1, lexeme.length - 1) // strip quotes
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

            val lexeme = source.substring(i, endComment)

            // count new lines
            line += lexeme.count { it == '\n' }

            i = endComment
            continue
        }

        // line comments
        if (i + 1 < source.length && source.substring(i, i + 2) == "//") {
            val closing = source.indexOf("//", i + 2)
            val endComment = if (closing != -1) closing + 2 else source.length

            val lexeme = source.substring(i, endComment)

            i = endComment
            continue
        }

        val (litType, litLen) = scanLiterals(source, i)
        if (litType != null) {
            val lexeme = source.substring(i, i + litLen)

            val literal = when (litType) {
                TokenType.NUMBER -> lexeme.toDoubleOrNull()
                TokenType.STRING -> lexeme.substring(1, lexeme.length - 1) // remove quotes
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
        println("Token(type=${token.type}, lexeme=${token.lexeme}, literal=${token.literal}, line=${token.line})")
    }
}
