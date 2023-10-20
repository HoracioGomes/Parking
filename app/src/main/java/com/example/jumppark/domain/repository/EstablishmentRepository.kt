package com.example.jumppark.domain.repository

import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.dataUtils.Resource

interface EstablishmentRepository {
    suspend fun getEstablishmentInformations(establishmentId: String, userId: String): Resource<EstablishmentResponse>
}