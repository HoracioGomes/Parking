package com.example.jumppark.data.api

import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.model.responses.logout.LogoutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPIService {
    @POST("user/login")
    suspend fun getLogin(@Body user: UserLogin): Response<LoginResponse>

    @POST("{userId}/establishment/{establishmentId}/session/close/{sessionId}")
    suspend fun getLogout(
        @Path("userId") userId: String,
        @Path("establishmentId") establishmentId: String,
        @Path("sessionId") sessionId: String
    ): Response<LogoutResponse>
}