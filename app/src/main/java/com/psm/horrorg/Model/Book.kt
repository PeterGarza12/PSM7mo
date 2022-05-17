package com.psm.horrorg.Model

import android.graphics.Bitmap

data class Book(var bookId : Int? = null,
                var userId : Int? = null,
                var title:String? = null,
                var description:String? =  null,
                var image:Bitmap? =  null,
                var genreId: Int? = null){}
