package com.jafritech.chinesetakeaway

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class SqlDbHelper(
    context: Context?, name: String?, factory: CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, name, factory, version) {
    // override function to create database
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SCRIPT_CREATE_DATABASE)
    }

    // override function
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $DATABASE_TABLE")
        onCreate(db)
    }

    companion object {
        // member variables
        const val DATABASE_TABLE: String = "ORDER_LIST"
        private const val COLUMN1 = "slno"
        private const val COLUMN2 = "item"
        private const val COLUMN3 = "qty"
        private const val COLUMN4 = "price"

        // Script to create database table
        private const val SCRIPT_CREATE_DATABASE = ("create table "
                + DATABASE_TABLE + " " + "(" + COLUMN1
                + " integer primary key autoincrement, " + COLUMN2
                + " text not null, " + COLUMN3 + " text not null, " + COLUMN4
                + " text not null);")
    }
}
