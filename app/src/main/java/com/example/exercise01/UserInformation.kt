package com.example.exercise01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.exercise01.apis.ApiClient
import com.example.exercise01.databinding.ActivityUserInformationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserInformation : AppCompatActivity() {
    private lateinit var binding: ActivityUserInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInformationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        val message = intent.getStringArrayExtra(EXTRA_MESSAGE)
        val userId: String = intent.getStringExtra(userId)!!
        executeCall(userId)

//        binding.tViewUsername.apply { text = message!![0].toString() }
//        binding.tViewGender.apply { text = message!![1].toString() }
//        binding.tViewDocID.apply { text = message!![2].toString() }
//        binding.tViewDOB.apply { text = message!![3].toString() }

        binding.btnSubmit.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
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
                    binding.tViewDOB.apply { text = content.DOB }
                } else {
                    Toast.makeText(this@UserInformation, "Error Occurred: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@UserInformation, "Error Occurred: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}