package com.psm.horrorg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.psm.horrorg.Data.ALBUM_POSITION
import com.psm.horrorg.Model.Libros

class SinopsisBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sinopsis_book)

        var data = 100
        val extras = intent.extras
        if (extras != null){
            data = extras!!.getInt(ALBUM_POSITION)
        }

        val gson = Gson()
        val miLibro = gson.fromJson<Libros>(intent.getStringExtra("Libro"), Libros::class.java)


        val tvTitle = findViewById<TextView>(R.id.txt_titulo)
        val tvCategory = findViewById<TextView>(R.id.txt_Categoria)
        val tvDescription = findViewById<TextView>(R.id.txt_Descripcion)

        tvTitle.text = miLibro.strTitle
        tvCategory.text = miLibro.genre.toString()
        tvDescription.text = miLibro.strDescription



        val btnRead = findViewById<Button>(R.id.btn_Read)
        btnRead.setOnClickListener {
            Toast.makeText(this, miLibro.strTitle, Toast.LENGTH_SHORT).show()
            //val intent = Intent(this, ReadActivity::class.java)
            //startActivity(intent)
        }
    }
}