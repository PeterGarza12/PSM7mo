package com.psm.horrorg.Model

import android.graphics.Bitmap
import com.psm.horrorg.Data.Genre

class Libros  (
    var strTitle:String? = null,
    var strDescription:String? =  null,
    var intIdImage:Int =  0,
    var genre: Genre? = null,
    var imgArray:Bitmap? =  null
        ){
}