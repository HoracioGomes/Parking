package com.example.jumppark.data.model.establishment

data class Price(
    val covenant: Int,
    val establishmentId: Int,
    val invisible: Int,
    val items: List<Item>,
    val major: Int,
    val maximumPeriod: Int,
    val maximumValue: String,
    val tolerance: Int,
    val typePrice: String
)