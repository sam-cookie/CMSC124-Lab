package scanner

// Expressions
interface Expr

data class BinaryExpr(
    val left: Expr,
    val operator: Token,
    val right: Expr
) : Expr

data class UnaryExpr(
    val operator: Token,
    val right: Expr
) : Expr

data class LiteralExpr(
    val value: Any?
) : Expr

data class GroupingExpr(
    val expression: Expr
) : Expr

data class VariableExpr(
    val name: Token
) : Expr

data class AssignExpr(
    val name: Token,
    val value: Expr
) : Expr

data class LogicalExpr(
    val left: Expr,
    val operator: Token,
    val right: Expr
) : Expr

data class CallExpr(
    val callee: Expr,
    val paren: Token,
    val arguments: List<Expr>
) : Expr