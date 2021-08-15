package com.jafritech.chinesetakeaway.helperPackage

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class HelperTestClass : TestCase() {


    @Test
    fun testDbOperations() {
        val context : Context = ApplicationProvider.getApplicationContext()
        val helper = Helper()

        var result = helper.getOrderList(context)
        assertThat(result).isEmpty()

        helper.insertOrder("Momos", "6", "33.0", context)
        result = helper.getOrderList(context)
        assertThat(result.size).isEqualTo(1)

        helper.insertOrder("Onion Rings", "1", "3.20", context)
        result = helper.getOrderList(context)
        assertThat(result.size).isEqualTo(2)

        helper.insertOrder("Won Ton Soup", "1", "3.60", context)
        result = helper.getOrderList(context)
        assertThat(result.size).isEqualTo(3)

        helper.deleteAllRecords(context)

        result = helper.getOrderList(context)
        assertThat(result.size).isEqualTo(0)
    }

}