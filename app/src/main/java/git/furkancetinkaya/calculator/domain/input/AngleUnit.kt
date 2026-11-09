package git.furkancetinkaya.calculator.domain.input

enum class AngleUnit {
    Degrees,
    Radians;

    fun toggled(): AngleUnit = when (this) {
        Degrees -> Radians
        Radians -> Degrees
    }
}
