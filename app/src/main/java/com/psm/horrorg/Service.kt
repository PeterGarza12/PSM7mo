package com.psm.horrorg

import com.psm.horrorg.Model.DataBook
import com.psm.horrorg.Model.DataChapter
import com.psm.horrorg.Model.User2
import retrofit2.Call
import retrofit2.http.*

interface Service {
    //Servicios para consumir el Libro
    @GET("Book/Books")
    fun getBooks(): Call<List<DataBook>>

    @Headers("Content-Type: application/json")
    @POST("Book/Save")
    fun saveBooks(@Body bookData: DataBook): Call<Int>

    @GET("User/Users/{EMAIL}")
    fun getUser(@Path("EMAIL") EMAIL: String): Call<List<User2>>

    @Headers("Content-Type: application/json")
    @POST("User/Save")
    fun saveUser(@Body userData: User2): Call<Int>

    @Headers("Content-Type: application/json")
    @POST("Chapter/Save")
    fun saveChapters(@Body chapterData: DataChapter): Call<Int>

}