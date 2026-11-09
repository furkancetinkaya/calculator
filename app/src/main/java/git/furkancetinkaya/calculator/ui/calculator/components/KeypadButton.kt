package git.furkancetinkaya.calculator.ui.calculator.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import git.furkancetinkaya.calculator.domain.input.CalculatorKey
import git.furkancetinkaya.calculator.domain.input.CalculatorKeyType
import git.furkancetinkaya.calculator.ui.calculator.displayLabel
import git.furkancetinkaya.calculator.ui.theme.keypadButtonColors

@Composable
internal fun KeypadButton(
    key: CalculatorKey,
    onClick: (CalculatorKey) -> Unit,
) {
    val colors = if (key == CalculatorKey.KEY_RESULT) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    } else {
        keypadButtonColors(
            contentColor = when (key.type) {
                CalculatorKeyType.OPERATOR -> MaterialTheme.colorScheme.primary
                CalculatorKeyType.STD_FUNCTION, CalculatorKeyType.TRIG_FUNCTION ->
                    MaterialTheme.colorScheme.onSurfaceVariant
                else -> MaterialTheme.colorScheme.onSurface
            },
        )
    }

    Button(
        onClick = { onClick(key) },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RectangleShape,
        colors = colors,
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 0.dp),
    ) {
        Text(
            text = key.displayLabel(),
            fontSize = when (key.type) {
                CalculatorKeyType.STD_FUNCTION, CalculatorKeyType.TRIG_FUNCTION -> 14.sp
                CalculatorKeyType.ACTION -> 16.sp
                else -> 20.sp
            },
            maxLines = 1,
            softWrap = false,
            textAlign = TextAlign.Center,
        )
    }
}
