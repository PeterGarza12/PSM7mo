package com.psm.horrorg.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.psm.horrorg.EditProfileUser
import com.psm.horrorg.R

class ProfileUser : Fragment(R.layout.profile_user) {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        val view: View = inflater.inflate(R.layout.profile_user, container, false)

        val editProfile = view.findViewById<Button>(R.id.btn_editProfile)

        editProfile.setOnClickListener{

            val intent = Intent(context, EditProfileUser::class.java)
            startActivity(intent)

        }


        return view
    }
}