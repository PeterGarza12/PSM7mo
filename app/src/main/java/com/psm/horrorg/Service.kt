package com.psm.horrorg

import com.psm.horrorg.Model.User2
import retrofit2.Call
import retrofit2.http.*

interface Service {
    //Servicios para consumir el Album
    /*@GET("Album/Albums")
    fun getAlbums(): Call<List<Album>>*/

    @GET("User/Users/{EMAIL}")
    fun getUser(@Path("EMAIL") EMAIL: String): Call<List<User2>>

    @Headers("Content-Type: application/json")
    @POST("App/User.php/SaveUser")
    fun saveUser(@Body userData: User2): Call<Int>

}