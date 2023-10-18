package com.example.jumppark.data.dataSourceImpl

import com.example.jumppark.data.api.UserAPIService
import com.example.jumppark.data.dataSource.UserRemoteDataSource
import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.model.responses.logout.LogoutResponse
import retrofit2.Response

class UserRemoteDataSourceImpl(
    private val service: UserAPIService
) : UserRemoteDataSource {
    override suspend fun getLogin(user: UserLogin): Response<LoginResponse> {
        return service.getLogin(user)
    }

    override suspend fun getLogout(userId: String, establishmentId: String, sessionId: String):
            Response<LogoutResponse> {
        return service.getLogout(
            userId = userId,
            establishmentId = establishmentId,
            sessionId = sessionId
        )
    }
}