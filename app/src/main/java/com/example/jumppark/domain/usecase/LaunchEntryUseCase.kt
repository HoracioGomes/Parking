package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.Voucher
import com.example.jumppark.domain.repository.VoucherRepository

class LaunchEntryUseCase(private val repository: VoucherRepository) {
    suspend fun execute(voucher: Voucher) = repository.saveVoucher(voucher)
}