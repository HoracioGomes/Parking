package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.Voucher
import com.example.jumppark.domain.repository.VoucherRepository
import kotlinx.coroutines.flow.Flow

class GetSearchVoucherUseCase(private val repository: VoucherRepository) {
    fun execute(query: String): Flow<List<Voucher>>{
        return repository.searchVoucher(query)
    }
}