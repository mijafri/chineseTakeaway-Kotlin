package com.jafritech.chinesetakeaway.helperPackage

import android.content.Context
import com.jafritech.chinesetakeaway.FoodAdapterDataSource
import com.jafritech.chinesetakeaway.OrderAdapterDataSource
import com.jafritech.chinesetakeaway.SqlHandler
import java.text.NumberFormat


class Helper {

    // declare SqlHandler instance object
    private var sqlHandler: SqlHandler? = null

    // format price
    fun formatPrice(price: Double) :String {
        // format price as per local currency rules
        return NumberFormat.getCurrencyInstance().format(price)
    }

    // check if order list is empty
    fun checkOrderList(context: Context): Boolean {
        sqlHandler = SqlHandler(context)
        val itemList  = ArrayList<FoodAdapterDataSource>()

        itemList.clear()
        val query = "SELECT * FROM ORDER_LIST"
        val c1 = sqlHandler!!.selectQuery(query)
        c1!!.moveToFirst()
        return (c1.count != 0)
    }

    // delete all records of order list
    fun deleteAllRecords(context: Context) {
        sqlHandler = SqlHandler(context)
        var query = "DELETE FROM ORDER_LIST;\n"
        query += "DELETE FROM SQLITE_SEQUENCE WHERE name='ORDER_LIST';"
        sqlHandler!!.deleteRecords(query)
    }

    // insert new record to order list
    fun insertOrder(name : String, strQty : String, strPrice : String, context: Context ){
        sqlHandler = SqlHandler(context)
        val query = ("INSERT INTO ORDER_LIST (item,qty,price) values ('"
                + name + "','" + strQty + "','" + strPrice + "')")
        // executing query to inset row in the order list table
        sqlHandler!!.executeQuery(query)
    }

    // arrange order list row
    fun arrangeOrderItem(
        slno: String,
        a: Int,
        items: String,
        b: Int,
        qty: String,
        c: Int,
        price: String,
    ): String {
        val sl = (a * 1.6).toInt()
        val its = (b * 1.6).toInt()
        val qt = (c * 1.6).toInt()
        val str: StringBuilder = java.lang.StringBuilder()
        str.append(slno)
        for (i in 0 until sl) {
            str.append(" ")
        }
        str.append(items)
        for (i in 0 until its) {
            str.append(" ")
        }
        str.append(qty)
        for (i in 0 until qt) {
            str.append(" ")
        }
        str.append(price)
        return str.toString()
    }

    // return order list
    fun getOrderList(context: Context) : ArrayList<OrderAdapterDataSource>{
        // declare OrderAdapterDataSource order list
        val orderList = ArrayList<OrderAdapterDataSource>()
        // declare SqlHandler instance
        sqlHandler = SqlHandler(context)
        // declare variable to manage item s. no.
        var sno = 0
        // create query
        val query = "SELECT * FROM ORDER_LIST "
        // execute query
        val c1 = sqlHandler!!.selectQuery(query)
        // check if result is not empty
        if (c1 != null && c1.count != 0) {
            // if data exist, goto first row
            if(c1.moveToFirst()){
                // fetch all the records while looping
                do{
                    sno += 1
                    // fetch row data
                    val name : String = c1.getString(c1.getColumnIndex("item"))
                    val qty : Int = c1.getString(c1.getColumnIndex("qty")).toInt()
                    val price : Double = c1.getString(c1.getColumnIndex("price")).toDouble()

                    // create object and add to array list
                    orderList.add(OrderAdapterDataSource(sno, name, qty, price))

                }while (c1.moveToNext())
            }
        }
        // return orderList
        return orderList
    }

}