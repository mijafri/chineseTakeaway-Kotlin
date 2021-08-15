package com.jafritech.chinesetakeaway

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class SqlHandler(context: Context?) {
    // declare nullable context variable and assign null
    var context: Context? = null

    // declare SQLiteDatabase instance
    private var sqlDatabase: SQLiteDatabase

    // create SqlDbHelper object and pass the required information
    private var dbHelper: SqlDbHelper = SqlDbHelper(
        context, DATABASE_NAME, null,
        DATABASE_VERSION
    )

    // local function to execute query
    fun executeQuery(query: String?) {
        try {
            // closer the database if open
            if (sqlDatabase.isOpen) {
                sqlDatabase.close()
            }
            // get writable database
            sqlDatabase = dbHelper.writableDatabase
            // execute query
            sqlDatabase.execSQL(query)
        } catch (e: Exception) {
            println("DATABASE ERROR $e")
        }
    }

    // local function to delete all data from order_list table
    fun deleteRecords(query: String?) {
        try {
            // close data if open
            if (sqlDatabase.isOpen) {
                sqlDatabase.close()
            }
            // get writable database
            sqlDatabase = dbHelper.writableDatabase
            // execute query
            sqlDatabase.execSQL(query)
        } catch (e: Exception) {
            println("DATABASE ERROR $e")
        }
    }

    // local function to get data
    fun selectQuery(query: String?): Cursor? {
        // declare cursor variable
        var c1: Cursor? = null
        try {
            // closer the database if open
            if (sqlDatabase.isOpen) {
                sqlDatabase.close()
            }
            // get writable database
            sqlDatabase = dbHelper.writableDatabase
            // get data
            c1 = sqlDatabase.rawQuery(query, null)
        } catch (e: Exception) {
            println("DATABASE ERROR $e")
        }
        // return data
        return c1
    }

    companion object {
        // database name
        const val DATABASE_NAME: String = "CHINESETAKEAWAY"

        // database version
        const val DATABASE_VERSION: Int = 1
    }

    init {
        sqlDatabase = dbHelper.writableDatabase
    }
}