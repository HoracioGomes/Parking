package com.example.jumppark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vouchers")
data class Voucher(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    var model: String?,
    var plate: String?,
    var color: String?,
    var parked: Boolean?,
    var predictedValue: Double = 0.0,
    var predictedMin: Int?,
    var paidValue: Double = 0.0,
    var paid: Boolean?,
    var paymentMethodId: Long?,
    var paymentMethodName: String?,
    var finished: Boolean?,
    var entryDate: String?,
    var exitDate: String?,
    var paymentDate: String?,
    var establishmentId: Long?
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
