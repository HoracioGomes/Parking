package com.example.jumppark.domain.repository

import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.data.model.responses.logout.LogoutResponse

interface UserRepository {
    suspend fun getLogin(user: UserLogin): Resource<LoginResponse>
    suspend fun getLogout(userId: String, establishmentId: String, sessionId: String): Resource<LogoutResponse>
}