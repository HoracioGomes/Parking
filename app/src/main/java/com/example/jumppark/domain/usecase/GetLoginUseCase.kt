package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.util.Resource
import com.example.jumppark.domain.repository.UserRepository

class GetLoginUseCase(private val repository: UserRepository) {
    suspend fun execute(emai: String, password: String): Resource<LoginResponse> {
        return repository.getLogin(email = emai, password = password)
    }
}