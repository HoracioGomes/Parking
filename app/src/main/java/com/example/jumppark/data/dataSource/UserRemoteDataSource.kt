package com.example.jumppark.data.dataSource

import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.model.responses.logout.LogoutResponse
import retrofit2.Response
import retrofit2.http.Path

interface UserRemoteDataSource {
    suspend fun getLogin(user: UserLogin): Response<LoginResponse>
    suspend fun getLogout(
        userId: String,
        establishmentId: String,
        sessionId: String
    ): Response<LogoutResponse>
}