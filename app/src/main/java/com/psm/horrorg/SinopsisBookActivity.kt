package com.psm.horrorg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.psm.horrorg.Data.ALBUM_POSITION

class SinopsisBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sinopsis_book)

        var data = 100
        val extras = intent.extras
        if (extras != null){
            data = extras!!.getInt(ALBUM_POSITION)
        }





        val btnRead = findViewById<Button>(R.id.btn_Read)
        btnRead.setOnClickListener {
            Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
            //val intent = Intent(this, ReadActivity::class.java)
            //startActivity(intent)
        }
    }
}