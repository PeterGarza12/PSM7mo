package com.psm.horrorg.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.psm.horrorg.EditProfileUser
import com.psm.horrorg.Model.User
import com.psm.horrorg.Model.Usuario
import com.psm.horrorg.R

class ProfileUser : Fragment(R.layout.profile_user) {

    private  lateinit var user: User
    private lateinit var ivPic: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.profile_user, container, false)




        val txtUsername=view.findViewById<TextView>(R.id.txt_profile_username)
        val txtPassword = view.findViewById<TextView>(R.id.txtPassword)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)
        val txtName=view.findViewById<TextView>(R.id.txtName)
        val txtEmail = view.findViewById<TextView>(R.id.txtEmail)
        ivPic = view.findViewById(R.id.iv_profile_pic)



        txtUsername.text =Usuario.getUsername()
        txtPassword.text=Usuario.getPassword()
        txtDate.text=Usuario.getDateBirth()
        txtName.text = Usuario.getName()
        txtEmail.text = Usuario.getEmail()
        ivPic.setImageBitmap(Usuario.getImage())



        val editProfile = view.findViewById<Button>(R.id.btn_editProfile)

        editProfile.setOnClickListener{

            val intent = Intent(context, EditProfileUser::class.java)
            startActivity(intent)

        }


        return view
    }
}