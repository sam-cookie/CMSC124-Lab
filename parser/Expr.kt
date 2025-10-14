package parser
import scanner.*

sealed class Expr {
    data class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr()
    data class Unary(val operator: Token, val right: Expr) : Expr()
    data class Literal(val value: Any?) : Expr()
    data class Grouping(val expression: Expr) : Expr()
}

fun printAst(expr: Expr): String = when (expr) {
    is Expr.Binary -> "(${expr.operator.lexeme} ${printAst(expr.left)} ${printAst(expr.right)})"
    is Expr.Unary -> "(${expr.operator.lexeme} ${printAst(expr.right)})"
    is Expr.Literal -> expr.value.toString()
    is Expr.Grouping -> "(group ${printAst(expr.expression)})"
}
