package com.example.jumppark.data.repository

import com.example.jumppark.data.dataSource.VoucherLocalDataSource
import com.example.jumppark.data.model.Voucher
import com.example.jumppark.domain.repository.VoucherRepository
import kotlinx.coroutines.flow.Flow

class VoucherRepositoryImpl(private val dataSource: VoucherLocalDataSource) : VoucherRepository {
    override suspend fun saveVoucher(voucher: Voucher) {
        dataSource.saveVoucher(voucher)
    }

    override fun getSavedVoucher(): Flow<List<Voucher>> {
        TODO("Not yet implemented")
    }

    override fun searchVoucher(query: String): Flow<List<Voucher>> {
        TODO("Not yet implemented")
    }
}