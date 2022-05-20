package com.psm.horrorg

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.psm.horrorg.Db.DbHelper
import com.psm.horrorg.Db.dbImages
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.Usuario

class ListadoCapitulosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_capitulos)



    }

}