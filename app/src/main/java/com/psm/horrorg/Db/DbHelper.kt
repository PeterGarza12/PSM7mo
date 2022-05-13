package com.psm.horrorg.Db
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.psm.horrorg.Model.User

open class DbHelper(context: Context?):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER){
    companion object{
        private val DATABASE_VER= 1
        private val DATABASE_NAME = "Horrorg.db"
        //Table
        val TABLE_NAME = "User"
        private val COL_ID = "Id"
        private val COL_NAME = "UserName"
        private val COL_PASSWORD = "Password"
        private val COL_DATE = "DateBirth"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY:String = ("CREATE TABLE  $TABLE_NAME ( $COL_ID INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_PASSWORD TEXT, $COL_DATE TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }
    fun onCheckUserName(username: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery("select * from User where UserName = ?", arrayOf(username))
        return if (cursor.count > 0) {
            true
        } else {
            false
        }
    }

    fun onCheckUserNameAndPassword(username: String, password: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery(
            "select * from User where UserName = ? And Password = ?",
            arrayOf(username, password)
        )
        return if (cursor.count > 0) {
            true
        } else {
            false
        }
    }

}