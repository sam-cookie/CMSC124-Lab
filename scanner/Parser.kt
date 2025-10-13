package scanner

// ===== PARSER CLASS =====
class Parser(private val tokens: List<Token>) {

    private var current = 0

    // --- Entry point ---
    fun parse(): List<ASTNode.Stmt> {
        val statements = mutableListOf<ASTNode.Stmt>()
        // TODO: implement main parsing loop
        return statements
    }

    // ======== STATEMENTS ========
    private fun statement(): ASTNode.Stmt? { return null }

    private fun simpleStmt(): ASTNode.Stmt? { return null }
    private fun compoundStmt(): ASTNode.Stmt? { return null }

    private fun varDeclaration(): ASTNode.Stmt.VarDeclaration? { return null }
    private fun printStmt(): ASTNode.Stmt.PrintStmt? { return null }
    private fun breakStmt(): ASTNode.Stmt.Break? { return null }
    private fun continueStmt(): ASTNode.Stmt.Continue? { return null }
    private fun returnStmt(): ASTNode.Stmt.ReturnStmt? { return null }

    private fun functionDeclaration(): ASTNode.Stmt.FunctionDeclaration? { return null }
    private fun ifStmt(): ASTNode.Stmt.IfStmt? { return null }
    private fun elifClause(): ASTNode.Stmt.ElifClause? { return null }
    private fun elseClause(): List<ASTNode.Stmt>? { return null }
    private fun whileStmt(): ASTNode.Stmt.WhileStmt? { return null }
    private fun forStmt(): ASTNode.Stmt.ForStmt? { return null }

    // ======== EXPRESSIONS ========
    private fun expression(): ASTNode.Expr? { return null }
    private fun logicalOr(): ASTNode.Expr? { return null }
    private fun logicalAnd(): ASTNode.Expr? { return null }
    private fun equality(): ASTNode.Expr? { return null }
    private fun comparison(): ASTNode.Expr? { return null }
    private fun term(): ASTNode.Expr? { return null }
    private fun factor(): ASTNode.Expr? { return null }
    private fun unary(): ASTNode.Expr? { return null }
    private fun primary(): ASTNode.Expr? { return null }

    private fun arrayLiteral(): ASTNode.Expr.ArrayLiteral? { return null }
    private fun funcCall(callee: ASTNode.Expr): ASTNode.Expr.Call? { return null }

    private fun arguments(): List<ASTNode.Expr>? { return null }
    private fun parameters(): List<Token>? { return null }

    // ======== UTILITIES ========
    private fun match(vararg types: TokenType): Boolean { return false }
    private fun check(type: TokenType): Boolean { return false }
    private fun advance(): Token { return tokens[current++] }
    private fun isAtEnd(): Boolean { return current >= tokens.size }
    private fun peek(): Token { return tokens[current] }
    private fun previous(): Token { return tokens[current - 1] }
    private fun consume(type: TokenType, message: String): Token { return tokens[current++] }
    private fun error(token: Token, message: String) { /* TODO */ }
}