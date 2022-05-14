package com.psm.horrorg

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.text.set
import com.psm.horrorg.Db.DbHelper
import com.psm.horrorg.Db.dbUsers
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btn_Login.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v!!.id== R.id.btn_Login){
           showInfo()
        }
    }

    private fun showInfo(){
        if(this.input_username.text.toString() =="" && this.input_password.text.toString()=="" && this.input_password2.text.toString()==""){
            Toast.makeText(this,"Favor de llenar los campos",Toast.LENGTH_LONG).show();
        }else if(this.input_password.text.toString()!=this.input_password2.text.toString()){
            Toast.makeText(this,"Las contraseñas tienen que ser iguales",Toast.LENGTH_LONG).show();
        }else{

            var Dbusers = dbUsers(this@RegisterActivity)
            var id:Long =Dbusers.insertarUsuario(this.input_username.text.toString(),this.input_password.text.toString(),this.input_password2.text.toString())

            if(id>0){

                Toast.makeText(this,"Usuario creado",Toast.LENGTH_LONG).show();
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)

                //val intent=Intent(this,DrawerActivity::class.java)
                //intent.putExtra("user",this.input_username.text.toString())

            }
            else{
                Toast.makeText(this,"Error al crear el usuario",Toast.LENGTH_LONG).show();
                clean()
            }

        }
    }
    private fun clean(){

    }

}