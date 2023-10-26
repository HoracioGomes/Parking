package com.example.jumppark.presentation.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jumppark.domain.usecase.GetLoginUseCase
import com.example.jumppark.domain.usecase.GetLogoutUseCase
import com.example.jumppark.presentation.viewmodel.UserViewModel

class UserViewModelFactory(
    private val application: Application,
    private val getLoginUseCase: GetLoginUseCase,
    private val getLogoutUseCase: GetLogoutUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(application, getLoginUseCase, getLogoutUseCase) as T
    }
}