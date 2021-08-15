package com.jafritech.chinesetakeaway

import android.content.Context
import com.jafritech.chinesetakeaway.helperPackage.Helper
import java.io.Serializable


class OrderAdapterDataSource(
    val itemSno: Int = 0,
    val itemName: String = "",
    val itemQty: Int = 0,
    val itemPrice: Double = 0.0
) : Serializable {

    companion object {

        fun getOrderList(context: Context): ArrayList<OrderAdapterDataSource> {

            // declare array list of OrderAdapterDataSource
            var orderList = ArrayList<OrderAdapterDataSource>()
            orderList.clear()

            // get order list with getOrderList function from Helper class
            orderList = Helper().getOrderList(context)

            // return order list
            return orderList
        }
    }
}

