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
    val predictedValue: Double = 0.0,
    val predictedMin: Int?,
    val surplusValue: Double = 0.0,
    val paidValue: Double = 0.0,
    val paid: Boolean?,
    val paymentMethodId: Long?,
    val paymentMethodName: String?,
    val finished: Boolean?,
    val entryDate: String?,
    val exitDate: String?,
    val paymentDate: String?,
    val establishmentId: Long?
) : java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Voucher

        if (plate != other.plate) return false

        return true
    }

    override fun hashCode(): Int {
        return plate?.hashCode() ?: 0
    }
}
