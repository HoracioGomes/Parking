package com.example.jumppark.data.dataSource

import com.example.jumppark.data.model.Voucher

interface VoucherLocalDataSource {
    suspend fun saveVoucher(voucher: Voucher)
}