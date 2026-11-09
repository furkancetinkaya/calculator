package git.furkancetinkaya.calculator.ui.calculator

import git.furkancetinkaya.calculator.domain.input.CalculatorKey
import git.furkancetinkaya.calculator.domain.input.CalculatorKeyType

/**
 * Text shown on a keypad button.
 */
internal fun CalculatorKey.displayLabel(): String = when (this) {
    CalculatorKey.KEY_AC -> "AC"
    CalculatorKey.KEY_DEL -> "DEL"
    CalculatorKey.KEY_ALT -> "ALT"
    CalculatorKey.KEY_INV -> "INV"

    CalculatorKey.KEY_MUL -> "×"
    CalculatorKey.KEY_DIV -> "/"
    CalculatorKey.KEY_MINUS -> "−"

    CalculatorKey.KEY_SQRT -> "sqrt()"
    CalculatorKey.KEY_LOG10 -> "log₁₀()"
    CalculatorKey.KEY_LOG2 -> "log₂()"
    CalculatorKey.KEY_LN -> "ln()"
    CalculatorKey.KEY_ROUND -> "rnd()"
    CalculatorKey.KEY_ABS -> "abs()"
    CalculatorKey.KEY_FLOOR -> "floor()"
    CalculatorKey.KEY_CEIL -> "ceil()"

    CalculatorKey.KEY_SIN -> "sin()"
    CalculatorKey.KEY_COS -> "cos()"
    CalculatorKey.KEY_TAN -> "tan()"
    CalculatorKey.KEY_SINH -> "sinh()"
    CalculatorKey.KEY_COSH -> "cosh()"
    CalculatorKey.KEY_TANH -> "tanh()"

    CalculatorKey.KEY_ARCSIN -> "sin⁻¹()"
    CalculatorKey.KEY_ARCCOS -> "cos⁻¹()"
    CalculatorKey.KEY_ARCTAN -> "tan⁻¹()"
    CalculatorKey.KEY_ARCSINH -> "sinh⁻¹()"
    CalculatorKey.KEY_ARCCOSH -> "cosh⁻¹()"
    CalculatorKey.KEY_ARCTANH -> "tanh⁻¹()"

    else -> when (type) {
        CalculatorKeyType.STD_FUNCTION, CalculatorKeyType.TRIG_FUNCTION -> "$value()"
        else -> value
    }
}
