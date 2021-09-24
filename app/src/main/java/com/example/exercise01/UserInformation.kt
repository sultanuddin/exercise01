package com.example.exercise01

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.exercise01.apis.ApiClient
import com.example.exercise01.databinding.ActivityUserInformationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserInformation : AppCompatActivity() {
    private lateinit var binding: ActivityUserInformationBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInformationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val userId: String = intent.getStringExtra("userId")!!
        executeCall(userId)

        binding.btnSubmit.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun executeCall(userId: String) {
        val mainActivityJob = Job()
        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.getUserById(userId)
                if (response.isSuccessful && response.body()!=null) {
                    val content = response.body()!!
                    // TODO: assign userId
                    binding.tViewUsername.apply { text = content.username }
                    binding.tViewGender.apply { text = content.gender }
                    binding.tViewDocID.apply { text = content.DocID }
                    val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
                    val dateTime = LocalDate.parse(content.DOB, formatter)
                    val dateOfBirth = "${dateTime.dayOfMonth} ${dateTime.month} ${dateTime.year}"
                    binding.tViewDOB.apply { text = dateOfBirth }
                } else {
                    Toast.makeText(this@UserInformation, "Error Occurred: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@UserInformation, "Error Occurred: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}