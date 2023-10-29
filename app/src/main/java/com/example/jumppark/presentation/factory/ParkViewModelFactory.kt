package com.example.jumppark.presentation.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jumppark.domain.usecase.GetSavedVoucherUseCase
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.domain.usecase.LaunchEntryUseCase
import com.example.jumppark.presentation.viewmodel.ParkViewModel

class ParkViewModelFactory(
    private val app: Application,
    private val getEstablishmentUseCase: GetStablishmentInformationUseCase,
    private val launchEntryUseCase: LaunchEntryUseCase,
    private val getSavedVoucherUseCase: GetSavedVoucherUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ParkViewModel(
            app,
            getEstablishmentUseCase,
            launchEntryUseCase,
            getSavedVoucherUseCase
        ) as T
    }
}