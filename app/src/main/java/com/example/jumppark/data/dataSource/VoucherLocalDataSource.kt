package com.example.jumppark.data.dataSource

import com.example.jumppark.data.model.Voucher
import kotlinx.coroutines.flow.Flow


interface VoucherLocalDataSource {
    suspend fun saveVoucher(voucher: Voucher)

    fun getSavedVouchers(): Flow<List<Voucher>>

    fun getParkedVouchers(): Flow<List<Voucher>>
}