package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jumppark.data.model.Voucher

class BaseViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    private val _bottomNavVisibility = MutableLiveData<Boolean>()
    private val _toolBarVisibility = MutableLiveData<Boolean>()
    private val _vouchers = MutableLiveData<MutableList<Voucher>>()
    val bottomNavVisibility: LiveData<Boolean> get() = _bottomNavVisibility
    val toolBarVisibility: LiveData<Boolean> get() = _toolBarVisibility
    val vouchers: LiveData<MutableList<Voucher>> get() = _vouchers

    fun setBottomNavVisibility(isVisible: Boolean) {
        _bottomNavVisibility.value = isVisible
    }

    fun setToolBarVisibility(isVisible: Boolean) {
        _toolBarVisibility.value = isVisible
    }

    fun setVouchers(vouchers:List<Voucher>){
        _vouchers.value = vouchers.toMutableList()
    }
}