package git.furkancetinkaya.calculator.data.format

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorNumberFormatterTest {

    private val formatter = CalculatorNumberFormatter()

    @Test
    fun removesBinaryFloatingPointNoise() {
        assertEquals("0.02", formatter.format(0.1 * 0.2))
        assertEquals("0.3", formatter.format(0.1 + 0.2))
        assertEquals("3.3", formatter.format(1.1 * 3.0))
        assertEquals("1", formatter.format(0.1 + 0.2 + 0.7))
    }

    @Test
    fun roundsToDisplayPrecision() {
        assertEquals("0.3333333333", formatter.format(1.0 / 3.0))
        assertEquals("0.6666666667", formatter.format(2.0 / 3.0))
        assertEquals("1.414213562", formatter.format(kotlin.math.sqrt(2.0)))
    }

    @Test
    fun stripsTrailingZeros() {
        assertEquals("2", formatter.format(2.0))
        assertEquals("100", formatter.format(100.0))
        assertEquals("0.0001", formatter.format(0.0001))
        assertEquals("0", formatter.format(-0.0))
    }

    @Test
    fun usesScientificNotationForExtremeValues() {
        assertEquals("1E+10", formatter.format(1.0E10))
        assertEquals("1.23456789E+10", formatter.format(12345678901.0))
        assertEquals("0.00001", formatter.format(1.0E-5))
        assertEquals("1E-7", formatter.format(1.0E-7))
    }
}
