package com.psm.horrorg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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

        }else{
            val intent=Intent(this,HomeActivity::class.java)
            intent.putExtra("user",this.input_username.text.toString())
            startActivity(intent)
        }
    }


}