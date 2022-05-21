package com.psm.horrorg.Db

import android.content.Context

import android.content.ContentValues
import android.database.Cursor
import android.widget.Toast
import com.psm.horrorg.Data.DataManager
import com.psm.horrorg.Model.*
import com.psm.horrorg.RestEngine
import com.psm.horrorg.Service
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*

class dbChapters(var context: Context?) : DbHelper(context) {


    fun insertChapter(Title: String?, Body: String?, Image: ByteArray?, BookId: Int?): Long {
        var id: Long = 0
        try {
            val dbHelper = DbHelper(context!!)

            val db = dbHelper.writableDatabase

            val values = ContentValues()
            values.put("$COL_CH_TITLE", Title)
            values.put("$COL_CH_BODY", Body)
            values.put("$COL_CH_IMAGE", Image)
            values.put("$COL_CH_BOOKID", BookId)

            id = db.insert(TABLE_CH_NAME, null, values)

            insertingChapter(Title, Body, Image, BookId)

        } catch (ex: Exception) {
            ex.toString()
            Toast.makeText(context!!, ex.toString(), Toast.LENGTH_SHORT).show()
        }

        return id
    }

    fun insertingChapter(Title: String?, Body: String?, Image: ByteArray?, BookId: Int?){


        val encodedString:String =  Base64.getEncoder().encodeToString(Image)
        val strEncodeImage:String = "data:image/png;base64," + encodedString

        //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
        // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
        val chapterd =  DataChapter(0,
            Title,
            Body,
            strEncodeImage,
            BookId
        )

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Int> = service.saveChapters(chapterd)

        result.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(context!!,"Error",Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(context!!,"OK",Toast.LENGTH_LONG).show()
            }
        })

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