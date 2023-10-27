package com.example.jumppark.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jumppark.data.model.Voucher

@Database(
    entities = [Voucher::class],
    version = 3,
    exportSchema = false
)
abstract class ParkDatabase : RoomDatabase() {
    abstract fun getVoucherDao(): VoucherDAO
}