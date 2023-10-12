package com.example.jumppark.data.model.responses.login

data class User(
    val accessToken: String,
    val appRestrictions: Any,
    val document: String,
    val email: String,
    val establishments: List<Int>,
    val lastEstablishment: Any,
    val mainUser: Int,
    val name: String,
    val phone: String,
    val profileId: Int,
    val rpsCount: Int,
    val serviceOrderCount: Int,
    val status: Int,
    val userCode: String,
    val userId: Int,
    val userRegistration: Any,
    val userRestrictions: Any,
    val uuid: String
)