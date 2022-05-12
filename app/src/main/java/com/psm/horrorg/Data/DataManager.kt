package com.psm.horrorg.Data

import android.content.Context
import com.psm.horrorg.R


object DataManager {
    val genres = ArrayList<Genre>()
    val albums = ArrayList<Album>()
    var content: Context? = null

    init {
        this.initializeGenres()
        this.initializeAlbums()
    }

    private fun initializeGenres(){
        var genre =  Genre(1,"Mas Populares")
        genres.add(genre)

        genre = Genre(2,"Cuentos Cortos")
        genres.add(genre)

        genre = Genre(3,"Suspenso")
        genres.add(genre)

        genre = Genre(4,"Criminologia")
        genres.add(genre)

        genre = Genre(5,"Horror")
        genres.add(genre)

    }

    private fun initializeAlbums(){
        var album =  Album()
        album.strTitle =  "Please Please Me"
        album.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        album.intIdImage = R.drawable.portada6
        album.genre =  genres[1]

        albums.add(album)

        album =  Album()
        album.strTitle =  "With The Beatles"
        album.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles02,content!!)
        album.intIdImage = R.drawable.portada3
        album.genre =  genres[1]

        albums.add(album)

        album =  Album()
        album.strTitle =  "A Hard Day Night"
        album.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles03,content!!)
        album.intIdImage = R.drawable.portada7
        album.genre =  genres[1]

        albums.add(album)

        album =  Album()
        album.strTitle =  "Beatles For Sale"
        album.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles04,content!!)
        album.intIdImage = R.drawable.portada4
        album.genre =  genres[1]

        albums.add(album)

        album =  Album()
        album.strTitle =  "Help"
        album.strDescription = LOREMIPSUM
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles05,content!!)
        album.intIdImage = R.drawable.portada5
        album.genre =  genres[1]

        albums.add(album)

    }


}