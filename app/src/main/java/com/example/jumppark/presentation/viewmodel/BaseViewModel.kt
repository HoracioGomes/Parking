package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BaseViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    private val _bottomNavVisibility = MutableLiveData<Boolean>()
    val bottomNavVisibility: LiveData<Boolean> get() = _bottomNavVisibility

    fun setBottomNavVisibility(isVisible: Boolean) {
        _bottomNavVisibility.value = isVisible
    }
}