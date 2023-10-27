package com.example.jumppark.data.model.responses.establishment

data class ItemPrice(
    val establishmentId: Int,
    val itemId: Int,
    val period: Int,
    val price: String,
    val since: Int,
    val typePrice: String
)