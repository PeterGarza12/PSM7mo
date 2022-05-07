package com.psm.horrorg

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class EditProfileUser: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_user_edit)

        val btnCancelar = findViewById<Button>(R.id.btn_edit_cancel)
        val btnUpdate = findViewById<Button>(R.id.btn_edit_update)

        btnCancelar.setOnClickListener {
            Toast.makeText(this, "Se ha cancelado", Toast.LENGTH_SHORT).show()
        }

        btnUpdate.setOnClickListener {
            Toast.makeText(this, "Se ha actualizado", Toast.LENGTH_SHORT).show()
        }
    }
}