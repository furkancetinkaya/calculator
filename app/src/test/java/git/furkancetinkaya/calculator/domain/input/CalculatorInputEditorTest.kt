package git.furkancetinkaya.calculator.domain.input

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculatorInputEditorTest {

    private fun state() = CalculatorInputEditor()

    private fun CalculatorInputEditor.enter(
        vararg keys: CalculatorKey,
        angleUnit: AngleUnit = AngleUnit.Degrees,
    ) {
        keys.forEach { insert(it, angleUnit) }
    }

    @Test
    fun deleteOnInitialZeroKeepsSingleZero() {
        val state = state()

        state.deleteLast()

        assertEquals(listOf(CalculatorKey.KEY_NUM0), state.tokens)
        assertEquals("0", state.expression())
    }

    @Test
    fun deleteOnSingleDigitResultFallsBackToZero() {
        val state = state()
        state.replaceWithResult(listOf(CalculatorKey.KEY_NUM4))

        state.deleteLast()

        assertEquals(listOf(CalculatorKey.KEY_NUM0), state.tokens)
    }

    @Test
    fun deleteRemovesFunctionAndItsParenthesis() {
        val state = state()
        state.enter(CalculatorKey.KEY_SIN)

        state.deleteLast()

        assertEquals(listOf(CalculatorKey.KEY_NUM0), state.tokens)
        assertFalse(state.hasOpenAngleFunction)
    }

    @Test
    fun deleteRemovesLeftParenthesis() {
        val state = state()
        state.enter(CalculatorKey.KEY_LPAREN)

        state.deleteLast()

        assertEquals(listOf(CalculatorKey.KEY_NUM0), state.tokens)
    }

    @Test
    fun deleteWalksBackThroughFunctionArguments() {
        val state = state()
        state.enter(CalculatorKey.KEY_SIN, CalculatorKey.KEY_NUM3, CalculatorKey.KEY_NUM0)

        state.deleteLast()
        assertEquals(listOf(CalculatorKey.KEY_SIN, CalculatorKey.KEY_NUM3), state.tokens)

        state.deleteLast()
        assertEquals(listOf(CalculatorKey.KEY_SIN), state.tokens)

        state.deleteLast()
        assertEquals(listOf(CalculatorKey.KEY_NUM0), state.tokens)
        assertFalse(state.hasOpenAngleFunction)
    }

    @Test
    fun closingTrigInDegreeAddsDegree() {
        val state = state()
        state.enter(CalculatorKey.KEY_SIN, CalculatorKey.KEY_NUM3, CalculatorKey.KEY_NUM0, CalculatorKey.KEY_RPAREN)

        assertEquals(
            listOf(
                CalculatorKey.KEY_SIN,
                CalculatorKey.KEY_NUM3,
                CalculatorKey.KEY_NUM0,
                CalculatorKey.KEY_DEGREE,
                CalculatorKey.KEY_RPAREN,
            ),
            state.tokens,
        )
        assertEquals("sin(30°)", state.expression())
        assertFalse(state.hasOpenAngleFunction)
    }

    @Test
    fun closingTrigInRadianDoesNotAddDegree() {
        val state = state()
        state.enter(
            CalculatorKey.KEY_SIN,
            CalculatorKey.KEY_NUM3,
            CalculatorKey.KEY_NUM0,
            CalculatorKey.KEY_RPAREN,
            angleUnit = AngleUnit.Radians,
        )

        assertEquals("sin(30)", state.expression())
        assertFalse(state.tokens.contains(CalculatorKey.KEY_DEGREE))
    }

    @Test
    fun closingPlainParenthesisInsideTrigWaitsForTrigClose() {
        val state = state()
        state.enter(
            CalculatorKey.KEY_SIN,
            CalculatorKey.KEY_LPAREN,
            CalculatorKey.KEY_NUM2,
            CalculatorKey.KEY_PLUS,
            CalculatorKey.KEY_NUM3,
            CalculatorKey.KEY_RPAREN,
        )

        assertEquals("sin((2+3)", state.expression())
        assertFalse(state.tokens.contains(CalculatorKey.KEY_DEGREE))
        assertTrue(state.hasOpenAngleFunction)

        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)

        assertEquals("sin((2+3)°)", state.expression())
        assertFalse(state.hasOpenAngleFunction)
    }

    @Test
    fun rightParenthesisIsIgnoredWhenNothingIsOpen() {
        val state = state()

        state.enter(CalculatorKey.KEY_RPAREN, CalculatorKey.KEY_RPAREN)

        assertEquals(listOf(CalculatorKey.KEY_NUM0), state.tokens)
        assertEquals("0", state.expression())
    }

    @Test
    fun deleteRightParenthesisRemovesDegreeAndReopensTrig() {
        val state = state()
        state.enter(CalculatorKey.KEY_SIN, CalculatorKey.KEY_NUM3, CalculatorKey.KEY_NUM0, CalculatorKey.KEY_RPAREN)

        state.deleteLast()

        assertEquals(listOf(CalculatorKey.KEY_SIN, CalculatorKey.KEY_NUM3, CalculatorKey.KEY_NUM0), state.tokens)
        assertTrue(state.hasOpenAngleFunction)
    }

    @Test
    fun deleteRightParenthesisWithoutOpenParenthesesDoesNotCrash() {
        val state = state()
        state.enter(CalculatorKey.KEY_RPAREN)

        state.deleteLast()

        assertEquals(listOf(CalculatorKey.KEY_NUM0), state.tokens)
    }

    @Test
    fun resultClearsParenthesisState() {
        val state = state()
        state.enter(CalculatorKey.KEY_SIN, CalculatorKey.KEY_NUM3, CalculatorKey.KEY_NUM0, CalculatorKey.KEY_RPAREN)

        state.replaceWithResult(listOf(CalculatorKey.KEY_NUM5))

        assertEquals(listOf(CalculatorKey.KEY_NUM5), state.tokens)
        assertFalse(state.hasOpenAngleFunction)

        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)
        assertEquals(listOf(CalculatorKey.KEY_NUM5), state.tokens)

        state.deleteLast()
        assertEquals(listOf(CalculatorKey.KEY_NUM0), state.tokens)
    }

    @Test
    fun nonTrigFunctionInsideTrigKeepsUnitButton() {
        val state = state()
        state.enter(CalculatorKey.KEY_SIN, CalculatorKey.KEY_SQRT)

        assertTrue(state.hasOpenAngleFunction)

        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)
        assertTrue(state.hasOpenAngleFunction)

        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)
        assertFalse(state.hasOpenAngleFunction)
    }

    @Test
    fun unitButtonStaysVisibleForNestedPlainParentheses() {
        val state = state()
        state.enter(CalculatorKey.KEY_SIN, CalculatorKey.KEY_LPAREN, CalculatorKey.KEY_LPAREN)

        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)
        assertTrue(state.hasOpenAngleFunction)

        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)
        assertTrue(state.hasOpenAngleFunction)

        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)
        assertFalse(state.hasOpenAngleFunction)
    }

    @Test
    fun nestedTrigFunctionsEachReceiveDegree() {
        val state = state()
        state.enter(CalculatorKey.KEY_SIN, CalculatorKey.KEY_COS, CalculatorKey.KEY_NUM3, CalculatorKey.KEY_NUM0)

        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)
        state.insert(CalculatorKey.KEY_RPAREN, AngleUnit.Degrees)

        assertEquals("sin(cos(30°)°)", state.expression())
        assertEquals(2, state.tokens.count { it == CalculatorKey.KEY_DEGREE })
    }

    @Test
    fun inverseTrigFunctionsDoNotReceiveDegree() {
        val state = state()
        state.enter(
            CalculatorKey.KEY_ARCSIN,
            CalculatorKey.KEY_NUM0,
            CalculatorKey.KEY_DOT,
            CalculatorKey.KEY_NUM5,
            CalculatorKey.KEY_RPAREN,
        )

        assertEquals("arcsin(0.5)", state.expression())
        assertFalse(state.tokens.contains(CalculatorKey.KEY_DEGREE))
        assertFalse(state.hasOpenAngleFunction)
    }

    @Test
    fun unknownResultCharacterMapsToErrorKey() {
        assertEquals(CalculatorKey.KEY_ERROR, CalculatorKey.fromChar('?'))
    }
}
