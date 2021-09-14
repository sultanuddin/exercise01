package com.example.exercise01

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.exercise01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN

        val textField = binding.autoCompleteGender
        val genders = listOf("Male", "Female")
        val adapter = ArrayAdapter(this, R.layout.activity_main, genders)
        (textField.setAdapter(adapter))
    }
}