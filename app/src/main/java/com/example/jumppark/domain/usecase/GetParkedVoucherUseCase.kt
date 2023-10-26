package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.Voucher
import com.example.jumppark.domain.repository.VoucherRepository
import kotlinx.coroutines.flow.Flow

class GetParkedVoucherUseCase(val repository: VoucherRepository) {
    fun execute(): Flow<List<Voucher>> {
            return repository.getSavedVoucher()
    }
}