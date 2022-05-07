package com.psm.horrorg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_Login.setOnClickListener{
            showInfo()
        }

        btn_Registro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showInfo(){
        if(this.input_username.text.toString() =="" && this.input_password.text.toString()=="" ){
            Toast.makeText(this, "Ingresar información de usuario", Toast.LENGTH_SHORT).show()
        }else{
            //Cambié el homeactivity por el drawer
            val intent = Intent(this, DrawerActivity::class.java)
            intent.putExtra("user", this.input_username.text.toString())
            startActivity(intent)
        }
    }
}