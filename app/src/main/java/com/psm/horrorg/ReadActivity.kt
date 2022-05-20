package com.psm.horrorg

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.psm.horrorg.Model.Libro
import com.psm.horrorg.Model.chapter

class ReadActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        val tvTitle = findViewById<TextView>(R.id.textView3)
        val tvBody = findViewById<TextView>(R.id.textView14)
        val tvImage = findViewById<ImageView>(R.id.imageView8)

        tvTitle.text = chapter.getTitle()
        tvBody.text = chapter.getBody()
        tvImage.setImageBitmap(chapter.getimgArray())


    }
}