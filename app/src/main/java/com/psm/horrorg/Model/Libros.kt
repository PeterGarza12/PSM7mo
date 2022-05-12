package com.psm.horrorg.Model

import com.psm.horrorg.Data.Genre

class Libros  (
    var strTitle:String? = null,
    var strDescription:String? =  null,
    var intIdImage:Int =  0,
    var genre: Genre? = null,
    var imgArray:ByteArray? =  null
        ){
}