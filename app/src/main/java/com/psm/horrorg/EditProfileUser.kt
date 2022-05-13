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
import androidx.appcompat.app.AppCompatActivity
import com.psm.horrorg.Db.dbUsers
import com.psm.horrorg.Model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_Login
import kotlinx.android.synthetic.main.activity_register.*

class EditProfileUser: AppCompatActivity() {
    var user = User()
    var sUser:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_user_edit)

        val extras = intent.extras
        if (extras !=null){
            val strUser = extras?.getString("user")?:"sin dato"
            Log.e("Dato", strUser)
            sUser=strUser.toString()
        }

        val txtUsername=findViewById<TextView>(R.id.id_username)
        val txtPassword = findViewById<EditText>(R.id.id_password)
        val txtConfirmPassword = findViewById<EditText>(R.id.id_confirmPassword)

        val btnCancelar = findViewById<Button>(R.id.btn_edit_cancel)
        val btnUpdate = findViewById<Button>(R.id.btn_edit_update)

        val DbUsers= dbUsers(this)
        user=DbUsers.verUser("Peter")
        if(user!=null){
            txtUsername.text = user!!.username.toString()
            txtPassword.setText(user!!.password.toString())
            txtConfirmPassword.setText(user!!.password.toString())
        }

        btnCancelar.setOnClickListener {
            Toast.makeText(this, "Se ha cancelado", Toast.LENGTH_SHORT).show()
        }

        btnUpdate.setOnClickListener {
            Toast.makeText(this, "Se ha actualizado", Toast.LENGTH_SHORT).show()
        }
    }
}


