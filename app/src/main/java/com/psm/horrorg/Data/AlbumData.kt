package com.psm.horrorg.Data

//Nos permite manejar los generos
class Genre(var intId:Int, var strTitle:String){

    override fun toString(): String {
        return this.strTitle
    }
}
//Las variables se declaran como opcionales.
//Esto permite crear un objecto de album por defecto vacio
//que sea usa cuando vamos agregar un nuevo album
class Album(var strTitle:String? = null,
            var strDescription:String? =  null,
            var intIdImage:Int? =  null,
            var genre: Genre? = null,
            var imgArray:ByteArray? =  null){}