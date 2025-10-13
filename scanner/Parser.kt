package parser

class Parser(private val tokens: List<Token>) {

    private var current = 0

    // Entry point: parse a program
    fun parse(): List<Stmt> {
        val statements = mutableListOf<Stmt>()
        while (!isAtEnd()) {
            statements.add(declaration())
        }
        return statements
    }

    // Parse declarations (like `bar x = 5;` or `func foo() ...`)
    private fun declaration(): Stmt {
        // TODO: implement according to your grammar
        return statement()
    }

    // Parse statements
    private fun statement(): Stmt {
        // TODO: handle printing, if, while, etc.
        return expressionStatement()
    }

    private fun expressionStatement(): Stmt {
        val expr = expression()
        return ExpressionStmt(expr)
    }

    // Parse expressions
    private fun expression(): Expr {
        // TODO: implement expression grammar (logical_or, equality, etc.)
        return LiteralExpr(null)
    }

    // Utility functions
    private fun match(vararg types: TokenType): Boolean {
        for (type in types) {
            if (check(type)) {
                advance()
                return true
            }
        }
        return false
    }

    private fun check(type: TokenType): Boolean {
        if (isAtEnd()) return false
        return peek().type == type
    }

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun isAtEnd(): Boolean = peek().type == TokenType.EOF
    private fun peek(): Token = tokens[current]
    private fun previous(): Token = tokens[current - 1]
}
