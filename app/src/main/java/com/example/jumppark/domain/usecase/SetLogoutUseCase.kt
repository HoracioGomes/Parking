package com.example.jumppark.domain.usecase

import com.example.jumppark.domain.repository.UserRepository

class SetLogoutUseCase(private val repository: UserRepository) {
    suspend fun getLogout() = repository.getLogout()
}