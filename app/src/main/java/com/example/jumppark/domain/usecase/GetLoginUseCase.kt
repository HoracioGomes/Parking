package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.domain.repository.UserRepository

class GetLoginUseCase(private val repository: UserRepository) {
    suspend fun execute(user: UserLogin): Resource<LoginResponse> {
        return repository.getLogin(user)
    }
}