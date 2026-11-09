package git.furkancetinkaya.calculator.ui.calculator

import git.furkancetinkaya.calculator.domain.input.CalculatorKey

internal object KeypadLayouts {

    val primary = listOf(
        CalculatorKey.KEY_AC, CalculatorKey.KEY_DEL, CalculatorKey.KEY_LPAREN, CalculatorKey.KEY_RPAREN,
        CalculatorKey.KEY_SQRT, CalculatorKey.KEY_POW, CalculatorKey.KEY_MOD, CalculatorKey.KEY_DIV,
        CalculatorKey.KEY_NUM7, CalculatorKey.KEY_NUM8, CalculatorKey.KEY_NUM9, CalculatorKey.KEY_MUL,
        CalculatorKey.KEY_NUM4, CalculatorKey.KEY_NUM5, CalculatorKey.KEY_NUM6, CalculatorKey.KEY_MINUS,
        CalculatorKey.KEY_NUM1, CalculatorKey.KEY_NUM2, CalculatorKey.KEY_NUM3, CalculatorKey.KEY_PLUS,
        CalculatorKey.KEY_ALT, CalculatorKey.KEY_NUM0, CalculatorKey.KEY_DOT, CalculatorKey.KEY_RESULT,
    )

    val secondary = listOf(
        CalculatorKey.KEY_AC, CalculatorKey.KEY_DEL, CalculatorKey.KEY_LPAREN, CalculatorKey.KEY_RPAREN,
        CalculatorKey.KEY_FACT, CalculatorKey.KEY_LOG10, CalculatorKey.KEY_LOG2, CalculatorKey.KEY_LN,
        CalculatorKey.KEY_INV, CalculatorKey.KEY_SIN, CalculatorKey.KEY_COS, CalculatorKey.KEY_TAN,
        CalculatorKey.KEY_PI, CalculatorKey.KEY_SINH, CalculatorKey.KEY_COSH, CalculatorKey.KEY_TANH,
        CalculatorKey.KEY_E, CalculatorKey.KEY_EULER, CalculatorKey.KEY_ROUND, CalculatorKey.KEY_ABS,
        CalculatorKey.KEY_ALT, CalculatorKey.KEY_FLOOR, CalculatorKey.KEY_CEIL, CalculatorKey.KEY_RESULT,
    )

    val inverseTrig = listOf(
        CalculatorKey.KEY_AC, CalculatorKey.KEY_DEL, CalculatorKey.KEY_LPAREN, CalculatorKey.KEY_RPAREN,
        CalculatorKey.KEY_FACT, CalculatorKey.KEY_LOG10, CalculatorKey.KEY_LOG2, CalculatorKey.KEY_LN,
        CalculatorKey.KEY_INV, CalculatorKey.KEY_ARCSIN, CalculatorKey.KEY_ARCCOS, CalculatorKey.KEY_ARCTAN,
        CalculatorKey.KEY_PI, CalculatorKey.KEY_ARCSINH, CalculatorKey.KEY_ARCCOSH, CalculatorKey.KEY_ARCTANH,
        CalculatorKey.KEY_E, CalculatorKey.KEY_EULER, CalculatorKey.KEY_ROUND, CalculatorKey.KEY_ABS,
        CalculatorKey.KEY_ALT, CalculatorKey.KEY_FLOOR, CalculatorKey.KEY_CEIL, CalculatorKey.KEY_RESULT,
    )
}
