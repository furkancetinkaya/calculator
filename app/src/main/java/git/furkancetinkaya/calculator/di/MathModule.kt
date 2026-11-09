package git.furkancetinkaya.calculator.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import git.furkancetinkaya.calculator.data.format.CalculatorNumberFormatter
import git.furkancetinkaya.calculator.data.math.SymjaMathEngine
import git.furkancetinkaya.calculator.domain.math.DefaultEvaluateExpression
import git.furkancetinkaya.calculator.domain.math.DefaultWarmUpMathEngine
import git.furkancetinkaya.calculator.domain.math.EvaluateExpression
import git.furkancetinkaya.calculator.domain.math.MathEngine
import git.furkancetinkaya.calculator.domain.math.NumberFormatter
import git.furkancetinkaya.calculator.domain.math.WarmUpMathEngine
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MathModule {

    @Binds
    @Singleton
    abstract fun bindMathEngine(
        implementation: SymjaMathEngine,
    ): MathEngine

    @Binds
    abstract fun bindNumberFormatter(
        implementation: CalculatorNumberFormatter,
    ): NumberFormatter

    @Binds
    abstract fun bindEvaluateExpression(
        implementation: DefaultEvaluateExpression,
    ): EvaluateExpression

    @Binds
    abstract fun bindWarmUpMathEngine(
        implementation: DefaultWarmUpMathEngine,
    ): WarmUpMathEngine
}
