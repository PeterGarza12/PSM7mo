package com.psm.horrorg.Model

import android.graphics.Bitmap

object Libro {
    private var id:Int=0
    private var userId:Int=0
    private var strTitle:String = ""
    private var strDescription:String = ""
    private var intIdImage:Int = 0
    private var genre:String = ""
    private var imgArray:Bitmap? = null

    fun setLibro(id: Int, userId: Int, strTitle: String, strDescription: String, intIdImage: Int, genre: String, imgArray: Bitmap?){
        this.id = id;
        this.userId = userId;
        this.strTitle = strTitle;
        this.strDescription = strDescription;
        this.intIdImage = intIdImage;
        this.genre = genre;
        this.imgArray = imgArray;
    }

    fun getId(): Int{
        return this.id
    }

    fun getUserId(): Int{
        return this.userId
    }

    fun getTitle(): String{
        return this.strTitle
    }

    fun getDescription(): String{
        return this.strDescription
    }

    fun getIntImage(): Int{
        return this.intIdImage
    }

    fun getGenre(): String{
        return this.genre
    }

    fun getimgArray(): Bitmap?{
        return this.imgArray
    }
}