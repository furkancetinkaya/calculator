package git.furkancetinkaya.calculator.domain.math

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultEvaluateExpression @Inject constructor(
    private val mathEngine: MathEngine,
    private val numberFormatter: NumberFormatter,
) : EvaluateExpression {

    override suspend fun invoke(expression: String): EvaluationResult =
        withContext(Dispatchers.Default) {
            try {
                mathEngine.evaluate(expression)
                    ?.let { value -> EvaluationResult.Value(numberFormatter.format(value)) }
                    ?: EvaluationResult.Undefined
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                EvaluationResult.Failure(error)
            }
        }
}
