package com.example.jumppark.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jumppark.data.model.Voucher
import kotlinx.coroutines.flow.Flow

@Dao
interface VoucherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voucher: Voucher)

    @Query("SELECT * FROM vouchers")
    fun getVouchers():Flow<List<Voucher>>

    @Query("SELECT * FROM vouchers AS v WHERE v.parked == 1")
    fun getParkedVouchers():Flow<List<Voucher>>
}