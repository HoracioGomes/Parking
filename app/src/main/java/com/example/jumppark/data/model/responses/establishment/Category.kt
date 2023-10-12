package com.example.jumppark.data.model.responses.establishment

data class Category(
    val categoryId: Int,
    val categoryName: String,
    val categorySourceId: Int,
    val costCenterId: Int,
    val transactionTypeId: Int
)