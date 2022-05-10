package com.psm.horrorg.Db

import android.content.Context

import android.content.ContentValues
import java.lang.Exception

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
}