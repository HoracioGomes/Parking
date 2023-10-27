package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jumppark.data.model.Voucher
import com.example.jumppark.ui.uiUtils.PaymentMethodsName

class BaseViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    private val _bottomNavVisibility = MutableLiveData<Boolean>()
    private val _toolBarVisibility = MutableLiveData<Boolean>()
    private val _vouchers = MutableLiveData<MutableList<Voucher>>()
    private val _loadedData = MutableLiveData<Boolean>()
    val bottomNavVisibility: LiveData<Boolean> get() = _bottomNavVisibility
    val toolBarVisibility: LiveData<Boolean> get() = _toolBarVisibility
    val vouchers: LiveData<MutableList<Voucher>> get() = _vouchers
    val loadedData: LiveData<Boolean> get() = _loadedData

    fun setBottomNavVisibility(isVisible: Boolean) {
        _bottomNavVisibility.value = isVisible
    }

    fun setToolBarVisibility(isVisible: Boolean) {
        _toolBarVisibility.value = isVisible
    }

    fun setVouchers(vouchers: List<Voucher>) {
        _vouchers.value = vouchers.toMutableList()
        _loadedData.value = true
    }

    fun getTotalVouchersValue(): Double? {
        return _vouchers.value?.sumOf { it.paidValue }
    }

    fun getTotalMoneyPayment(): Double? {
        return _vouchers.value?.sumOf { if (it.paymentMethodName == PaymentMethodsName.Dinheiro.toString()) it.paidValue else 0.0 }
    }

    fun getTotalPixPayment(): Double? {
        return _vouchers.value?.sumOf { if (it.paymentMethodName == PaymentMethodsName.Pix.toString()) it.paidValue else 0.0 }
    }

    fun getTotalBillPayment(): Double? {
        return _vouchers.value?.sumOf { if (it.paymentMethodName == PaymentMethodsName.Boleto.toString()) it.paidValue else 0.0 }
    }

    fun getTotalDebitPayment(): Double? {
        return _vouchers.value?.sumOf { if (it.paymentMethodName == PaymentMethodsName.Débito.toString()) it.paidValue else 0.0 }
    }

    fun getTotalCreditPayment(): Double? {
        return _vouchers.value?.sumOf { if (it.paymentMethodName == PaymentMethodsName.Crédito.toString()) it.paidValue else 0.0 }
    }

    fun getTotalParkedVehicles(): Int {
        return _vouchers.value?.count { it.parked == true } ?: 0
    }
}