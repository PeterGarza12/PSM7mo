package com.psm.horrorg.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.psm.horrorg.Db.dbUsers
import com.psm.horrorg.EditProfileUser
import com.psm.horrorg.Model.User
import com.psm.horrorg.Model.Usuario
import com.psm.horrorg.R

class ProfileUser : Fragment(R.layout.profile_user) {
    var user = User()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.profile_user, container, false)
        val appContext = requireContext().applicationContext
        val DbUsers= dbUsers(appContext)


        val txtUsername=view.findViewById<TextView>(R.id.txtUsername)

        val txtPassword = view.findViewById<TextView>(R.id.txtPassword)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)




            txtUsername.text =Usuario.getUsername()
            txtPassword.text=Usuario.getPassword()
            txtDate.text=Usuario.getDateBirth()



        val editProfile = view.findViewById<Button>(R.id.btn_editProfile)

        editProfile.setOnClickListener{

            val intent = Intent(context, EditProfileUser::class.java)
            startActivity(intent)

        }


        return view
    }
}