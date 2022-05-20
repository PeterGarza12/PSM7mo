package com.psm.horrorg.Model

import android.graphics.Bitmap

object chapter {
    private var id:Int=0
    private var strTitle:String = ""
    private var strBody:String = ""
    private var imgArray: Bitmap? = null
    private var bookId:Int=0

    fun setCapitulo(id: Int, bookId: Int, strTitle: String, strBody: String, imgArray: Bitmap?){
        this.id = id;
        this.bookId = bookId;
        this.strTitle = strTitle;
        this.strBody = strBody;
        this.imgArray = imgArray;
    }

    fun getId(): Int{
        return this.id

    }
    fun getTitle(): String{
        return this.strTitle
    }
    fun getBody(): String{
        return this.strBody
    }
    fun getBookId(): Int{
        return this.bookId

    }
    fun getimgArray(): Bitmap?{
        return this.imgArray
    }
}