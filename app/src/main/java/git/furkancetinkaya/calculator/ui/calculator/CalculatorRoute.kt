package git.furkancetinkaya.calculator.ui.calculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CalculatorRoute(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalculatorScreen(
        state = uiState,
        onKeyPressed = viewModel::onKeyPressed,
        onAngleUnitToggled = viewModel::onAngleUnitToggled,
        modifier = modifier,
    )
}
