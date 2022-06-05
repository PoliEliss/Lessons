package com.rorono.lessons.preferencesdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModelProvider
import com.rorono.lessons.databinding.ActivityMain3Binding
import com.rorono.lessons.preferencesdatastore.viewModel.MainViewModel
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var _binding: ActivityMain3Binding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.readFromDataStore.observe(this) { key ->
            binding.tvText.text = key
        }

        binding.sendButton.setOnClickListener {
            val text = binding.editText.text.toString()
            viewModel.saveToDataStore(text)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}