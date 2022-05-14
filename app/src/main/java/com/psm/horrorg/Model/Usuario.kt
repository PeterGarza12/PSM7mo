package com.psm.horrorg.Model

object Usuario {
    private var id:Int=0
    private var username:String = ""
    private var password:String = ""
    private var dateBirth:String = ""

    fun setUsuario(id: Int, username: String, password: String, dateBirth: String){
        this.id = id;
        this.username = username;
        this.password = password;
        this.dateBirth = dateBirth;
    }

    fun getId(): Int{
        return this.id
    }

    fun getUsername(): String{
        return this.username
    }

    fun getPassword(): String{
        return this.password
    }

    fun getDateBirth(): String{
        return this.dateBirth
    }
}