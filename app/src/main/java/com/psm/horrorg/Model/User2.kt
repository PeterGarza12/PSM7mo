package com.psm.horrorg.Model

import android.graphics.Bitmap

data class User2(var USERID     :Int?    =  null,
                 var USERNAME   :String? =  null,
                 var PASS       :String? =  null,
                 var BIRTHDAY   :String? =  null,
                 var IMAGE      :ByteArray?=null,
                 var NAME       :String? =  null,
                 var EMAIL      :String? =  null
                ){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User2

        if (IMAGE != null) {
            if (other.IMAGE == null) return false
            if (!IMAGE.contentEquals(other.IMAGE)) return false
        } else if (other.IMAGE != null) return false

        return true
    }

    override fun hashCode(): Int {
        return IMAGE?.contentHashCode() ?: 0
    }
}
