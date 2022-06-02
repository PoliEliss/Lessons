package com.rorono.lessons

import android.graphics.Bitmap

interface ImageCallback {

    fun success ( bitmap:Bitmap)

    fun failed()


}
