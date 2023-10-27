package com.example.jumppark.data.dataSourceImpl

import com.example.jumppark.data.dataSource.VoucherLocalDataSource
import com.example.jumppark.data.db.VoucherDAO
import com.example.jumppark.data.model.Voucher

class VoucherLocalDataSourceImpl(
    private val voucherDAO: VoucherDAO
) : VoucherLocalDataSource {
    override suspend fun saveVoucher(voucher: Voucher) {
        return voucherDAO.insert(voucher)
    }
}