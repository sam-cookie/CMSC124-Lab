package scanner

import scanner.Token
import scanner.TokenType

// ===== ABSTRACT SYNTAX TREE (AST) NODES =====
sealed class ASTNode {
    // --- Expressions ---
    sealed class Expr : ASTNode() {
        data class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr()
        data class Unary(val operator: Token, val right: Expr) : Expr()
        data class Literal(val value: Any?) : Expr()
        data class Grouping(val expression: Expr) : Expr()
        data class Variable(val name: Token) : Expr()
        data class Assignment(val name: Token, val value: Expr) : Expr()
        data class Logical(val left: Expr, val operator: Token, val right: Expr) : Expr()
        data class Call(val callee: Expr, val arguments: List<Expr>) : Expr()
        data class ArrayLiteral(val elements: List<Expr>) : Expr()
    }

    // --- Statements ---
    sealed class Stmt : ASTNode() {
        data class VarDeclaration(val name: Token, val initializer: Expr?) : Stmt()
        data class ExpressionStmt(val expression: Expr) : Stmt()
        data class PrintStmt(val expression: Expr) : Stmt()
        data class Block(val statements: List<Stmt>) : Stmt()
        data class IfStmt(
            val condition: Expr,
            val thenBranch: List<Stmt>,
            val elifClauses: List<ElifClause>,
            val elseBranch: List<Stmt>?
        ) : Stmt()
        data class ElifClause(val condition: Expr, val body: List<Stmt>)
        data class WhileStmt(val condition: Expr, val body: List<Stmt>) : Stmt()
        data class ForStmt(
            val init: Stmt?,
            val condition: Expr?,
            val increment: Stmt?,
            val body: List<Stmt>
        ) : Stmt()
        data class FunctionDeclaration(
            val name: Token,
            val params: List<Token>,
            val body: List<Stmt>
        ) : Stmt()
        data class ReturnStmt(val keyword: Token, val value: Expr?) : Stmt()
        object Break : Stmt()
        object Continue : Stmt()
    }
}