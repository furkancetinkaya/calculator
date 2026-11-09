package git.furkancetinkaya.calculator.domain.math

/**
 * Outcome of evaluating a calculator expression.
 */
sealed interface EvaluationResult {

    /** A finite numeric result, already formatted for display. */
    data class Value(val text: String) : EvaluationResult

    /** The expression is valid but has no real finite result. */
    data object Undefined : EvaluationResult

    /** Evaluation failed unexpectedly, for example because the engine crashed. */
    data class Failure(val cause: Throwable) : EvaluationResult
}
