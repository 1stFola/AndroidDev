package com.example.journal.data.remote

import com.example.journal.data.model.LoginRequest
import com.example.journal.data.model.LoginResponse
import com.example.journal.data.model.RegistrationRequest
import com.example.journal.data.model.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GossipCentralAPI {

    @POST("auth/user")
    suspend fun register(@Body request : RegistrationRequest) : RegistrationResponse

    @POST("auth/authenticate")
    suspend fun login(@Body request : LoginRequest) : LoginResponse

}