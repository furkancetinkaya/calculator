package git.furkancetinkaya.calculator.ui.calculator

import git.furkancetinkaya.calculator.domain.input.CalculatorKey
import git.furkancetinkaya.calculator.domain.input.CalculatorKeyType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculatorKeyLabelTest {

    @Test
    fun plainKeysUseTheirValueAsLabel() {
        assertEquals("7", CalculatorKey.KEY_NUM7.displayLabel())
        assertEquals(".", CalculatorKey.KEY_DOT.displayLabel())
        assertEquals("π", CalculatorKey.KEY_PI.displayLabel())
        assertEquals("e", CalculatorKey.KEY_EULER.displayLabel())
        assertEquals("=", CalculatorKey.KEY_RESULT.displayLabel())
    }

    @Test
    fun actionAndOperatorKeysUseReadableLabels() {
        assertEquals("AC", CalculatorKey.KEY_AC.displayLabel())
        assertEquals("DEL", CalculatorKey.KEY_DEL.displayLabel())
        assertEquals("ALT", CalculatorKey.KEY_ALT.displayLabel())
        assertEquals("INV", CalculatorKey.KEY_INV.displayLabel())
        assertEquals("×", CalculatorKey.KEY_MUL.displayLabel())
        assertEquals("/", CalculatorKey.KEY_DIV.displayLabel())
        assertEquals("−", CalculatorKey.KEY_MINUS.displayLabel())
    }

    @Test
    fun standardFunctionsUseCallNotation() {
        assertEquals("sqrt()", CalculatorKey.KEY_SQRT.displayLabel())
        assertEquals("ln()", CalculatorKey.KEY_LN.displayLabel())
        assertEquals("rnd()", CalculatorKey.KEY_ROUND.displayLabel())
        assertEquals("abs()", CalculatorKey.KEY_ABS.displayLabel())
        assertEquals("floor()", CalculatorKey.KEY_FLOOR.displayLabel())
        assertEquals("ceil()", CalculatorKey.KEY_CEIL.displayLabel())
    }

    @Test
    fun trigonometricFunctionsUseCallNotation() {
        assertEquals("sin()", CalculatorKey.KEY_SIN.displayLabel())
        assertEquals("cos()", CalculatorKey.KEY_COS.displayLabel())
        assertEquals("tan()", CalculatorKey.KEY_TAN.displayLabel())
        assertEquals("sinh()", CalculatorKey.KEY_SINH.displayLabel())
        assertEquals("cosh()", CalculatorKey.KEY_COSH.displayLabel())
        assertEquals("tanh()", CalculatorKey.KEY_TANH.displayLabel())
    }

    @Test
    fun inverseTrigonometricFunctionsUseSuperscriptMinusOne() {
        assertEquals("sin⁻¹()", CalculatorKey.KEY_ARCSIN.displayLabel())
        assertEquals("cos⁻¹()", CalculatorKey.KEY_ARCCOS.displayLabel())
        assertEquals("tan⁻¹()", CalculatorKey.KEY_ARCTAN.displayLabel())
        assertEquals("sinh⁻¹()", CalculatorKey.KEY_ARCSINH.displayLabel())
        assertEquals("cosh⁻¹()", CalculatorKey.KEY_ARCCOSH.displayLabel())
        assertEquals("tanh⁻¹()", CalculatorKey.KEY_ARCTANH.displayLabel())

        assertFalse(CalculatorKey.KEY_ARCSIN.displayLabel().contains("^"))
    }

    @Test
    fun logarithmsUseSubscriptBases() {
        assertEquals("log₁₀()", CalculatorKey.KEY_LOG10.displayLabel())
        assertEquals("log₂()", CalculatorKey.KEY_LOG2.displayLabel())
    }

    @Test
    fun everyFunctionLabelEndsWithParentheses() {
        CalculatorKey.entries
            .filter { it.type == CalculatorKeyType.STD_FUNCTION || it.type == CalculatorKeyType.TRIG_FUNCTION }
            .forEach { key ->
                assertTrue(key.name, key.displayLabel().endsWith("()"))
            }
    }

    @Test
    fun labelsDoNotChangeInsertedTokens() {
        assertEquals("arcsin", CalculatorKey.KEY_ARCSIN.value)
        assertEquals("log10", CalculatorKey.KEY_LOG10.value)
        assertEquals("log2", CalculatorKey.KEY_LOG2.value)
        assertEquals("*", CalculatorKey.KEY_MUL.value)
        assertEquals("/", CalculatorKey.KEY_DIV.value)
        assertEquals("-", CalculatorKey.KEY_MINUS.value)
    }
}
