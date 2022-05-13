package com.psm.horrorg.Db

import android.content.Context

import android.content.ContentValues
import android.database.Cursor
import com.psm.horrorg.Model.User
import java.lang.Exception
import java.util.ArrayList

class dbUsers(var context: Context?) : DbHelper(context) {

    fun insertarUsuario(username: String?, password: String?, dateBirth: String?): Long {
        var id: Long = 0
        try {
            val dbHelper = DbHelper(context!!)

            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("UserName", username)
            values.put("Password", password)
            values.put("DateBirth", dateBirth)
            id = db.insert(TABLE_NAME, null, values)
        } catch (ex: Exception) {
            ex.toString()
        }
        return id
    }

    fun verUser(username: String?): User {
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
    }
}