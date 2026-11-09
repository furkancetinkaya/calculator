package git.furkancetinkaya.calculator.domain.input

enum class CalculatorKeyType {
    NUMBER,
    OPERATOR,
    TRIG_FUNCTION,
    STD_FUNCTION,
    SPECIAL,
    ACTION,
}

/**
 * A keypad key. [value] is the token inserted into the expression; action keys
 * are handled by the ViewModel and never inserted directly.
 */
enum class CalculatorKey(
    val type: CalculatorKeyType,
    val value: String,
) {
    KEY_NUM0(CalculatorKeyType.NUMBER, "0"),
    KEY_NUM1(CalculatorKeyType.NUMBER, "1"),
    KEY_NUM2(CalculatorKeyType.NUMBER, "2"),
    KEY_NUM3(CalculatorKeyType.NUMBER, "3"),
    KEY_NUM4(CalculatorKeyType.NUMBER, "4"),
    KEY_NUM5(CalculatorKeyType.NUMBER, "5"),
    KEY_NUM6(CalculatorKeyType.NUMBER, "6"),
    KEY_NUM7(CalculatorKeyType.NUMBER, "7"),
    KEY_NUM8(CalculatorKeyType.NUMBER, "8"),
    KEY_NUM9(CalculatorKeyType.NUMBER, "9"),

    KEY_AC(CalculatorKeyType.ACTION, "ac"),
    KEY_DEL(CalculatorKeyType.ACTION, "del"),
    KEY_RESULT(CalculatorKeyType.ACTION, "="),
    KEY_ALT(CalculatorKeyType.ACTION, "alt"),
    KEY_INV(CalculatorKeyType.ACTION, "inv"),

    KEY_POW(CalculatorKeyType.OPERATOR, "^"),
    KEY_MOD(CalculatorKeyType.OPERATOR, "%"),
    KEY_DIV(CalculatorKeyType.OPERATOR, "/"),
    KEY_MUL(CalculatorKeyType.OPERATOR, "*"),
    KEY_MINUS(CalculatorKeyType.OPERATOR, "-"),
    KEY_PLUS(CalculatorKeyType.OPERATOR, "+"),
    KEY_FACT(CalculatorKeyType.OPERATOR, "!"),

    KEY_SQRT(CalculatorKeyType.STD_FUNCTION, "sqrt"),
    KEY_LOG10(CalculatorKeyType.STD_FUNCTION, "log10"),
    KEY_LOG2(CalculatorKeyType.STD_FUNCTION, "log2"),
    KEY_LN(CalculatorKeyType.STD_FUNCTION, "ln"),
    KEY_ROUND(CalculatorKeyType.STD_FUNCTION, "rnd"),
    KEY_ABS(CalculatorKeyType.STD_FUNCTION, "abs"),
    KEY_FLOOR(CalculatorKeyType.STD_FUNCTION, "floor"),
    KEY_CEIL(CalculatorKeyType.STD_FUNCTION, "ceil"),

    KEY_SIN(CalculatorKeyType.TRIG_FUNCTION, "sin"),
    KEY_COS(CalculatorKeyType.TRIG_FUNCTION, "cos"),
    KEY_TAN(CalculatorKeyType.TRIG_FUNCTION, "tan"),
    KEY_SINH(CalculatorKeyType.TRIG_FUNCTION, "sinh"),
    KEY_COSH(CalculatorKeyType.TRIG_FUNCTION, "cosh"),
    KEY_TANH(CalculatorKeyType.TRIG_FUNCTION, "tanh"),
    KEY_ARCSIN(CalculatorKeyType.TRIG_FUNCTION, "arcsin"),
    KEY_ARCCOS(CalculatorKeyType.TRIG_FUNCTION, "arccos"),
    KEY_ARCTAN(CalculatorKeyType.TRIG_FUNCTION, "arctan"),
    KEY_ARCSINH(CalculatorKeyType.TRIG_FUNCTION, "arcsinh"),
    KEY_ARCCOSH(CalculatorKeyType.TRIG_FUNCTION, "arccosh"),
    KEY_ARCTANH(CalculatorKeyType.TRIG_FUNCTION, "arctanh"),

    KEY_LPAREN(CalculatorKeyType.SPECIAL, "("),
    KEY_RPAREN(CalculatorKeyType.SPECIAL, ")"),
    KEY_EULER(CalculatorKeyType.SPECIAL, "e"),
    KEY_PI(CalculatorKeyType.SPECIAL, "π"),
    KEY_E(CalculatorKeyType.SPECIAL, "E"),
    KEY_DOT(CalculatorKeyType.SPECIAL, "."),
    KEY_DEGREE(CalculatorKeyType.SPECIAL, "°"),
    KEY_UNDEFINED(CalculatorKeyType.SPECIAL, "Undefined"),
    KEY_ERROR(CalculatorKeyType.SPECIAL, "Error");

    companion object {
        fun fromChar(char: Char): CalculatorKey {
            val value = char.toString()
            return entries.firstOrNull { entry -> entry.value == value } ?: KEY_ERROR
        }
    }
}
