package com.psm.horrorg.Model

import android.graphics.Bitmap

data class User2(var userId:Int? =  null,
                 var username:String? = null,
                 var pass:String? =  null,
                 var birthday:String? =  null,
                 var image: Bitmap? = null){}
