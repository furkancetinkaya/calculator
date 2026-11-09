package git.furkancetinkaya.calculator.domain.math

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class EvaluateExpressionTest {

    private class FakeMathEngine(
        private val result: Double? = null,
        private val error: Exception? = null,
    ) : MathEngine {

        override fun warmUp() = Unit

        override fun evaluate(expression: String): Double? {
            error?.let { throw it }
            return result
        }
    }

    private class FakeNumberFormatter : NumberFormatter {
        override fun format(value: Double): String = "formatted:$value"
    }

    @Test
    fun returnsFormattedValue() = runTest {
        val useCase = DefaultEvaluateExpression(
            mathEngine = FakeMathEngine(result = 42.0),
            numberFormatter = FakeNumberFormatter(),
        )

        assertEquals(EvaluationResult.Value("formatted:42.0"), useCase("6*7"))
    }

    @Test
    fun reportsUndefinedWhenTheEngineHasNoResult() = runTest {
        val useCase = DefaultEvaluateExpression(
            mathEngine = FakeMathEngine(result = null),
            numberFormatter = FakeNumberFormatter(),
        )

        assertEquals(EvaluationResult.Undefined, useCase("1/0"))
    }

    @Test
    fun wrapsEngineFailures() = runTest {
        val error = IllegalStateException("engine failed")
        val useCase = DefaultEvaluateExpression(
            mathEngine = FakeMathEngine(error = error),
            numberFormatter = FakeNumberFormatter(),
        )

        val result = useCase("2+2")

        assertSame(error, (result as EvaluationResult.Failure).cause)
    }
}
