package com.psm.horrorg

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.psm.horrorg.DatePicker.DatePickerEdit
import com.psm.horrorg.Db.dbUsers
import com.psm.horrorg.Fragments.ProfileUser
import com.psm.horrorg.Model.User

import com.psm.horrorg.Model.Usuario
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_Login
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.profile_user_edit.*

class EditProfileUser: AppCompatActivity(),View.OnClickListener {

    private lateinit var user: User

    var sUser:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_user_edit)



        val txtUsername=findViewById<TextView>(R.id.id_username)
        val txtDate = findViewById<TextView>(R.id.editDate)
        val txtPassword = findViewById<EditText>(R.id.id_password)
        val txtConfirmPassword = findViewById<EditText>(R.id.id_confirmPassword)


        val btnUpdate = findViewById<Button>(R.id.btn_edit_update)

        val DbUsers= dbUsers(this)

        user=DbUsers.verUser(Usuario.getUsername())

        if(user!=null){
            txtUsername.text = Usuario.getUsername()
            txtDate.text= Usuario.getDateBirth()
            txtPassword.setText(Usuario.getPassword())
            txtConfirmPassword.setText(Usuario.getPassword())
        }

        editDate.setOnClickListener{showDatePickerDialog()}
        btn_edit_cancel.setOnClickListener {
            val intent=Intent(this,DrawerActivity::class.java)
            startActivity(intent)

        }

        btnUpdate.setOnClickListener(this)
    }
    private fun showDatePickerDialog() {
        // Creamos un  objeto
        // Hay que mencionar las variables que queremos utilizar para que las agarre
        val datePicker= DatePickerEdit{ day, month, year-> onDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager,"datePicker")
    }

    private fun onDateSelected(day:Int, month:Int, year:Int){
        editDate.setText("$day/$month/$year")

    }

    override fun onClick(v: View?) {
        if(v!!.id== R.id.btn_edit_update){
            showInfo()
        }

    }
    private fun showInfo(){
        if(this.id_username.text.toString() =="" && this.id_password.text.toString()=="" && this.id_confirmPassword.text.toString()==""&&this.editDate.text.toString()=="" ){
            Toast.makeText(this,"Favor de llenar los campos",Toast.LENGTH_LONG).show();
        }else if(this.id_password.text.toString()!=this.id_confirmPassword.text.toString()){
            Toast.makeText(this,"Las contraseñas tienen que ser iguales",Toast.LENGTH_LONG).show();
        }else{

            var Dbusers = dbUsers(this@EditProfileUser)
            var resultado: Boolean =Dbusers.modificarUser(Usuario.getUsername(),this.id_password.text.toString(),this.editDate.text.toString())

            if(resultado){

                Toast.makeText(this,"Ahorita vengo",Toast.LENGTH_LONG).show();
                /*user = Dbusers.verUser(Usuario.getUsername())
                if(user!=null){
                    Usuario.setUsuario(user.USERID, user.USERNAME, user.PASS, user.BIRTHDAY)
                    Toast.makeText(this,"Usuario modificado",Toast.LENGTH_LONG).show();
                    val intent=Intent(this,DrawerActivity::class.java)
                    startActivity(intent)
                }*/


                //val intent=Intent(this,DrawerActivity::class.java)
                //intent.putExtra("user",this.input_username.text.toString())

            }
            else{
                Toast.makeText(this,"Error al modificar el usuario",Toast.LENGTH_LONG).show();

            }

        }
    }
}


