package com.example.exercise01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exercise01.databinding.ActivityUserInformationBinding

class UserInformation : AppCompatActivity() {
    private lateinit var binding: ActivityUserInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInformationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val message = intent.getStringArrayExtra(EXTRA_MESSAGE)

        binding.tViewUsername.apply { text = message!![0].toString() }
        binding.tViewGender.apply { text = message!![1].toString() }
        binding.tViewDocID.apply { text = message!![2].toString() }
        binding.tViewDOB.apply { text = message!![3].toString() }

        binding.btnSubmit.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}