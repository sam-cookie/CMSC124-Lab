enum class TokenType {

    // single token
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, PLUS, MINUS, TIMES, DIVIDE, EQUALS, 
    COMMA, DASH, COLON, LESS_THAN_EQUAL, GREATER_THAN_EQUAL, LESS_THAN, GREATER_THAN, EQUALTO, DOUBLE_QUOTE,

    // literals 
    IDENTIFIER, NUMBER, STRING,

    // keywords
    VAR, 

    EOF
}

data class Token(
    val type: TokenType,
    val lexeme: String,
    val literal: Any? 
    val line: Int
)

class Scanner { 
    print function 
    shibal 
}