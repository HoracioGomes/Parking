package com.example.jumppark.data.dataSourceImpl

import com.example.jumppark.data.dataSource.VoucherLocalDataSource
import com.example.jumppark.data.db.VoucherDAO
import com.example.jumppark.data.model.Voucher
import kotlinx.coroutines.flow.Flow

class VoucherLocalDataSourceImpl(
    private val voucherDAO: VoucherDAO
) : VoucherLocalDataSource {
    override suspend fun saveVoucher(voucher: Voucher) {
        return voucherDAO.insert(voucher)
    }

    override fun getSavedVouchers(): Flow<List<Voucher>> {
        return voucherDAO.getVouchers()
    }

    override fun getParkedVouchers(): Flow<List<Voucher>> {
        return voucherDAO.getParkedVouchers()
    }


}