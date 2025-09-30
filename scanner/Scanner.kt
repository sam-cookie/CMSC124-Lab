package scanner

class Scanner(source: String, index:Int, line:Int, length:Int) { 
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

    fun scanLiterals(source: String, index: Int, line: Int): Pair<TokenType?, Int> {
        val c = source[index]

        // number literal
        if (c.isDigit()) {
            var length = 1
            while (index + length < source.length && source[index + length].isDigit()) {
                length++
            }

            if (index + length < source.length && source[index + length] == '.') {
                if (index + length + 1 < source.length && source[index + length + 1].isDigit()) {
                    length++
                    while (index + length < source.length && source[index + length].isDigit()) {
                        length++
                    }
                }
            }

            val numStart = source.substring(index, index + length)
            if (index + length < source.length && (source[index + length].isLetter() || source[index + length] == '_')) {
                println("error with starting number '$numStart' in identifier name at line $line")
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

                if (index + length < source.length) {
                    val specialChar = source[index + length]
                    if (!specialChar.isWhitespace() && !specialChar.isLetterOrDigit() && specialChar != '_') {
                        println("error '$specialChar' at line $line")
                    }
                }
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
                if (index + length < source.length && source[index + length] == '"') {
                    length++
                    return TokenType.STRING to length
                } else {
                    println("error: string literal at line $line does not end with a double quote")
                    return null to 1
                }
            }

        return null to 1
    }

    fun scanOtherCharacters(source: String, startIndex: Int, startLine: Int, length: Int) {
        var index = startIndex
        var line = startLine
        val tokens = mutableListOf<Token>()
        while (index < source.length) {
            val c = source[index]

            if (c == ' ' || c == '\r' || c == '\t') {
                index++
                continue
            }

            if (c == '\n') {
                line++
                index++
                continue
            }

            if (index + 2 < source.length && source.substring(index, index + 3) == "///") {
                val closing = source.indexOf("///", index + 3)
                val endComment = if (closing != -1) closing + 3 else source.length
                val lexeme = source.substring(index, endComment)
                line += lexeme.count { it == '\n' }
                index = endComment
                continue
            }

            if (index + 1 < source.length && source.substring(index, index + 2) == "//") {
                val closing = source.indexOf("//", index + 2)
                val endComment = if (closing != -1) closing + 2 else source.length
                val lexeme = source.substring(index, endComment)
                index = endComment
                continue
            }

            if (isLiteral(source, index, line, tokens)) {
                continue
            }
            if (isSymbol(source, index, line, tokens)) {
                continue
            }

            index++
        }

        tokens.add(Token(TokenType.EOF, "", null, line))

        for (token in tokens) {
            println(token)
        }
    }

    fun isLiteral(source: String, index: Int, line: Int, tokens: MutableList<Token>): Boolean {
        val (litType, litLen) = scanLiterals(source, index, line)
        if (litType != null) {
            val lexeme = source.substring(index, index + litLen)
            val literal = when (litType) {
                TokenType.NUMBER -> lexeme.toDouble()
                TokenType.STRING -> lexeme.substring(1, lexeme.length - 1)
                else -> null
            }
            tokens.add(Token(litType, lexeme, literal, line))
            return true
        }
        return false
    }

    fun isSymbol(source: String, index: Int, line: Int, tokens: MutableList<Token>): Boolean {
        val (symType, symLen) = scanToken(source, index)
        if (symType != null) {
            val lexeme = source.substring(index, index + symLen)
            tokens.add(Token(symType, lexeme, null, line))
            return true
        }
        return false
    }
}