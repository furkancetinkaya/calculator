package git.furkancetinkaya.calculator.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import git.furkancetinkaya.calculator.domain.input.AngleUnit
import git.furkancetinkaya.calculator.domain.input.CalculatorKey
import git.furkancetinkaya.calculator.ui.calculator.components.CalculatorDisplay
import git.furkancetinkaya.calculator.ui.calculator.components.CalculatorKeypad
import git.furkancetinkaya.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorScreen(
    state: CalculatorUiState,
    onKeyPressed: (CalculatorKey) -> Unit,
    onAngleUnitToggled: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        CalculatorDisplay(
            displayText = state.expression,
            isAngleUnitButtonVisible = state.isAngleUnitButtonVisible,
            angleUnit = state.angleUnit,
            onAngleUnitToggled = onAngleUnitToggled,
        )

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        CalculatorKeypad(
            keypadMode = state.keypadMode,
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow),
            onKeyClick = onKeyPressed,
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Composable
private fun CalculatorScreenLightPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorScreen(
            state = previewState(),
            onKeyPressed = {},
            onAngleUnitToggled = {},
        )
    }
}

@Preview(name = "Dark", showBackground = true)
@Composable
private fun CalculatorScreenDarkPreview() {
    CalculatorTheme(darkTheme = true) {
        CalculatorScreen(
            state = previewState(),
            onKeyPressed = {},
            onAngleUnitToggled = {},
        )
    }
}

private fun previewState() = CalculatorUiState(
    expression = "12+sin(30°)",
    angleUnit = AngleUnit.Degrees,
    isAngleUnitButtonVisible = true,
)
