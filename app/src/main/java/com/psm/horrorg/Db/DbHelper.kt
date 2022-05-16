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
        val COL_ID = "Id"
        val COL_NAME = "UserName"
        val COL_PASSWORD = "Password"
        val COL_DATE = "DateBirth"

        //Books Table
        val TABLE_BOOKS_NAME = "Books"
        val COL_BOOKS_ID = "Id"
        val COL_BOOKS_USER = "UserId"
        val COL_BOOKS_TITLE = "Title"
        val COL_BOOKS_DESCRIPTION = "Description"
        val COL_BOOKS_IDIMAGE = "IdImage"
        val COL_BOOKS_IDGENRE = "IdGenre"
        val COL_BOOKS_TITLEGENRE = "TitleGenre"
        val COL_BOOKS_IMGARRAY = "ImgArray"

        val TABLE_IMG_NAME = "Images"
        val COL_IMG_ID = "Id"
        val COL_IMG_IMG = "IMAGEN"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE_QUERY:String = ("CREATE TABLE IF NOT EXISTS  $TABLE_NAME ( $COL_ID INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_PASSWORD TEXT, $COL_DATE TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)

        val CREATE_BOOKS_TABLE_QUERY:String = ("CREATE TABLE IF NOT EXISTS  $TABLE_BOOKS_NAME ( " +
                "$COL_BOOKS_ID INTEGER PRIMARY KEY, " +
                "$COL_BOOKS_USER INTEGER, " +
                "$COL_BOOKS_TITLE TEXT, " +
                "$COL_BOOKS_DESCRIPTION TEXT, " +
                "$COL_BOOKS_IDIMAGE INTEGER, " +
                "$COL_BOOKS_IDGENRE INTEGER, " +
                "$COL_BOOKS_TITLEGENRE TEXT, " +
                "$COL_BOOKS_IMGARRAY TEXT, " +
                "FOREIGN KEY($COL_BOOKS_USER) REFERENCES $TABLE_NAME($COL_ID)" +
                "FOREIGN KEY($COL_BOOKS_IDIMAGE) REFERENCES $TABLE_IMG_NAME($COL_IMG_ID))")
        db!!.execSQL(CREATE_BOOKS_TABLE_QUERY)

        val CREATE_IMAGES_TABLE_QUERY:String = ("CREATE TABLE IF NOT EXISTS  $TABLE_IMG_NAME ( $COL_IMG_ID INTEGER PRIMARY KEY, $COL_IMG_IMG BLOB)")
        db!!.execSQL(CREATE_IMAGES_TABLE_QUERY)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    fun onCheckUserName(username: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery("select * from User where UserName = ?", arrayOf(username))
        var flag = false
        if (cursor.count > 0) {
            flag = true
        }
        return flag
    }

    fun onCheckUserNameAndPassword(username: String, password: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery(
            "select * from User where UserName = ? And Password = ?",
            arrayOf(username, password)
        )
        var flag = false
        if (cursor.count > 0) {
            flag = true
        }
        return flag
    }

}