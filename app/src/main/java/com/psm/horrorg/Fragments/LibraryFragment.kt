package com.psm.horrorg.Fragments

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.psm.horrorg.Adapter.AdaptadorLibros
import com.psm.horrorg.BookActivity
import com.psm.horrorg.Data.ALBUM_POSITION
import com.psm.horrorg.Data.DEFAULT_ALBUM_POSITION
import com.psm.horrorg.Db.DbHelper
import com.psm.horrorg.Db.dbImages
import com.psm.horrorg.Model.DataBook
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.User2
import com.psm.horrorg.Model.Usuario
import com.psm.horrorg.R
import com.psm.horrorg.RestEngine
import com.psm.horrorg.Service
import okio.ByteString.Companion.decodeBase64
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.util.*

class LibraryFragment: Fragment(), SearchView.OnQueryTextListener{

    private var context2: Context? = null


    override fun onAttach(context: Context){
        super.onAttach(context)
        this.context2 = context
    }


    lateinit var txtBuscar: SearchView
    private var libros = mutableListOf<Libros>()
    private var librosAdaptador: AdaptadorLibros? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_biblioteca, container, false)

        var dbhelper = DbHelper(this.context2!!)
        var dbImg = dbImages(this.context2!!)
        txtBuscar=view.findViewById(R.id.sv_library)
        txtBuscar.setOnQueryTextListener(this)

        this.librosAdaptador = AdaptadorLibros(libros, this.context2!!)
        getLibros(view, dbhelper, dbImg)


        return view
    }

    private fun getLibros(view: View, dbHelper: DbHelper, dbimg: dbImages) {
        Toast.makeText(context, "Sí entraaaaa", Toast.LENGTH_SHORT).show()

        val rv_libros = view.findViewById<RecyclerView>(R.id.rv_library)
        // Linea para hacer el recycler horizontal
        // rv_grupos.layoutManager = LinearLayoutManager(this.context2!!, LinearLayoutManager.HORIZONTAL, false)

        rv_libros.adapter = librosAdaptador

        libros.clear()
        var libro: Libros

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<DataBook>> = service.getBooks()

        result.enqueue(object: Callback<List<DataBook>> {
            override fun onFailure(call: Call<List<DataBook>>, t: Throwable) {

                Toast.makeText(context2!!,"Ingresando de manera local",Toast.LENGTH_LONG).show()

            }//Aquí termina el onfailure

            override fun onResponse(call: Call<List<DataBook>>, response: Response<List<DataBook>>) {

                val itemArray =  response.body()
                //Si sí trajo el objeto
                if (itemArray!!.isNotEmpty()){

                    for(item in itemArray){
                        libro = Libros()
                        libro.libroId = item.BOOKID!!.toInt()
                        libro.userId = item.USERID!!.toInt()
                        libro.strTitle =  item.TITLE
                        libro.strDescription = item.DESCRIPTION
                        //
                        val strImage:String =  item.IMAGE!!.replace("data:image/png;base64,","")
                        val byteArray =  Base64.getDecoder().decode(strImage)
                        val bitmap : Bitmap? = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                        libro.imgArray = bitmap
                        libro.genre =  item.GENRETITLE

                        libros.add(libro)
                    }
                    rv_libros.adapter?.notifyDataSetChanged()


                }
                //Si no encontró el objeto seguro se equivoco en el correo
                else{
                    Toast.makeText(context2!!,"No se encontró al usuario",Toast.LENGTH_LONG).show()
                }
            } //Acá termina el onresponse
        }) //Aqui termina el resut

        /*try {
            val db = dbHelper.readableDatabase

            val cursorUser: Cursor
            cursorUser = db.rawQuery(
                "select * from BOOKS", null
            )

            if (cursorUser.moveToFirst()) {
                do{

                    libro = Libros()
                    libro.libroId = cursorUser.getInt(0)
                    libro.userId = cursorUser.getInt(1)
                    libro.strTitle =  cursorUser.getString(2)
                    libro.strDescription = cursorUser.getString(3)
                    //
                    libro.intIdImage = cursorUser.getInt(4)
                    libro.imgArray = dbimg.getImage(libro.intIdImage)
                    var catId = cursorUser.getInt(5)
                    libro.genre =  dbimg.getCat(catId)

                    libros.add(libro)
                }
                while (cursorUser.moveToNext())
            }
            cursorUser.close()

        } catch (ex: Exception) {
            ex.toString()
            Toast.makeText(this.context2!!, ex.toString(), Toast.LENGTH_SHORT).show()
        }*/
        rv_libros.smoothScrollToPosition(0)
    }




    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            if(librosAdaptador != null) this.librosAdaptador?.filter?.filter(newText)//evaluas con el filtrado del adapter
        }else{

        }
        return false

    }

}

