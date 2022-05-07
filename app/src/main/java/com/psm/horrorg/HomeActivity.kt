package com.psm.horrorg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.psm.horrorg.Data.ALBUM_POSITION
import com.psm.horrorg.Data.DEFAULT_ALBUM_POSITION
import com.psm.horrorg.Data.DataManager
import kotlinx.android.synthetic.main.content_list.*


class HomeActivity : AppCompatActivity(),SearchView.OnQueryTextListener{

    private var albumAdapter:BookRecycleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val extras = intent.extras
        if (extras !=null){
            val strUser = extras?.getString("user")?:"sin dato"
            Log.e("Dato", strUser)
        }
        setSupportActionBar(findViewById(R.id.toolbar))

        DataManager.content =  this
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

            val  activityIntent =  Intent(this,BookActivity::class.java)
            activityIntent.putExtra(ALBUM_POSITION,DEFAULT_ALBUM_POSITION)
            startActivity(activityIntent)
        }

        //RecyclerView
        rcListAlbum.layoutManager =  LinearLayoutManager(this) //lita de elementos en forma vertical
        this.albumAdapter =  BookRecycleAdapter(this, DataManager.albums) //constructor
        rcListAlbum.adapter = this.albumAdapter

        //SearchView
        searchView.setOnQueryTextListener(this)// utilizamos la interface


    }
    fun exampleList(){
        // ejemplos de listOf()
        val primos: List<Int> = listOf(2, 3, 5, 7)
        val nombres: List<String> = listOf("Juan", "Roberto", "María")
        val listaMezclada = listOf("Juan", 1, 2.445, 's')

        //ejemplos mutableListOF
        val mPrimos: MutableList<Int> = mutableListOf(1,2,3)
        mPrimos.add(4)
        val mNombres: MutableList<String> = mutableListOf("Juan", "Roberto", "María")

        //emptyList
        val listaVacia: List<String> = emptyList<String>()

        //listOfNotNull regresa los datos que no son nulos
        val listaNoNulos: List<Int> = listOfNotNull(2, 45, 2, null, 5, null)

    }

    fun findAnElementInAList(){
        var batmans: List<String> = listOf("Christian Bale", "Michael Keaton", "Ben Affleck", "George Clooney")

        //BUSCA EXACTAMENTE EL ELEMENTO
        val theFirstBatman = batmans.find { actor -> "Michael Keaton".equals(actor) }

        if (theFirstBatman != null) {
            theFirstBatman?.toString()?.let { Log.e("theFirstBatman", it) }
        }else{
            Log.e("theFirstBatman", "No se encontro")
        }

        //TRAE NOMBRES QU CONTIENE A
        val theCoolestBatmans = batmans.filter { actor -> actor.contains("A") }

        Log.e("theCoolestBatmans", theCoolestBatmans.toString())
        Log.e("theFirstBatman", "No se encontro")

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            if(albumAdapter != null) this.albumAdapter?.filter?.filter(newText)//evaluas con el filtrado del adapter
        }
        return false

    }

}