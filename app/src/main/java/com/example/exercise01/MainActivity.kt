package com.example.exercise01

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import com.example.exercise01.databinding.ActivityMainBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText

const val EXTRA_MESSAGE = "com.example.exercise01.MESSAGE"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val viewLayout = binding.root
        setContentView(viewLayout)

        val genders = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, R.layout.dropdown_menu_popup_item, genders)
        (binding.autoCompleteGender as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.textInputDOB.setOnClickListener{clickDatePicker()}

        binding.textInputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateUsernameText(s)
            }
        })
        binding.textInputName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validateUsernameText((v as EditText).text)
            }
        }
        binding.autoCompleteGender.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateGenderText(s)
            }
        })
        binding.autoCompleteGender.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validateGenderText((v as EditText).text)
            }
        }
        binding.textInputDocID.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateDocText(s)
            }
        })
        binding.textInputDocID.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validateDocText((v as EditText).text)
            }
        }
        binding.textInputDOB.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateDOBText(s)
            }
        })
        binding.textInputDOB.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validateDOBText((v as EditText).text)
            }
        }

        binding.btnSubmit.setOnClickListener{
            when {
                binding.textInputName.text.toString().isEmpty() -> {
                    binding.textLayoutName.error = "Please enter your Username!"
                }
                binding.autoCompleteGender.text.toString().isEmpty() -> {
                    binding.textLayoutGender.error = "Please enter your Gender!"
                }
                binding.textInputDocID.text.toString().isEmpty() -> {
                    binding.textLayoutDocID.error = "Please enter your Document ID!"
                }
                binding.textInputDOB.text.toString().isEmpty() -> {
                    binding.textLayoutDOB.error = "Please enter your Date of Birth!"
                }
                else -> {
                    val username = binding.textInputName.text.toString()
                    val userGender = binding.autoCompleteGender.text.toString()
                    val docID = binding.textInputDocID.text.toString()
                    val dobUser = binding.textInputDOB.text.toString()
                    val message = arrayOf(username,userGender,docID,dobUser)
                    val intent = Intent(this, UserInformation::class.java)
                        .apply{
                            putExtra(EXTRA_MESSAGE, message)
                        }
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
    private fun validateUsernameText(s: Editable) {
        if (!TextUtils.isEmpty(s)) {
            binding.textLayoutName.error = null
        } else {
            binding.textLayoutName.error = "Please enter your Username!"
        }
    }
    private fun validateGenderText(s: Editable) {
        if (!TextUtils.isEmpty(s)) {
            binding.textLayoutGender.error = null
        } else {
            binding.textLayoutGender.error = "Please enter your Gender!"
        }
    }
    private fun validateDocText(s: Editable) {
        if (!TextUtils.isEmpty(s)) {
            binding.textLayoutDocID.error = null
        } else {
            binding.textLayoutDocID.error = "Please enter your Document ID!"
        }
    }
    private fun validateDOBText(s: Editable) {
        if (!TextUtils.isEmpty(s)) {
            binding.textLayoutDOB.error = null
        } else {
            binding.textLayoutDOB.error = "Please enter your Date of Birth!"
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickDatePicker(){
        val constraintsBuilder = CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build().apply { addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis) } }
        datePicker.show(supportFragmentManager,"DATE_PICKER")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDateSelected(dateTimeStampInMillis: Long){
        val dateTime: LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(
            dateTimeStampInMillis
        ), ZoneId.systemDefault())
        val dateValue = "${dateTime.dayOfMonth} ${dateTime.month} ${dateTime.year}"
        binding.textInputDOB.setText(dateValue)
    }
}