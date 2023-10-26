package com.example.jumppark.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.jumppark.data.model.Voucher

@Dao
interface VoucherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voucher: Voucher)
}