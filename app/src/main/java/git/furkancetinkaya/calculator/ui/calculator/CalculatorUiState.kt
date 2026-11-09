package git.furkancetinkaya.calculator.ui.calculator

import androidx.compose.runtime.Immutable
import git.furkancetinkaya.calculator.domain.input.AngleUnit

/** Immutable state rendered by the calculator screen. */
@Immutable
data class CalculatorUiState(
    val expression: String = "0",
    val keypadMode: KeypadMode = KeypadMode.Primary,
    val angleUnit: AngleUnit = AngleUnit.Degrees,
    val isAngleUnitButtonVisible: Boolean = false,
)
