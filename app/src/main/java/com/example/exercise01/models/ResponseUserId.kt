package com.example.exercise01.models

import com.google.gson.annotations.SerializedName

data class ResponseUserId(
    @SerializedName("name") val UserId: String
)