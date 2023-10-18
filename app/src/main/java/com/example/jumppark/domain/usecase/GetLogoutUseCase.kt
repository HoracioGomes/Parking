package com.example.jumppark.domain.usecase

import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.responses.logout.LogoutResponse
import com.example.jumppark.domain.repository.UserRepository

class GetLogoutUseCase(private val repository: UserRepository) {
    suspend fun execute(userId: String, establishmentId: String, sessionId: String): Resource<LogoutResponse> {
        return repository.getLogout(userId = userId, establishmentId = establishmentId, sessionId = sessionId)
    }
}