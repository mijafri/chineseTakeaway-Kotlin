package com.jafritech.chinesetakeaway.helperPackage

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HelperTest {

    @Test
    fun testFormatPrice() {
        val amount = 20.0
        val expected = "£20.00"
        val result = Helper().formatPrice(amount)
        assertThat(result).isEqualTo(expected)
    }
}