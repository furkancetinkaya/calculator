package git.furkancetinkaya.calculator.ui.calculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import git.furkancetinkaya.calculator.domain.input.CalculatorKey
import git.furkancetinkaya.calculator.ui.calculator.KeypadLayouts
import git.furkancetinkaya.calculator.ui.calculator.KeypadMode

@Composable
internal fun CalculatorKeypad(
    keypadMode: KeypadMode,
    modifier: Modifier = Modifier,
    onKeyClick: (CalculatorKey) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val keys = when (keypadMode) {
            KeypadMode.Primary -> KeypadLayouts.primary
            KeypadMode.Secondary -> KeypadLayouts.secondary
            KeypadMode.InverseTrig -> KeypadLayouts.inverseTrig
        }
        KeypadGrid(
            keys = keys,
            onKeyClick = onKeyClick,
        )
    }
}

@Composable
private fun KeypadGrid(
    modifier: Modifier = Modifier,
    keys: List<CalculatorKey>,
    onKeyClick: (CalculatorKey) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false,
    ) {
        items(keys) { key ->
            KeypadButton(key, onKeyClick)
        }
    }
}
