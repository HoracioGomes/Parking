package com.example.jumppark.data.repository

import com.example.jumppark.data.dataSource.UserRemoteDataSource
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.dataUtils.responseToResource
import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.model.responses.logout.LogoutResponse
import com.example.jumppark.domain.repository.UserRepository

class UserRepositoryImpl(
    private val dataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getLogin(user: UserLogin): Resource<LoginResponse> {
        val response = dataSource.getLogin(user)
        return responseToResource(response)
    }

    override suspend fun getLogout(
        userId: String,
        establishmentId: String,
        sessionId: String
    ): Resource<LogoutResponse> {
        val response = dataSource.getLogout(
            userId = userId,
            establishmentId = establishmentId,
            sessionId = sessionId
        )
        return responseToResource(response)
    }
}