package com.example.jumppark.data.model.responses.login

data class Session(
    val active: Int,
    val created_at: String,
    val endDateTime: String,
    val establishmentId: Int,
    val sessionId: Int,
    val startDateTime: String,
    val status: Int,
    val updated_at: String,
    val userId: Int,
    val versionName: String
)