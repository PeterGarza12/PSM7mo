package com.psm.horrorg.Model

import android.graphics.Bitmap

object Categorias {
    private var strname:String = ""

    fun setCategoria(strname: String){

        this.strname = strname
    }

    fun getName(): String{
        return this.strname
    }
}