package com.psm.horrorg.Db

import android.content.Context

import android.content.ContentValues
import android.database.Cursor
import android.widget.Toast
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
            values.put("UserId", UserId)
            values.put("Title", Title)
            values.put("Description", Description)
            values.put("IdImage", IdImage)
            values.put("IdGenre", IdGenre)
            values.put("TitleGenre", TitleGenre)
            values.put("ImgArray", ImgArray)

            id = db.insert(TABLE_NAME, null, values)

        } catch (ex: Exception) {
            ex.toString()
            Toast.makeText(context!!, ex.toString(), Toast.LENGTH_SHORT).show()
        }

        return id
    }


    /*fun verUser(username: String?): User {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        val user = User()
        val cursorUser: Cursor
        cursorUser = db.rawQuery(
            "select * from User where UserName = ? LIMIT 1",
            arrayOf(username)
        )
        if (cursorUser.moveToFirst()) {
            user.id = cursorUser.getInt(0)
            user.username = cursorUser.getString(1)
            user.password = cursorUser.getString(2)
            user.dateBirth = cursorUser.getString(3)
        }
        cursorUser.close()
        return user
    }*/
}