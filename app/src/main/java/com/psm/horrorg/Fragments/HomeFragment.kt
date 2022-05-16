package com.psm.horrorg.Fragments

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.Usuario
import com.psm.horrorg.R

class HomeFragment: Fragment(){

    private var context2: Context? = null


    override fun onAttach(context: Context){
        super.onAttach(context)
        this.context2 = context
    }



    private var libros = mutableListOf<Libros>()
    private var librosAdaptador: AdaptadorLibros? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        var dbhelper = DbHelper(this.context2!!)
        var dbImg = dbImages(this.context2!!)

        this.librosAdaptador = AdaptadorLibros(libros, this.context2!!)
        getLibros(view, dbhelper, dbImg)

        view.findViewById<FloatingActionButton>(R.id.fab_CreateBook).setOnClickListener { view ->

            val  activityIntent =  Intent(this.context2!!,BookActivity::class.java)
            activityIntent.putExtra(ALBUM_POSITION,DEFAULT_ALBUM_POSITION)
            startActivity(activityIntent)


        }

        return view
    }

    private fun getLibros(view: View, dbHelper: DbHelper, dbimg: dbImages) {
        Toast.makeText(context, "Sí entraaaaa", Toast.LENGTH_SHORT).show()

        val rv_grupos = view.findViewById<RecyclerView>(R.id.rv_libros)
        // Linea para hacer el recycler horizontal
        // rv_grupos.layoutManager = LinearLayoutManager(this.context2!!, LinearLayoutManager.HORIZONTAL, false)

        rv_grupos.adapter = librosAdaptador

        libros.clear()
        var libro: Libros


        try {
            val db = dbHelper.readableDatabase
    
            val cursorUser: Cursor
            cursorUser = db.rawQuery(
                "select * from Books where UserId = ?",
                arrayOf(Usuario.getId().toString())
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
        }

        rv_grupos.smoothScrollToPosition(0)

    }


}