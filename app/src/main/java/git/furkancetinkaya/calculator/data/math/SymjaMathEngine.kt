package git.furkancetinkaya.calculator.data.math

import git.furkancetinkaya.calculator.domain.math.MathEngine
import org.matheclipse.core.eval.ExprEvaluator
import org.matheclipse.parser.client.ParserConfig
import javax.inject.Inject

/**
 * Evaluates calculator expressions with the Symja computer algebra engine.
 *
 * The keypad uses a few symbols that differ from Symja's Mathematica-like
 * input syntax, so [toSymjaExpression] translates them before evaluation:
 *
 *  - `°` is the degree symbol, i.e. `Degree` in Symja
 *  - `%` is modulo, i.e. the `Mod` function in Symja
 *  - `rnd` is an alias for `Round`
 *  - `ceil` is an alias for `Ceiling`
 *  - `e` is Euler's number, which Symja spells `E`
 */
class SymjaMathEngine @Inject constructor() : MathEngine {

    private val evaluator: ExprEvaluator by lazy {
        // The calculator inserts explicit operators, and this also makes
        // Symja read `1E6` as scientific notation instead of `1*E*6`.
        ParserConfig.EXPLICIT_TIMES_OPERATOR = true
        ExprEvaluator()
    }

    private val eulerToken = Regex("(?<![\\p{L}\\p{N}_.])e(?![\\p{L}\\p{N}_])")
    private val roundAlias = Regex("\\brnd\\b")
    private val ceilAlias = Regex("\\bceil\\b")

    /**
     * Loads and initializes the Symja evaluator ahead of the first user
     * calculation. Safe to call more than once.
     */
    override fun warmUp() {
        // Touches the parser and the most common operators/functions so their
        // classes are loaded during startup instead of the first calculation.
        evaluate("1+2*3^2-4/2+sqrt(4)+sin(30°)+log10(100)+2!")
    }

    @Synchronized
    override fun evaluate(expression: String): Double? {
        val input = toSymjaExpression(expression)
        if (input.isBlank()) {
            return null
        }

        return try {
            evaluator.evalf(input).takeIf { it.isFinite() }
        } catch (_: Exception) {
            null
        }
    }

    private fun toSymjaExpression(expression: String): String {
        return expression
            .replace("°", "*Degree")
            .replace("%", "~Mod~")
            .replace(roundAlias, "Round")
            .replace(ceilAlias, "Ceiling")
            .replace(eulerToken, "E")
    }
}
