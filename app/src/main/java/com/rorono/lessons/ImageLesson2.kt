package com.rorono.lessons

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.net.URL
import java.util.*
import com.squareup.picasso.Callback as Callback1

class ImageLesson2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_lesson2)

        val image = findViewById<ImageView>(R.id.iconImageView)


      /*   Picasso.get().load(URL).centerCrop()
            .resize(720, 1280)
            .placeholder(android.R.drawable.ic_media_pause)
            .error(android.R.drawable.ic_dialog_alert)
            .into(image)*/

        Glide.with(this).load(URL).into(image)


        //image.setImageResource(R.drawable.ic_launcher_background)

        /* val netImage = NetImage(URL,object :ImageCallback{
             override fun success(bitmap: Bitmap)=runOnUiThread() {
                image.setImageBitmap(bitmap)
             }

             override fun failed()=runOnUiThread() {
              Snackbar.make(image,"failed",Snackbar.LENGTH_SHORT).show()
             }

         })
         netImage.start()*/
    }

    companion object {
        const val URL = "https://www.imgonline.com.ua/examples/bee-on-daisy.jpg"
    }
}






