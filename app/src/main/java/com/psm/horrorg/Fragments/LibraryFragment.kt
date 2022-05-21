package com.psm.horrorg.Fragments

import android.content.Context
import android.content.Intent
import android.database.Cursor
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

        val rv_grupos = view.findViewById<RecyclerView>(R.id.rv_library)
        // Linea para hacer el recycler horizontal
        // rv_grupos.layoutManager = LinearLayoutManager(this.context2!!, LinearLayoutManager.HORIZONTAL, false)

        rv_grupos.adapter = librosAdaptador

        libros.clear()
        var libro: Libros

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<DataBook>> = service.getBooks()

        result.enqueue(object: Callback<List<DataBook>> {
            override fun onFailure(call: Call<List<DataBook>>, t: Throwable) {

            }//Aquí termina el onfailure

            override fun onResponse(call: Call<List<DataBook>>, response: Response<List<DataBook>>) {

                val arrayItems =  response.body()
                //Si sí trajo el objeto
                if (arrayItems!!.isNotEmpty()){
                    for (item in arrayItems!!){
                        libro = Libros()
                        libro.libroId = item.BOOKID!!.toInt()
                        libro.userId = item.USERID!!.toInt()
                        libro.strTitle =  item.TITLE
                        libro.strDescription = item.DESCRIPTION
                        //
                        var byteArray:ByteArray? = null
                        val strImage:String =  item.IMAGE!!.replace("data:image/png;base64,","")
                        byteArray =  Base64.getDecoder().decode(strImage)

                        val array = ByteArrayInputStream(byteArray)
                        val bitmap = BitmapFactory.decodeStream(array)

                        libro.imgArray = bitmap

                        var catId = item.GENREID!!.toInt()
                        libro.genre =  dbimg.getCat(catId)

                        libros.add(libro)
                    }
                }
            } //Acá termina el onresponse
        }) //Aqui termina el resut
        rv_grupos.smoothScrollToPosition(0)
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }


}

