package git.furkancetinkaya.calculator.domain.input

/**
 * One parenthesis or function scope opened by the user.
 *
 * A scope is open while [closeNumber] is negative. When the user types `)`,
 * the most recently opened scope is marked as closed and receives a
 * monotonically increasing close number. DEL uses those numbers to reopen the
 * correct scope again.
 */
private data class ParenthesisScope(
    val isTrigonometric: Boolean,
    val closeNumber: Int = -1,
) {
    val isOpen: Boolean get() = closeNumber < 0
}

/**
 * Pure input model for the calculator keypad.
 *
 * Keeping the token list and the parenthesis bookkeeping together makes it
 * impossible for the two to drift apart, and makes the editing rules testable
 * without Android or coroutines.
 */
internal class CalculatorInputEditor {

    private var tokenList: List<CalculatorKey> = DEFAULT_TOKENS
    private val parentheses = mutableListOf<ParenthesisScope>()
    private var nextCloseNumber = 0

    val tokens: List<CalculatorKey> get() = tokenList

    /** True while the user is inside an angle-taking trigonometric function. */
    val hasOpenAngleFunction: Boolean
        get() = parentheses.any { it.isOpen && it.isTrigonometric }

    fun expression(): String = buildString {
        for (key in tokenList) {
            append(key.value)
            if (key.type == CalculatorKeyType.STD_FUNCTION ||
                key.type == CalculatorKeyType.TRIG_FUNCTION
            ) {
                append(CalculatorKey.KEY_LPAREN.value)
            }
        }
    }

    fun insert(key: CalculatorKey, angleUnit: AngleUnit) {
        when (key.type) {
            CalculatorKeyType.NUMBER -> replaceInitialZeroOrAppend(key)
            CalculatorKeyType.OPERATOR -> append(key)
            CalculatorKeyType.STD_FUNCTION,
            CalculatorKeyType.TRIG_FUNCTION -> {
                replaceInitialZeroOrAppend(key)
                parentheses.add(ParenthesisScope(isTrigonometric = key.isAngleFunction()))
            }
            CalculatorKeyType.SPECIAL -> when (key) {
                CalculatorKey.KEY_LPAREN -> {
                    replaceInitialZeroOrAppend(key)
                    parentheses.add(ParenthesisScope(isTrigonometric = false))
                }
                CalculatorKey.KEY_RPAREN -> closeParenthesis(angleUnit)
                CalculatorKey.KEY_EULER,
                CalculatorKey.KEY_PI -> replaceInitialZeroOrAppend(key)
                else -> append(key)
            }
            CalculatorKeyType.ACTION -> Unit
        }
    }

    fun deleteLast() {
        if (tokenList.isEmpty()) {
            tokenList = DEFAULT_TOKENS
            return
        }

        val dropped = tokenList.last()
        var updated = tokenList.dropLast(1)

        when {
            dropped.openedParenthesis() -> {
                if (parentheses.isNotEmpty()) {
                    parentheses.removeAt(parentheses.lastIndex)
                }
            }
            dropped == CalculatorKey.KEY_RPAREN -> {
                val reopenedIndex = parentheses.indices
                    .filter { parentheses[it].closeNumber >= 0 }
                    .maxByOrNull { parentheses[it].closeNumber }

                if (reopenedIndex != null) {
                    val reopened = parentheses[reopenedIndex]
                    parentheses[reopenedIndex] = reopened.copy(closeNumber = -1)

                    // The degree sign directly before `)` was inserted together
                    // with the closing parenthesis, so remove it as one unit.
                    if (reopened.isTrigonometric &&
                        updated.lastOrNull() == CalculatorKey.KEY_DEGREE
                    ) {
                        updated = updated.dropLast(1)
                    }
                }
            }
        }

        tokenList = updated.ifEmpty { DEFAULT_TOKENS }
    }

    fun replaceWithResult(resultTokens: List<CalculatorKey>) {
        tokenList = resultTokens.ifEmpty { DEFAULT_TOKENS }
        resetParentheses()
    }

    fun clear() {
        tokenList = DEFAULT_TOKENS
        resetParentheses()
    }

    private fun closeParenthesis(angleUnit: AngleUnit) {
        val index = parentheses.indexOfLast { it.isOpen }
        if (index < 0) {
            // There is nothing to close; do not append a dangling `)`.
            return
        }

        val closed = parentheses[index]
        parentheses[index] = closed.copy(closeNumber = nextCloseNumber++)

        tokenList = if (closed.isTrigonometric && angleUnit == AngleUnit.Degrees) {
            tokenList + CalculatorKey.KEY_DEGREE + CalculatorKey.KEY_RPAREN
        } else {
            tokenList + CalculatorKey.KEY_RPAREN
        }
    }

    private fun replaceInitialZeroOrAppend(key: CalculatorKey) {
        tokenList = if (tokenList.size == 1 && tokenList.first() == CalculatorKey.KEY_NUM0) {
            listOf(key)
        } else {
            tokenList + key
        }
    }

    private fun append(key: CalculatorKey) {
        tokenList = tokenList + key
    }

    private fun resetParentheses() {
        parentheses.clear()
        nextCloseNumber = 0
    }

    private fun CalculatorKey.openedParenthesis(): Boolean =
        this == CalculatorKey.KEY_LPAREN ||
            type == CalculatorKeyType.STD_FUNCTION ||
            type == CalculatorKeyType.TRIG_FUNCTION

    /**
     * Only sin/cos/tan take an angle argument. Hyperbolic and inverse
     * functions must not receive an automatically inserted degree sign.
     */
    private fun CalculatorKey.isAngleFunction(): Boolean =
        this == CalculatorKey.KEY_SIN ||
            this == CalculatorKey.KEY_COS ||
            this == CalculatorKey.KEY_TAN

    private companion object {
        val DEFAULT_TOKENS = listOf(CalculatorKey.KEY_NUM0)
    }
}
