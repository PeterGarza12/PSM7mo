package com.psm.horrorg

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.view.View
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
import java.util.function.BinaryOperator

class MainActivity : AppCompatActivity() {

    val DB = DbHelper(this)
    val DbUsers= dbUsers(this)
    lateinit var user: User

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

        if(this.input_main_mail.text.toString() =="" || this.input_password.text.toString()=="" ){
            Toast.makeText(this, "Ingresar información de usuario", Toast.LENGTH_SHORT).show()
        }
        else{

            val islogged: Boolean = retrieveUser(this.input_main_mail.text.toString(), this.input_password.text.toString())

            if(islogged) {
                val intent = Intent(this, DrawerActivity::class.java)
                startActivity(intent)
            }

            //val checkuserpass:Boolean = DbUsers.onCheckEmailAndPassword(this.input_main_mail.text.toString(),this.input_password.text.toString())

            /*if(checkuserpass){
                Toast.makeText(this, "Ingreso correctamente", Toast.LENGTH_SHORT).show()

                //retrieveUser()
                user = DbUsers.verUser(this.input_main_mail.text.toString())

                if(user!=null){

                    Usuario.setUsuario(user.USERID, user.USERNAME, user.PASS, user.BIRTHDAY,user.IMAGE, user.NAME, user.EMAIL)

                }

                val intent = Intent(this, DrawerActivity::class.java)
                intent.putExtra("user", this.input_main_mail.text.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Error al iniciar seson, ingrese los datos nuevamente", Toast.LENGTH_SHORT).show()
            }*/

        }
    }

    private fun retrieveUser(email: String, password: String): Boolean{

        var succeed = false

        user = DbUsers.verUser(email)

        if(user!=null){

            Usuario.setUsuario(user.USERID, user.USERNAME, user.PASS, user.BIRTHDAY,user.IMAGE, user.NAME, user.EMAIL)
            succeed = true
        }
        else{
            Toast.makeText(this@MainActivity,"Usuario no encontrado",Toast.LENGTH_LONG).show()
        }

        /*val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<User2>> = service.getUser(email)

        result.enqueue(object: Callback<List<User2>> {
            override fun onFailure(call: Call<List<User2>>, t: Throwable) {

                Toast.makeText(this@MainActivity,"Ingresando de manera local",Toast.LENGTH_LONG).show()

            }//Aquí termina el onfailure

            override fun onResponse(call: Call<List<User2>>, response: Response<List<User2>>) {

                val item =  response.body()
                //Si sí trajo el objeto
                if (item != null){

                    //Si la contraseña del objeto coincide con la ingresada setea al usuario
                    if(item[0].PASS.toString() == password){

                        val image = item[0].IMAGE
                        val bitmap = BitmapFactory.decodeByteArray(image, 0, image!!.size)


                        Usuario.setUsuario(item[0].USERID!!.toInt(), item[0].USERNAME!!.toString(), item[0].PASS!!.toString(),
                            item[0].BIRTHDAY!!.toString(), bitmap, item[0].NAME!!.toString(), item[0].EMAIL!!.toString())

                        Toast.makeText(this@MainActivity,"Login con éxito",Toast.LENGTH_LONG).show()

                        succeed = true
                    }
                    //Si no coincide le avisa
                    else{
                        Toast.makeText(this@MainActivity,"Credenciales equivocadas",Toast.LENGTH_LONG).show()
                    }
                }
                //Si no encontró el objeto seguro se equivoco en el correo
                else{
                    Toast.makeText(this@MainActivity,"No se encontró al usuario",Toast.LENGTH_LONG).show()
                }
            } //Acá termina el onresponse
        }) //Aqui termina el resut*/

        return succeed
    }
}