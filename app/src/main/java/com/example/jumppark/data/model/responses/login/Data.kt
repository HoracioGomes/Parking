package com.example.jumppark.data.model.responses.login

data class Data(
    val cashReserve: String,
    val cashier: Any,
    val establishments: List<Establishment>,
    val session: Session,
    val user: User
)