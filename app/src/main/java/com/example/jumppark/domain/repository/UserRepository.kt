package com.example.jumppark.domain.repository

import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.util.Resource

interface UserRepository {
    suspend fun getLogin(email: String, password: String): Resource<LoginResponse>
    suspend fun getLogout()
}