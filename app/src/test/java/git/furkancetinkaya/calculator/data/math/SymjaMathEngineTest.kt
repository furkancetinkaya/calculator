package git.furkancetinkaya.calculator.data.math

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import kotlin.math.E
import kotlin.math.PI

class SymjaMathEngineTest {

    private val engine = SymjaMathEngine()
    private val delta = 1e-10

    @Test
    fun arithmeticOperators() {
        assertEquals(14.0, engine.evaluate("2+3*4")!!, delta)
        assertEquals(2.0, engine.evaluate("10%4")!!, delta)
        assertEquals(1024.0, engine.evaluate("2^10")!!, delta)
        assertEquals(120.0, engine.evaluate("5!")!!, delta)
    }

    @Test
    fun constants() {
        assertEquals(PI, engine.evaluate("π")!!, delta)
        assertEquals(E, engine.evaluate("e")!!, delta)
        assertEquals(1000.0, engine.evaluate("1E3")!!, delta)
        assertEquals(0.001, engine.evaluate("1E-3")!!, delta)
        assertEquals(350.0, engine.evaluate("3.5E2")!!, delta)
    }

    @Test
    fun functions() {
        assertEquals(4.0, engine.evaluate("sqrt(16)")!!, delta)
        assertEquals(3.0, engine.evaluate("log10(1000)")!!, delta)
        assertEquals(2.0, engine.evaluate("log2(4)")!!, delta)
        assertEquals(1.0, engine.evaluate("ln(e)")!!, delta)
        assertEquals(0.5, engine.evaluate("sin(30°)")!!, delta)
        assertEquals(0.5, engine.evaluate("cos(60°)")!!, delta)
        assertEquals(1.0, engine.evaluate("tan(45°)")!!, delta)
        assertEquals(PI / 6.0, engine.evaluate("arcsin(0.5)")!!, delta)
        assertEquals(PI / 3.0, engine.evaluate("arccos(0.5)")!!, delta)
        assertEquals(PI / 4.0, engine.evaluate("arctan(1)")!!, delta)
        assertEquals(kotlin.math.sinh(1.0), engine.evaluate("sinh(1)")!!, delta)
        assertEquals(kotlin.math.cosh(1.0), engine.evaluate("cosh(1)")!!, delta)
        assertEquals(kotlin.math.tanh(1.0), engine.evaluate("tanh(1)")!!, delta)
        assertEquals(kotlin.math.asinh(1.0), engine.evaluate("arcsinh(1)")!!, delta)
        assertEquals(kotlin.math.acosh(2.0), engine.evaluate("arccosh(2)")!!, delta)
        assertEquals(kotlin.math.atanh(0.5), engine.evaluate("arctanh(0.5)")!!, delta)
        assertEquals(2.0, engine.evaluate("abs(-2)")!!, delta)
        assertEquals(2.0, engine.evaluate("floor(2.9)")!!, delta)
        assertEquals(3.0, engine.evaluate("ceil(2.1)")!!, delta)
        assertEquals(3.0, engine.evaluate("rnd(2.6)")!!, delta)
    }

    @Test
    fun undefinedResults() {
        assertNull(engine.evaluate("1/0"))
        assertNull(engine.evaluate("sqrt(-1)"))
        assertNull(engine.evaluate("unknownFunction(1)"))
    }

    @Test
    fun warmUpKeepsEngineUsable() {
        engine.warmUp()
        assertEquals(4.0, engine.evaluate("2+2")!!, delta)
    }
}
