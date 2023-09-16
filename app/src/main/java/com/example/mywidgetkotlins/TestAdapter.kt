package com.example.mywidgetkotlins

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.IOException

class TestAdapter(private val mContext: Context) {
    private var mDb: SQLiteDatabase? = null
    private val mDbHelper: DataBaseHelper

    init {
        mDbHelper = DataBaseHelper(mContext)
    }

    @Throws(SQLException::class)
    fun createDatabase(): TestAdapter {
        try {
            mDbHelper.createDataBase()
        } catch (mIOException: IOException) {
            Log.e(TAG, "$mIOException  UnableToCreateDatabase")
            throw Error("UnableToCreateDatabase")
        }
        return this
    }

    @Throws(SQLException::class)
    fun open(): TestAdapter {
        try {
            mDbHelper.openDataBase()
            mDbHelper.close()
            mDb = mDbHelper.readableDatabase
            mDb = mDbHelper.writableDatabase
        } catch (mSQLException: SQLException) {
            Log.e(TAG, "open >>$mSQLException")
            throw mSQLException
        }
        return this
    }

    fun close() {
        mDbHelper.close()
    }

    companion object {
        protected const val TAG = "DataAdapter"
    }
}