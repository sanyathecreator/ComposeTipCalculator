package com.example.composetipcalculator

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorTests {
    @Test
    fun calculateTip_20PercentNoRoundUp() {
        val amount = 10.0
        val tipPercent = 20.0
        val expectedTip = NumberFormat.getCurrencyInstance().format(2.0)
        val actualTip = calculateTip(amount, tipPercent, false)
        assertEquals(expectedTip, actualTip)
    }

    @Test
    fun ensurePositive_NegativeInput() {
        val input = -5.0
        val expected = 5.0
        val actual = ensurePositive(input)
        assertEquals(expected, actual)
    }
    @Test
    fun ensurePositive_PositiveInput() {
        val input = 5.0
        val expected = 5.0
        val actual = ensurePositive(input)
        assertEquals(expected, actual)
    }
}