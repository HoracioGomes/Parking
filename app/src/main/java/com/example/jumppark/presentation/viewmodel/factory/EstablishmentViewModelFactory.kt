package com.example.jumppark.presentation.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.presentation.viewmodel.EstablishmentViewModel

class EstablishmentViewModelFactory(
    private val app: Application,
    private val establishmentUseCase: GetStablishmentInformationUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EstablishmentViewModel(
            app,
            establishmentUseCase
        ) as T
    }
}