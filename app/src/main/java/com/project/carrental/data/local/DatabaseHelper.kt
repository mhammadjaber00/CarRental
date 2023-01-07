package com.project.carrental.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    "database.sqlite", null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Admin (id INTEGER PRIMARY KEY, name TEXT, email TEXT, password TEXT, phone TEXT, cars BLOB)")

        db?.execSQL("CREATE TABLE Customer (id INTEGER PRIMARY KEY, name TEXT, email TEXT, password TEXT, phone TEXT, cars BLOB)")

        db?.execSQL("CREATE TABLE Car (id INTEGER PRIMARY KEY, name TEXT, price REAL, image TEXT, color TEXT, isRented INTEGER, startDate TEXT, endDate TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}