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
        val TABLE_NAME = "USER"
        val COL_ID = "USERID"
        val COL_USERNAME = "USERNAME"
        val COL_PASSWORD = "PASSWORD"
        val COL_DATE = "BIRTHDAY"
        val COL_IMAGE = "IMAGE"
        val COL_EMAIL = "EMAIL"
        val COL_NAME = "NAME"

        //Books Table
        val TABLE_BOOKS_NAME = "BOOKS"
        val COL_BOOKS_ID = "BOOKID"
        val COL_BOOKS_USER = "USERID"
        val COL_BOOKS_TITLE = "TITLE"
        val COL_BOOKS_DESCRIPTION = "DESCRIPTION"
        val COL_BOOKS_IDIMAGE = "IMAGEID"
        val COL_BOOKS_IDGENRE = "GENREID"
        val COL_BOOKS_TITLEGENRE = "GENRETITLE"
        val COL_BOOKS_IMGARRAY = "IMGARRAY"

        //Img table
        val TABLE_IMG_NAME = "IMAGES"
        val COL_IMG_ID = "IDIMAGE"
        val COL_IMG_IMG = "IMAGE"


        //Chapters Table
        val TABLE_CH_NAME = "CHAPTERS"
        val COL_CH_ID = "CHAPTERSID"
        val COL_CH_TITLE = "CHAPTERSTITLE"
        val COL_CH_BODY = "BODY"
        val COL_CH_IMAGE = "IMAGE"
        val COL_CH_BOOKID = "BOOKID"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE_QUERY:String = ("CREATE TABLE IF NOT EXISTS  $TABLE_NAME ( " +
                "$COL_ID INTEGER PRIMARY KEY, " +
                "$COL_USERNAME TEXT, " +
                "$COL_PASSWORD TEXT, " +
                "$COL_DATE TEXT, " +
                "$COL_IMAGE BLOB, " +
                "$COL_NAME TEXT, " +
                "$COL_EMAIL TEXT)")
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

        val CREATE_CHAPTERS_TABLE_QUERY:String = ("CREATE TABLE IF NOT EXISTS  $TABLE_CH_NAME ( " +
                "$COL_CH_ID INTEGER PRIMARY KEY, " +
                "$COL_CH_TITLE TEXT, " +
                "$COL_CH_BODY TEXT, " +
                "$COL_CH_IMAGE BLOB, " +
                "$COL_CH_BOOKID INTEGER, " +
                "FOREIGN KEY($COL_CH_BOOKID) REFERENCES $TABLE_BOOKS_NAME($COL_BOOKS_ID)" +
                ")")
        db!!.execSQL(CREATE_CHAPTERS_TABLE_QUERY)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    /*fun onCheckUserName(username: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery("select * from User where UserName = ?", arrayOf(username))
        var flag = false
        if (cursor.count > 0) {
            flag = true
        }
        return flag
    }*/



}