package com.example.exercise01.apis

import com.example.exercise01.models.ResponseUserId
import com.example.exercise01.models.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("mobileid/users/{userid}.json")
    suspend fun getUserById(@Path("userid") userid : String): Response<User>

    @POST("mobileid/users.json")
    suspend fun postUser(@Body user: User): Response<ResponseUserId>

}
