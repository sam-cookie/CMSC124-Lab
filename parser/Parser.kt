package parser
import scanner.*

class Parser(private val tokens: List<Token>) {
    private var current = 0

    fun parse(): Expr {
        return expression()
    }

    // expression -> logical_or
    private fun expression(): Expr {
        return logicalOr()
    }

    // logical_or -> logical_and { "||" logical_and }
    private fun logicalOr(): Expr {
        var expr = logicalAnd()
        while (match(TokenType.OR)) {
            val operator = previous()
            val right = logicalAnd()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    // logical_and -> equality { "&&" equality }
    private fun logicalAnd(): Expr {
        var expr = equality()
        while (match(TokenType.AND)) {
            val operator = previous()
            val right = equality()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    // equality -> comparison { "==" | "!=" comparison }
    private fun equality(): Expr {
        var expr = comparison()
        while (match(TokenType.EQUALTO, TokenType.NOT_EQUAL)) {
            val operator = previous()
            val right = comparison()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    // comparison -> term { ">" | ">=" | "<" | "<=" term }
    private fun comparison(): Expr {
        var expr = term()
        while (match(
                TokenType.GREATER_THAN, TokenType.GREATER_THAN_EQUAL,
                TokenType.LESS_THAN, TokenType.LESS_THAN_EQUAL
            )) {
            val operator = previous()
            val right = term()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    // term -> factor { "+" | "-" factor }
    private fun term(): Expr {
        var expr = factor()
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            val operator = previous()
            val right = factor()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    // factor -> unary { "*" | "/" | "%" unary }
    private fun factor(): Expr {
        var expr = unary()
        while (match(TokenType.TIMES, TokenType.DIVIDE, TokenType.MODULO)) {
            val operator = previous()
            val right = unary()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    // unary -> "!" | "-" | "+" unary | primary
    private fun unary(): Expr {
        if (match(TokenType.NOT, TokenType.MINUS, TokenType.PLUS)) {
            val operator = previous()
            val right = unary()
            return Expr.Unary(operator, right)
        }
        return primary()
    }

    // primary -> NUMBER | STRING | TRUE | FALSE | NULL | IDENTIFIER | "(" expression ")"
    private fun primary(): Expr {
        if (match(TokenType.NUMBER, TokenType.STRING, TokenType.TRUE, TokenType.FALSE, TokenType.NULL)) {
            return Expr.Literal(previous().literal)
        }

        if (match(TokenType.IDENTIFIER)) {
            return Expr.Literal(previous().lexeme)
        }

        if (match(TokenType.LEFT_PAREN)) {
            val expr = expression()
            consume(TokenType.RIGHT_PAREN, "Expect ')' after expression.")
            return Expr.Grouping(expr)
        }

        throw error(peek(), "Expect expression.")
    }

    private fun match(vararg types: TokenType): Boolean {
        for (type in types) {
            if (check(type)) {
                advance()
                return true
            }
        }
        return false
    }

    private fun consume(type: TokenType, message: String) {
        if (check(type)) { advance(); return }
        throw error(peek(), message)
    }

    private fun check(type: TokenType) = !isAtEnd() && peek().type == type
    private fun advance(): Token = tokens[current++]
    private fun isAtEnd() = peek().type == TokenType.EOF
    private fun peek(): Token = tokens[current]
    private fun previous(): Token = tokens[current - 1]
    private fun error(token: Token, message: String): RuntimeException {
        return RuntimeException("[line ${token.line}] Error at '${token.lexeme}': $message")
    }
}
