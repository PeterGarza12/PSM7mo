package com.psm.horrorg.Db

import android.content.Context

import android.content.ContentValues
import android.database.Cursor
import android.graphics.BitmapFactory
import com.psm.horrorg.Model.User
import com.psm.horrorg.Model.User2
import com.psm.horrorg.Model.Usuario
import java.lang.Exception
import java.util.ArrayList

class dbUsers(var context: Context?) : DbHelper(context) {

    fun validarCorreoUnico(email: String?): Boolean {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var unique = true

        val cursorUser: Cursor
        cursorUser = db.rawQuery(
            "select * from User where $COL_EMAIL = ? LIMIT 1",
            arrayOf(email)
        )
        if (cursorUser.moveToFirst()) {
            unique = false
        }
        cursorUser.close()
        return unique
    }

    fun insertarUsuario(username: String?, password: String?, dateBirth: String?, image: ByteArray?, name: String?, email: String?): Long {
        var id: Long = 0
        try {
            val dbHelper = DbHelper(context!!)

            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("$COL_USERNAME", username)
            values.put("$COL_PASSWORD", password)
            values.put("$COL_DATE", dateBirth)
            values.put("$COL_IMAGE", image)
            values.put("$COL_NAME", name)
            values.put("$COL_EMAIL", email)

            id = db.insert(TABLE_NAME, null, values)

        } catch (ex: Exception) {
            ex.toString()
        }
        return id
    }

    fun onCheckEmailAndPassword(email: String, password: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery(
            "select * from $TABLE_NAME where $COL_EMAIL = ? And $COL_PASSWORD = ?",
            arrayOf(email, password)
        )
        var flag = false
        if (cursor.count > 0) {
            flag = true
        }
        return flag
    }

    fun verUser(username: String?): User {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        lateinit var user: User

        val cursorUser: Cursor
        cursorUser = db.rawQuery(
            "select * FROM $TABLE_NAME where $COL_EMAIL = ? LIMIT 1",
            arrayOf(username)
        )
        if (cursorUser.moveToFirst()) {
            val id = cursorUser.getInt(0)
            val username = cursorUser.getString(1)
            val password = cursorUser.getString(2)
            val dateBirth = cursorUser.getString(3)
            val image = cursorUser.getBlob(4)
            val name = cursorUser.getString(5)
            val email = cursorUser.getString(6)

            cursorUser.close()
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)

            user = User(id, username, password, dateBirth, bitmap, name, email)
            Usuario.setUsuario(Usuario.getId(), user.USERNAME, user.PASS, user.BIRTHDAY,user.IMAGE, user.NAME, user.EMAIL)
        }

        return user
    }


    fun actualizarUsuario(username: String?, password: String?, dateBirth: String?, image: ByteArray?, name: String?, id: Int, isimage: Boolean): Boolean {

        var correcto = true
        val dbHelper = DbHelper(context!!)

        val db = dbHelper.writableDatabase

        if(isimage){
            try {

                val values = ContentValues()
                values.put("$COL_USERNAME", username)
                values.put("$COL_PASSWORD", password)
                values.put("$COL_DATE", dateBirth)
                values.put("$COL_IMAGE", image)
                values.put("$COL_NAME", name)

                db.update(TABLE_NAME, values, "$COL_ID =?", arrayOf(id.toString()))

            } catch (ex: Exception) {
                ex.toString()
            }
        }
        else{
            db.execSQL("UPDATE $TABLE_NAME SET $COL_USERNAME='"+username+"',$COL_PASSWORD='"+password+"',$COL_DATE='"+dateBirth+"', $COL_NAME = '"+name+"' WHERE $COL_ID = "+id+" ;")
        }

        db.close()


        return correcto
    }

    fun modificarUser(username: String?,password: String?,date: String?) {



    }
}