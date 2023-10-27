package com.example.jumppark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vouchers")
data class Voucher(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val model: String?,
    val plate: String?,
    val color: String?,
    val parked: Boolean?,
    val predictedValue: Double?,
    val predictedMin: Int?,
    val surplusValue: Double?,
    val paidValue: Double?,
    val paid: Boolean?,
    val paymentMethodId: Long?,
    val paymentMethodName: String?,
    val finished: Boolean?,
    val entryDate: String?,
    val exitDate: String?,
    val paymentDate: String?,
    val establishmentId: Long?
)
