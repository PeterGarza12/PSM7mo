package com.psm.horrorg

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.psm.horrorg.Adapter.AdaptadorCapitulos
import com.psm.horrorg.Adapter.AdaptadorLibros
import com.psm.horrorg.Db.DbHelper
import com.psm.horrorg.Db.dbImages
import com.psm.horrorg.Model.*
import kotlinx.android.synthetic.main.content_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ListadoCapitulosActivity : AppCompatActivity() {

    private var capitulos = mutableListOf<Chapters>()
    private var capitulosAdaptador: AdaptadorCapitulos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_listado_capitulos)
        setSupportActionBar(findViewById(R.id.toolbar))

        var dbhelper = DbHelper(this)
        var dbImg = dbImages(this)

        this.capitulosAdaptador = AdaptadorCapitulos(capitulos, this)
        getCapitulos(this, dbhelper, dbImg)



    }
    private fun getCapitulos(view: ListadoCapitulosActivity, dbHelper: DbHelper, dbimg: dbImages) {

        val rv_gruposCapitulos = view.findViewById<RecyclerView>(R.id.rv_capitulos)
        // Linea para hacer el recycler horizontal
        // rv_grupos.layoutManager = LinearLayoutManager(this.context2!!, LinearLayoutManager.HORIZONTAL, false)

        rv_gruposCapitulos.adapter = capitulosAdaptador
        var idBook = intent.getStringExtra("IDBook") ?: "sin_id"

        capitulos.clear()
        var capitulo: Chapters

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<DataChapter>> = service.getChapters()

        result.enqueue(object: Callback<List<DataChapter>> {
            override fun onFailure(call: Call<List<DataChapter>>, t: Throwable) {

                Toast.makeText(this@ListadoCapitulosActivity,"Ingresando de manera local",Toast.LENGTH_LONG).show()

            }//Aquí termina el onfailure

            override fun onResponse(call: Call<List<DataChapter>>, response: Response<List<DataChapter>>) {

                val itemArray =  response.body()
                //Si sí trajo el objeto
                if (itemArray!!.isNotEmpty()){

                    for(item in itemArray){
                        if(item.BOOKID!!.toInt() == Libro.getId())
                        {

                            capitulo = Chapters()
                            capitulo.id = item.CHAPTERSID!!.toInt()
                            capitulo.strTitle = item.CHAPTERSTITLE.toString()
                            capitulo.strBody =  item.BODY.toString()

                            val strImage:String = item.IMAGE!!.replace("data:image/png;base64,","")
                            val byteArray =  Base64.getDecoder().decode(strImage)
                            val bitmap : Bitmap? = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                            //
                            capitulo.imgArray = bitmap
                            capitulo.bookId = Libro.getId()


                            capitulos.add(capitulo)

                        }

                    }
                    rv_gruposCapitulos.adapter?.notifyDataSetChanged()

                }
                //Si no encontró el objeto seguro se equivoco en el correo
                else{
                    Toast.makeText(this@ListadoCapitulosActivity,"No se encontró al usuario",Toast.LENGTH_LONG).show()
                }
            } //Acá termina el onresponse
        })

        /*try {
            val db = dbHelper.readableDatabase

            val cursorUser: Cursor
            cursorUser = db.rawQuery(
                "select * from CHAPTERS where BOOKID = ?",
                arrayOf(Libro.getId().toString())
            )

            if (cursorUser.moveToFirst()) {
                do{

                    capitulo = Chapters()
                    capitulo.id = cursorUser.getInt(0)
                    capitulo.strTitle = cursorUser.getString(1)
                    capitulo.strBody =  cursorUser.getString(2)
                    val image: ByteArray = cursorUser.getBlob(3)
                    capitulo.imgArray = BitmapFactory.decodeByteArray(image, 0, image.size)
                    //
                    capitulo.bookId = cursorUser.getInt(4)


                    capitulos.add(capitulo)
                }
                while (cursorUser.moveToNext())
            }
            cursorUser.close()

        } catch (ex: Exception) {
            ex.toString()
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()
        }*/

        rv_gruposCapitulos.smoothScrollToPosition(0)

    }


}