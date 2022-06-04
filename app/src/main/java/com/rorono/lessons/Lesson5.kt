package com.rorono.lessons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class Lesson5 : AppCompatActivity() {
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson5)
        viewModel = (application as MyApplication).viewModel

        val textView = findViewById<TextView>(R.id.view)
        val observable = TextObservable()
        observable.observe(object :TextCallback{
            override fun updateText(str: String) =runOnUiThread{
                Log.d("TEST","MainActivity updateTExt")
                textView.text = str
            }
        })
       viewModel.init(observable)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeCounting()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseCounting()
    }
    override fun onDestroy() {
       viewModel.clear()
        super.onDestroy()
    }
}