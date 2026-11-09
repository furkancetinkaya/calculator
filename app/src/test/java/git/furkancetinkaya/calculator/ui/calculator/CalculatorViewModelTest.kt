package git.furkancetinkaya.calculator.ui.calculator

import git.furkancetinkaya.calculator.domain.input.AngleUnit
import git.furkancetinkaya.calculator.domain.input.CalculatorKey
import git.furkancetinkaya.calculator.domain.math.EvaluateExpression
import git.furkancetinkaya.calculator.domain.math.EvaluationResult
import git.furkancetinkaya.calculator.domain.math.WarmUpMathEngine
import git.furkancetinkaya.calculator.testutil.MainDispatcherRule
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalculatorViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeEvaluateExpression(
        private val result: EvaluationResult,
    ) : EvaluateExpression {
        var lastExpression: String? = null

        override suspend fun invoke(expression: String): EvaluationResult {
            lastExpression = expression
            return result
        }
    }

    private class ControlledEvaluateExpression : EvaluateExpression {
        val result = CompletableDeferred<EvaluationResult>()

        override suspend fun invoke(expression: String): EvaluationResult = result.await()
    }

    private class NoOpWarmUpMathEngine : WarmUpMathEngine {
        override suspend fun invoke() = Unit
    }

    private fun viewModel(
        evaluateExpression: EvaluateExpression = FakeEvaluateExpression(EvaluationResult.Value("0")),
    ) = CalculatorViewModel(
        evaluateExpression = evaluateExpression,
        warmUpMathEngine = NoOpWarmUpMathEngine(),
    )

    @Test
    fun startsWithEmptyExpressionInPrimaryMode() {
        val viewModel = viewModel()

        assertEquals("0", viewModel.uiState.value.expression)
        assertEquals(KeypadMode.Primary, viewModel.uiState.value.keypadMode)
        assertEquals(AngleUnit.Degrees, viewModel.uiState.value.angleUnit)
        assertFalse(viewModel.uiState.value.isAngleUnitButtonVisible)
    }

    @Test
    fun digitsAndOperatorsUpdateTheExpression() {
        val viewModel = viewModel()

        viewModel.onKeyPressed(CalculatorKey.KEY_NUM1)
        viewModel.onKeyPressed(CalculatorKey.KEY_PLUS)
        viewModel.onKeyPressed(CalculatorKey.KEY_NUM2)

        assertEquals("1+2", viewModel.uiState.value.expression)
    }

    @Test
    fun alternativeKeypadCyclesBetweenPrimaryAndSecondary() {
        val viewModel = viewModel()

        viewModel.onKeyPressed(CalculatorKey.KEY_ALT)
        assertEquals(KeypadMode.Secondary, viewModel.uiState.value.keypadMode)

        viewModel.onKeyPressed(CalculatorKey.KEY_INV)
        assertEquals(KeypadMode.InverseTrig, viewModel.uiState.value.keypadMode)

        viewModel.onKeyPressed(CalculatorKey.KEY_INV)
        assertEquals(KeypadMode.Secondary, viewModel.uiState.value.keypadMode)

        viewModel.onKeyPressed(CalculatorKey.KEY_ALT)
        assertEquals(KeypadMode.Primary, viewModel.uiState.value.keypadMode)
    }

    @Test
    fun angleUnitTogglesForTheNextFunctionArgument() {
        val viewModel = viewModel()

        viewModel.onAngleUnitToggled()

        assertEquals(AngleUnit.Radians, viewModel.uiState.value.angleUnit)
    }

    @Test
    fun successfulEvaluationReplacesTheExpression() = runTest {
        val evaluateExpression = FakeEvaluateExpression(EvaluationResult.Value("42"))
        val viewModel = viewModel(evaluateExpression)

        viewModel.onKeyPressed(CalculatorKey.KEY_NUM6)
        viewModel.onKeyPressed(CalculatorKey.KEY_RESULT)
        advanceUntilIdle()

        assertEquals("6", evaluateExpression.lastExpression)
        assertEquals("42", viewModel.uiState.value.expression)
    }

    @Test
    fun undefinedEvaluationShowsUndefined() = runTest {
        val viewModel = viewModel(FakeEvaluateExpression(EvaluationResult.Undefined))

        viewModel.onKeyPressed(CalculatorKey.KEY_NUM1)
        viewModel.onKeyPressed(CalculatorKey.KEY_DIV)
        viewModel.onKeyPressed(CalculatorKey.KEY_NUM0)
        viewModel.onKeyPressed(CalculatorKey.KEY_RESULT)
        advanceUntilIdle()

        assertEquals("Undefined", viewModel.uiState.value.expression)
    }

    @Test
    fun inputTypedDuringEvaluationIsNotOverwritten() = runTest {
        val evaluateExpression = ControlledEvaluateExpression()
        val viewModel = viewModel(evaluateExpression)

        viewModel.onKeyPressed(CalculatorKey.KEY_NUM6)
        viewModel.onKeyPressed(CalculatorKey.KEY_RESULT)
        advanceUntilIdle()

        viewModel.onKeyPressed(CalculatorKey.KEY_NUM7)
        evaluateExpression.result.complete(EvaluationResult.Value("42"))
        advanceUntilIdle()

        assertEquals("67", viewModel.uiState.value.expression)
    }
}
