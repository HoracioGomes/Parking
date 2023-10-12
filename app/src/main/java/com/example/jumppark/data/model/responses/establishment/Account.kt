package com.example.jumppark.data.model.responses.establishment

data class Account(
    val accountId: Int,
    val accountName: String,
    val accountTypeId: Int,
    val bankAccount: String,
    val bankAgency: String,
    val bankId: Any,
    val bankName: String
)