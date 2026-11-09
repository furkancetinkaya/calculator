package git.furkancetinkaya.calculator.domain.math

/**
 * Evaluates an expression and converts the result into a display-ready state.
 */
interface EvaluateExpression {
    suspend operator fun invoke(expression: String): EvaluationResult
}
