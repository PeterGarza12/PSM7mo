package com.psm.horrorg.Model

import android.graphics.Bitmap

object Usuario {
    private var USERID  :Int    =0
    private var USERNAME:String = ""
    private var PASS    :String = ""
    private var BIRTHDAY:String = ""
    private var IMAGE   :Bitmap? = null
    private var NAME    :String = ""
    private var EMAIL   :String = ""

    fun setUsuarioId(id: Int){
        this.USERID     = id;
    }

    fun setUsuario(id: Int, username: String, password: String, dateBirth: String, image: Bitmap, name: String, email: String){
        this.USERID     = id;
        this.USERNAME   = username;
        this.PASS       = password;
        this.BIRTHDAY   = dateBirth;
        this.IMAGE      = image;
        this.NAME       = name;
        this.EMAIL      = email;
    }

    fun getId(): Int{
        return this.USERID
    }

    fun getUsername(): String{
        return this.USERNAME
    }

    fun getPassword(): String{
        return this.PASS
    }

    fun getDateBirth(): String{
        return this.BIRTHDAY
    }

    fun getImage(): Bitmap?{
        return this.IMAGE
    }

    fun getName(): String{
        return this.NAME
    }

    fun getEmail(): String{
        return this.EMAIL
    }
}