package git.furkancetinkaya.calculator.domain.math

/**
 * Warms up the math engine without blocking the main thread.
 */
interface WarmUpMathEngine {
    suspend operator fun invoke()
}
