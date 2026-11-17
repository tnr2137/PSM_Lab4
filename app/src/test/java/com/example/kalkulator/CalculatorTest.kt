package com.example.kalkulator

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mariuszgromada.math.mxparser.License

class CalculatorTest {

    @Before
    fun setUp() {
        License.iConfirmNonCommercialUse("Filip Turczyn")
    }

    @Test
    fun testKolejnoscDzialan() {
        assertEquals("6", calculateExpression("2+2*2"))
    }

    @Test
    fun testNawiasy() {
        assertEquals("8", calculateExpression("(2+2)*2"))
    }

    @Test
    fun testDzieleniePrzezZero() {
        assertEquals("Błąd", calculateExpression("1/0"))
    }

    @Test
    fun testWyrazenieNiedokonczone() {
        assertEquals("", calculateExpression("2+"))
    }

    @Test
    fun testNawiasNiezamkniety() {
        assertEquals("", calculateExpression("((2+2)"))
    }

    @Test
    fun testPustePole() {
        assertEquals("", calculateExpression(""))
    }

    @Test
    fun testDziedzinaFunkcji() {
        assertEquals("Błąd", calculateExpression("sqrt(-1)"))
    }

    @Test
    fun testDuzeLiczby() {
        assertEquals("1524157875019052032", calculateExpression("1234567890*1234567890"))
    }

    @Test
    fun testSeparatorDziesietnyKropka() {
        assertEquals("3,5", calculateExpression("2.5+1"))
    }

    @Test
    fun testSeparatorDziesietnyPrzecinek() {
        assertEquals("3,5", calculateExpression("2,5+1"))
    }
}