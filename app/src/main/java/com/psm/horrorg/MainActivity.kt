package com.psm.horrorg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.psm.horrorg.Db.DbHelper
import com.psm.horrorg.Db.dbUsers
import com.psm.horrorg.Model.User
import com.psm.horrorg.Model.User2
import com.psm.horrorg.Model.Usuario
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    val DB = DbHelper(this)
    val DbUsers= dbUsers(this)
    var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txtbtnCrear = findViewById<View>(R.id.btnCrear)

        txtbtnCrear.setOnClickListener{
            val dbHelper = DbHelper(this)
            val db = dbHelper.writableDatabase
            if(db !=null){
                Toast.makeText(this,"Base de datos creada",Toast.LENGTH_LONG).show();
            }
            else{
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
        }
        else{
            //val checkuserpass:Boolean = DB.onCheckUserNameAndPassword(this.input_username.text.toString(),this.input_password.text.toString())
            val checkuserpass = true
            if(checkuserpass){
                Toast.makeText(this, "Ingreso correctamente", Toast.LENGTH_SHORT).show()

                retrieveUser()
                /*user = DbUsers.verUser(this.input_username.text.toString())
                if(user!=null){
                    Usuario.setUsuario(user.id, user.username.toString(), user.password.toString(), user.dateBirth.toString())
                }*/

                val intent = Intent(this, DrawerActivity::class.java)
                intent.putExtra("user", this.input_username.text.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Error al iniciar seson, ingrese los datos nuevamente", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun retrieveUser(){
        //val intId:Int =  txtId!!.text.toString().toInt()
        val username:String = this.input_username.text.toString()
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<User2>> = service.getUser(username)

        result.enqueue(object: Callback<List<User2>> {
            override fun onFailure(call: Call<List<User2>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<User2>>, response: Response<List<User2>>) {
                //var strMessage:String =  ""
                //var byteArray:ByteArray? = null
                val item =  response.body()
                if (item != null){

                    val hola = item[0].USERID.toString()

                    Usuario.setUsuario(item[0].USERID!!.toInt(), item[0].USERNAME.toString(), item[0].PASS.toString(), item[0].BIRTHDAY.toString())
                    Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_LONG).show()

                    //val strImage:String =  item[0].imgArray!!.replace("data:image/png;base64,","")
                    //byteArray =  Base64.getDecoder().decode(strImage)

                }
                /*if(byteArray != null){
                    imageUI!!.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
                }
                txtMessage!!.setText(strMessage)*/
                Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }
}