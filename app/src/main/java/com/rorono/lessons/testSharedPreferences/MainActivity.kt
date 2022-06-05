package com.rorono.lessons.testSharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rorono.lessons.R
import com.rorono.lessons.databinding.ActivityMain2Binding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var testText:String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSend.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        binding.textResult.text = binding.editText.text.toString()
        testText = binding.textResult.text as String
        Log.d("TEST","${testText}")
        val sharedPreferences = getSharedPreferences("text", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("lol",testText)
        }.apply()
    }
    private fun loadData(){
        val sharedPreferences = getSharedPreferences("text", Context.MODE_PRIVATE)
        val savedString:String? = sharedPreferences.getString("lol",null)
        binding.textResult.text = savedString
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}