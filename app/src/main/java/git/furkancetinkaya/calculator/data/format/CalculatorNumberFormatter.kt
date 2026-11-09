package git.furkancetinkaya.calculator.data.format

import git.furkancetinkaya.calculator.domain.math.NumberFormatter
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject

/**
 * Formats a computed [Double] for the calculator display.
 *
 * Binary floating point cannot represent most decimal fractions exactly, so an
 * expression such as `0.1 * 0.2` evaluates to `0.020000000000000004`. Rounding
 * to [SIGNIFICANT_DIGITS] significant decimal digits removes that noise while
 * keeping more precision than the display can comfortably show.
 *
 * Trailing zeros are stripped, and values with very large or small magnitudes
 * are shown in scientific notation (for example `1E+12`).
 */
class CalculatorNumberFormatter @Inject constructor() : NumberFormatter {

    private companion object {
        const val SIGNIFICANT_DIGITS = 10
        const val MIN_PLAIN_EXPONENT = -6
        const val MAX_PLAIN_EXPONENT = 9

        val mathContext = MathContext(SIGNIFICANT_DIGITS, RoundingMode.HALF_UP)
    }

    override fun format(value: Double): String {
        if (!value.isFinite()) {
            return value.toString()
        }
        if (value == 0.0) {
            return "0"
        }

        val rounded = BigDecimal(value.toString())
            .round(mathContext)
            .stripTrailingZeros()

        val exponent = rounded.precision() - rounded.scale() - 1
        return if (exponent in MIN_PLAIN_EXPONENT..MAX_PLAIN_EXPONENT) {
            rounded.toPlainString()
        } else {
            rounded.toString()
        }
    }
}
