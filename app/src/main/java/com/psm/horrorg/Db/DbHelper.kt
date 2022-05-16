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

        //Img table
        val TABLE_IMG_NAME = "Images"
        val COL_IMG_ID = "Id"
        val COL_IMG_IMG = "Imagen"

        //Genre Table
        val TABLE_CAT_NAME = "Categories"
        val COL_CAT_ID = "Id"
        val COL_CAT_CAT = "Category"

        //Chapters Table
        val TABLE_CH_NAME = "Chapters"
        val COL_CH_ID = "Id"
        val COL_CH_TITLE = "Titulo"
        val COL_CH_BODY = "Cuerpo"
        val COL_CH_IMAGE = "Imagen"
        val COL_CH_BOOKID = "BookId"
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

        val CREATE_CAT_TABLE_QUERY:String = ("CREATE TABLE IF NOT EXISTS  $TABLE_CAT_NAME ( $COL_CAT_ID INTEGER PRIMARY KEY, $COL_CAT_CAT BLOB)")
        db!!.execSQL(CREATE_CAT_TABLE_QUERY)

        var values = ContentValues()
        values.put(COL_CAT_CAT, "Suspenso")
        db.insert(TABLE_CAT_NAME, null, values)

        values = ContentValues()
        values.put(COL_CAT_CAT, "Criminología")
        db.insert(TABLE_CAT_NAME, null, values)

        values = ContentValues()
        values.put(COL_CAT_CAT, "Creepypastas")
        db.insert(TABLE_CAT_NAME, null, values)

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