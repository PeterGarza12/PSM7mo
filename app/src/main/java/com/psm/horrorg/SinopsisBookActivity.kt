package com.psm.horrorg

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.psm.horrorg.Data.ALBUM_POSITION
import com.psm.horrorg.Model.Libros
import com.psm.horrorg.Model.Usuario

class SinopsisBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sinopsis_book)


        val gson = Gson()
        val miLibro = gson.fromJson(intent.getStringExtra("Libro"), Libros::class.java)


        val tvTitle = findViewById<TextView>(R.id.txt_titulo)
        val tvCategory = findViewById<TextView>(R.id.txt_Categoria)
        val tvDescription = findViewById<TextView>(R.id.txt_Descripcion)
        val background = findViewById<ImageView>(R.id.iv_sinopsis_background)

        tvTitle.text = miLibro.strTitle
        tvCategory.text = miLibro.genre.toString()
        tvDescription.text = miLibro.strDescription
        background.setImageBitmap(miLibro.imgArray)



        val btnRead = findViewById<Button>(R.id.btn_Read)
        val btnCreate = findViewById<Button>(R.id.btn_add_chapter)

        if(miLibro.userId == Usuario.getId()){
            btnRead.visibility = View.GONE
            btnCreate.visibility = View.VISIBLE
        }
        else{
            btnCreate.visibility = View.GONE
            btnRead.visibility = View.VISIBLE
        }

        btnRead.setOnClickListener {
            val intent = Intent(this, ReadActivity::class.java)
            startActivity(intent)
        }
        btnCreate.setOnClickListener{
            val intent = Intent(this, CreateChapterActivity::class.java)
            startActivity(intent)
        }
    }
}