package git.furkancetinkaya.calculator.domain.math

/**
 * Formats a numeric result for the calculator display.
 */
interface NumberFormatter {
    fun format(value: Double): String
}
