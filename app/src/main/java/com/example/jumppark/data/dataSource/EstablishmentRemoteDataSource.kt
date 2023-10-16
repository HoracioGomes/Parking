package com.example.jumppark.data.dataSource

import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import retrofit2.Response

interface EstablishmentRemoteDataSource {
    suspend fun getEstablishmentInformations(): Response<EstablishmentResponse>
}