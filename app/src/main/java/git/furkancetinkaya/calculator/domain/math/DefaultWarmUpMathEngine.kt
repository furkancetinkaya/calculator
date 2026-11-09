package git.furkancetinkaya.calculator.domain.math

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultWarmUpMathEngine @Inject constructor(
    private val mathEngine: MathEngine,
) : WarmUpMathEngine {

    override suspend fun invoke() = withContext(Dispatchers.Default) {
        mathEngine.warmUp()
    }
}
