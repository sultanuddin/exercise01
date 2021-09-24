package com.example.exercise01.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username") val username: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("documentId") val DocID: String,
    @SerializedName("dateOfBirth") val DOB: String
)
