package com.psm.horrorg.Db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import com.psm.horrorg.Model.User
import java.lang.Exception
import java.sql.Blob

class dbImages (var context: Context?) : DbHelper(context) {

    fun insertImage(image: ByteArray): Int {

        var id: Int = 0
        var success: Long = 0

        try {
            val dbHelper = DbHelper(context!!)

            var db = dbHelper.writableDatabase

            val values = ContentValues()
            values.put(COL_IMG_IMG, image)

            success = db.insert(TABLE_IMG_NAME, null, values)

            if(success>0){

                db = dbHelper.readableDatabase

                val cursorUser: Cursor
                cursorUser = db.rawQuery(
                    "select * from $TABLE_IMG_NAME order by $COL_IMG_ID desc",
                    null
                )

                if (cursorUser.moveToFirst()) {
                    id = cursorUser.getInt(0)
                }
            }

        } catch (ex: Exception) {
            ex.toString()
            Toast.makeText(context!!, ex.toString(), Toast.LENGTH_SHORT).show()
        }

        return id
    }

    fun getImage(imgId: Int): Bitmap?{
        val dbHelper = DbHelper(context)
        val db = dbHelper.readableDatabase
        var bitmap: Bitmap? = null
        val bytes: ByteArray

        try {
            val cursorUser: Cursor
            cursorUser = db.rawQuery(
                "select $COL_IMG_IMG from $TABLE_IMG_NAME where $COL_IMG_ID = ?",
                arrayOf(imgId.toString())
            )
            if (cursorUser.moveToFirst()) {

                bytes = cursorUser.getBlob(0)
                cursorUser.close()
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            }

        }
        catch (ex: Exception){

        }
        return bitmap
    }
    fun getCat(catId: Int): String{
        val dbHelper = DbHelper(context)
        val db = dbHelper.readableDatabase
        var categoria = "Holi"

        try {
            val cursorUser: Cursor
            cursorUser = db.rawQuery(
                "select $COL_CAT_CAT from $TABLE_CAT_NAME where $COL_CAT_ID = ?",
                arrayOf(catId.toString())
            )
            if (cursorUser.moveToFirst()) {

                categoria = cursorUser.getString(0)
                cursorUser.close()

            }

        }
        catch (ex: Exception){

        }
        return categoria
    }

}