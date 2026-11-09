package git.furkancetinkaya.calculator.ui.calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import git.furkancetinkaya.calculator.domain.input.CalculatorInputEditor
import git.furkancetinkaya.calculator.domain.input.CalculatorKey
import git.furkancetinkaya.calculator.domain.input.CalculatorKeyType
import git.furkancetinkaya.calculator.domain.math.EvaluateExpression
import git.furkancetinkaya.calculator.domain.math.EvaluationResult
import git.furkancetinkaya.calculator.domain.math.WarmUpMathEngine
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val evaluateExpression: EvaluateExpression,
    private val warmUpMathEngine: WarmUpMathEngine,
) : ViewModel() {

    private companion object {
        const val TAG = "CalculatorViewModel"
    }

    private val inputEditor = CalculatorInputEditor()

    /**
     * Incremented whenever the user changes the expression. A result is only
     * applied when this still matches the revision captured before evaluation,
     * so input typed while math engine is running is never overwritten.
     */
    private var inputRevision = 0L
    private var resultJob: Job? = null

    private val _uiState = MutableStateFlow(
        CalculatorUiState(
            expression = inputEditor.expression(),
            isAngleUnitButtonVisible = inputEditor.hasOpenAngleFunction,
        )
    )
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // TODO: Causes ~2 seconds of delay at first start
            runCatching { warmUpMathEngine() }
        }
    }

    fun onKeyPressed(key: CalculatorKey) {
        when (key) {
            CalculatorKey.KEY_RESULT -> evaluate()
            CalculatorKey.KEY_AC -> {
                inputRevision++
                inputEditor.clear()
                publishState()
            }
            CalculatorKey.KEY_DEL -> {
                inputRevision++
                inputEditor.deleteLast()
                publishState()
            }
            CalculatorKey.KEY_ALT -> toggleKeypadMode()
            CalculatorKey.KEY_INV -> toggleInverseMode()
            else -> {
                if (key.type == CalculatorKeyType.ACTION) {
                    return
                }
                inputRevision++
                inputEditor.insert(key, _uiState.value.angleUnit)
                publishState()
            }
        }
    }

    fun onAngleUnitToggled() {
        _uiState.update { state ->
            state.copy(angleUnit = state.angleUnit.toggled())
        }
    }

    private fun evaluate() {
        resultJob?.cancel()

        val revision = inputRevision
        val expression = inputEditor.expression()

        resultJob = viewModelScope.launch {
            val resultTokens = when (val result = evaluateExpression(expression)) {
                is EvaluationResult.Value -> result.text.map { CalculatorKey.fromChar(it) }
                EvaluationResult.Undefined -> listOf(CalculatorKey.KEY_UNDEFINED)
                is EvaluationResult.Failure -> {
                    Log.e(TAG, "Evaluation failed for \"$expression\"", result.cause)
                    listOf(CalculatorKey.KEY_ERROR)
                }
            }

            if (inputRevision == revision) {
                inputEditor.replaceWithResult(resultTokens)
                publishState()
            }
        }
    }

    private fun toggleKeypadMode() {
        _uiState.update { state ->
            state.copy(
                keypadMode = when (state.keypadMode) {
                    KeypadMode.Primary -> KeypadMode.Secondary
                    else -> KeypadMode.Primary
                },
            )
        }
    }

    private fun toggleInverseMode() {
        _uiState.update { state ->
            state.copy(
                keypadMode = when (state.keypadMode) {
                    KeypadMode.Secondary -> KeypadMode.InverseTrig
                    KeypadMode.InverseTrig -> KeypadMode.Secondary
                    KeypadMode.Primary -> KeypadMode.Primary
                },
            )
        }
    }

    private fun publishState() {
        _uiState.update { state ->
            state.copy(
                expression = inputEditor.expression(),
                isAngleUnitButtonVisible = inputEditor.hasOpenAngleFunction,
            )
        }
    }
}
