package com.psm.horrorg

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.psm.horrorg.Adapter.AdaptadorCapitulos
import com.psm.horrorg.Adapter.AdaptadorLibros
import com.psm.horrorg.Db.DbHelper
import com.psm.horrorg.Db.dbImages
import com.psm.horrorg.Model.Chapters
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.Usuario
import com.psm.horrorg.Model.chapter
import kotlinx.android.synthetic.main.content_list.*

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

        capitulos.clear()
        var capitulo: Chapters


        try {
            val db = dbHelper.readableDatabase

            val cursorUser: Cursor
            cursorUser = db.rawQuery(
                "select * from CHAPTERS where BOOKID = ?",
                arrayOf(.toString())
            )

            if (cursorUser.moveToFirst()) {
                do{

                    capitulo = Chapters()
                    capitulo.bookId = cursorUser.getInt(0)
                    capitulo.id = cursorUser.getInt(1)
                    capitulo.strTitle =  cursorUser.getString(2)
                    capitulo.strBody = cursorUser.getString(3)
                    //
                    capitulo.intIdImage = cursorUser.getInt(4)
                    capitulo.imgArray = dbimg.getImage(capitulo.intIdImage)
                    var catId = cursorUser.getInt(5)


                    capitulos.add(capitulo)
                }
                while (cursorUser.moveToNext())
            }
            cursorUser.close()

        } catch (ex: Exception) {
            ex.toString()
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()
        }

        rv_gruposCapitulos.smoothScrollToPosition(0)

    }


}