package com.example.schedule.api

import com.example.schedule.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("User")
    fun getUsers(): Call<List<User>>
}
