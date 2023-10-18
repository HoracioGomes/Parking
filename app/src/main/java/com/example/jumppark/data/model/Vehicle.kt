package com.example.jumppark.data.model

import java.time.LocalDateTime

data class Vehicle(
    val model: String,
    val establishmentId: String,
    val parked: Boolean,
    val entryDate: LocalDateTime,
    val exitDate: LocalDateTime,
)
