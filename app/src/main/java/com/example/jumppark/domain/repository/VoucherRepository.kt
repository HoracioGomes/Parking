package com.example.jumppark.domain.repository

import com.example.jumppark.data.model.Voucher
import kotlinx.coroutines.flow.Flow


interface VoucherRepository {
    suspend fun saveVoucher(voucher: Voucher)
    fun getSavedVoucher(): Flow<List<Voucher>>

    fun getParkedVoucher(): Flow<List<Voucher>>
    fun searchVoucher(query: String): Flow<List<Voucher>>
}