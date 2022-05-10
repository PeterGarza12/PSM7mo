package com.psm.horrorg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.psm.horrorg.Db.DbHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val DB = DbHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txtbtnCrear = findViewById<View>(R.id.btnCrear)
        txtbtnCrear.setOnClickListener{
            val dbHelper = DbHelper(this)
            val db = dbHelper.writableDatabase
            if(db !=null){
                Toast.makeText(this,"Base de datos creada",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Error al crear la base de datos",Toast.LENGTH_LONG).show();
            }
        }
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
            val checkuserpass:Boolean = DB.onCheckUserNameAndPassword(this.input_username.text.toString(),this.input_password.text.toString())
            if(checkuserpass){
                Toast.makeText(this, "Ingreso correctamente", Toast.LENGTH_SHORT).show()
                //Cambié el homeactivity por el drawer

                val intent = Intent(this, DrawerActivity::class.java)
                intent.putExtra("user", this.input_username.text.toString())
                startActivity(intent)
            }else{
                Toast.makeText(this, "Error al iniciar seson, ingrese los datos nuevamente", Toast.LENGTH_SHORT).show()
            }

        }
    }
}