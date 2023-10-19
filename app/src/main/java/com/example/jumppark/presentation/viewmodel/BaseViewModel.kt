package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BaseViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    private val _bottomNavVisibility = MutableLiveData<Boolean>()
    private val _toolBarVisibility = MutableLiveData<Boolean>()
    val bottomNavVisibility: LiveData<Boolean> get() = _bottomNavVisibility
    val toolBarVisibility: LiveData<Boolean> get() = _toolBarVisibility

    fun setBottomNavVisibility(isVisible: Boolean) {
        _bottomNavVisibility.value = isVisible
    }

    fun setToolBarVisibility(isVisible: Boolean) {
        _toolBarVisibility.value = isVisible
    }
}