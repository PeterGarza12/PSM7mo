package com.psm.horrorg.Model

class User {
    var id:Int=0
    var username:String?=null
    var password:String?=null
    var dateBirth:String?=null
    var lastname:String?=null
    var email:String?=null

    constructor(){}

    constructor(id:Int,username:String,password:String,dateBirth:String){
        this.id=id
        this.username=username
        this.password=password
        this.dateBirth=dateBirth
    }

    override fun toString(): String {
        return super.toString()
    }
    fun isNull():Boolean{
        return !(username.equals("")&&password.equals("")&&dateBirth.equals(""))
    }
}