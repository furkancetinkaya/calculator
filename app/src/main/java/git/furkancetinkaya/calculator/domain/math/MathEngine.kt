package git.furkancetinkaya.calculator.domain.math

/**
 * Evaluates calculator expressions independently of the mathematical engine.
 */
interface MathEngine {

    /**
     * Loads and initializes the underlying engine before the first user
     * calculation. Safe to call more than once.
     */
    fun warmUp()

    /**
     * Evaluates [expression] and returns its numeric value, or `null` when the
     * expression is invalid or has no real finite result.
     */
    fun evaluate(expression: String): Double?
}
