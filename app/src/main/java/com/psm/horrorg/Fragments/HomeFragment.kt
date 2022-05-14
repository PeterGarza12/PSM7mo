package com.psm.horrorg.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.psm.horrorg.Adapter.AdaptadorLibros
import com.psm.horrorg.BookActivity
import com.psm.horrorg.BookRecycleAdapter
import com.psm.horrorg.Data.ALBUM_POSITION
import com.psm.horrorg.Data.DEFAULT_ALBUM_POSITION
import com.psm.horrorg.Data.DataManager
import com.psm.horrorg.Data.LOREMIPSUM
import com.psm.horrorg.DrawerActivity
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.Usuario
import com.psm.horrorg.R
import kotlinx.android.synthetic.main.content_list.*

class HomeFragment: Fragment(){

    private var context2: Context? = null

    override fun onAttach(context: Context){
        super.onAttach(context)
        this.context2 = context
    }


    private val libros = mutableListOf<Libros>()
    private var librosAdaptador: AdaptadorLibros? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        Toast.makeText(this.context2!!, Usuario.getUsername(), Toast.LENGTH_SHORT).show()

        this.librosAdaptador = AdaptadorLibros(libros, this.context2!!)
        getLibros(view)

        view.findViewById<FloatingActionButton>(R.id.fab_CreateBook).setOnClickListener { view ->

            val  activityIntent =  Intent(this.context2!!,BookActivity::class.java)
            activityIntent.putExtra(ALBUM_POSITION,DEFAULT_ALBUM_POSITION)
            startActivity(activityIntent)


        }

        return view
    }

    private fun getLibros(view: View) {
        Toast.makeText(context, "Sí entraaaaa", Toast.LENGTH_SHORT).show()

        val rv_grupos = view.findViewById<RecyclerView>(R.id.rv_libros)
        // Linea para hacer el recycler horizontal
        // rv_grupos.layoutManager = LinearLayoutManager(this.context2!!, LinearLayoutManager.HORIZONTAL, false)
        rv_grupos.adapter = librosAdaptador

        libros.clear()

        var libro = Libros()
        libro.strTitle =  "Please Please Me"
        libro.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        libro.intIdImage = R.drawable.portada3
        libro.genre =  DataManager.genres[0]

        libros.add(libro)

        libro = Libros()
        libro.strTitle =  "With The Beatles"
        libro.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        libro.intIdImage = R.drawable.portada4
        libro.genre =  DataManager.genres[2]

        libros.add(libro)

        libro = Libros()
        libro.strTitle =  "A Hard Day Night"
        libro.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        libro.intIdImage = R.drawable.portada5
        libro.genre =  DataManager.genres[3]

        libros.add(libro)

        libro = Libros()
        libro.strTitle =  "Please Please Me"
        libro.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        libro.intIdImage = R.drawable.portada6
        libro.genre =  DataManager.genres[4]

        libros.add(libro)

        libro = Libros()
        libro.strTitle =  "With The Beatles"
        libro.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        libro.intIdImage = R.drawable.portada7
        libro.genre =  DataManager.genres[4]

        libros.add(libro)

        libro = Libros()
        libro.strTitle =  "A Hard Day Night"
        libro.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        libro.intIdImage = R.drawable.portada10
        libro.genre =  DataManager.genres[1]

        libros.add(libro)

        rv_grupos.smoothScrollToPosition(0)

    }


}