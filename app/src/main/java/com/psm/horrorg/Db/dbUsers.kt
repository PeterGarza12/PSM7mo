package com.psm.horrorg.Db

import android.content.Context

import android.content.ContentValues
import android.database.Cursor
import com.psm.horrorg.Model.User
import java.lang.Exception
import java.util.ArrayList

class dbUsers(var context: Context?) : DbHelper(context) {

    fun validarCorreoUnico(email: String?): Boolean {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var unique = false

        val cursorUser: Cursor
        cursorUser = db.rawQuery(
            "select * from User where $COL_EMAIL = ? LIMIT 1",
            arrayOf(email)
        )
        if (cursorUser.moveToFirst()) {
            unique = true
        }
        cursorUser.close()
        return unique
    }

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

    fun modificarUser(username: String?,password: String?,date: String?): Boolean {

        var correcto:Boolean = false
        val dbHelper = DbHelper(context!!)

        val db = dbHelper.writableDatabase
        try {
            correcto=true
            db.execSQL("UPDATE User SET Password='"+password+"',DateBirth = '"+date+"' WHERE UserName ='"+username+"'")
        } catch (ex: Exception) {
            ex.toString()
            correcto=false
        }finally {
            db.close()
        }
        return correcto

    }
}