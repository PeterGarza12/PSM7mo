package com.psm.horrorg.Db

import android.content.Context

import android.content.ContentValues
import android.database.Cursor
import android.widget.Toast
import com.psm.horrorg.Data.DataManager
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.User
import com.psm.horrorg.Model.Usuario
import java.lang.Exception
import java.util.ArrayList

class dbBooks(var context: Context?) : DbHelper(context) {


    fun insertBook(UserId: Int?, Title: String?, Description: String?,
                   IdImage: Int?, IdGenre: Int?, TitleGenre: String?,
                   ImgArray: String?): Long {
        var id: Long = 0
        try {
            val dbHelper = DbHelper(context!!)

            val db = dbHelper.writableDatabase

            val values = ContentValues()
            values.put("$COL_BOOKS_USER", UserId)
            values.put("$COL_BOOKS_TITLE", Title)
            values.put("$COL_BOOKS_DESCRIPTION", Description)
            values.put("$COL_BOOKS_IDIMAGE", IdImage)
            values.put("$COL_BOOKS_IDGENRE", IdGenre)
            values.put("$COL_BOOKS_TITLEGENRE", TitleGenre)
            values.put("$COL_BOOKS_IMGARRAY", ImgArray)

            id = db.insert(TABLE_BOOKS_NAME, null, values)

        } catch (ex: Exception) {
            ex.toString()
            Toast.makeText(context!!, ex.toString(), Toast.LENGTH_SHORT).show()
        }

        return id
    }

   /* fun showMyBooks(userId: Int?): MutableList<Libros> {
        var myBooks = mutableListOf<Libros>()
        myBooks.clear()
        var book = Libros()


        try {
            val dbHelper = DbHelper(context!!)

            val db = dbHelper.readableDatabase

            val cursorUser: Cursor
            cursorUser = db.rawQuery(
                "select * from Books where UserId = ?",
                arrayOf((userId).toString())
            )

            if (cursorUser.moveToFirst()) {
                do{

                    book = Libros()
                    book.strTitle =  cursorUser.getString(2)
                    book.strDescription = cursorUser.getString(3)
                    //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
                    book.intIdImage = cursorUser.getInt(4)
                    book.genre =  DataManager.genres[cursorUser.getInt(5)]

                    myBooks.add(book)
                }
                    while (cursorUser.moveToNext())
            }
            cursorUser.close()

        } catch (ex: Exception) {
            ex.toString()
            Toast.makeText(context!!, ex.toString(), Toast.LENGTH_SHORT).show()
        }

        return myBooks
    }*/
}