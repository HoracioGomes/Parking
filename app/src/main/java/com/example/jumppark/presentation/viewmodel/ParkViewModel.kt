package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.Voucher
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.model.responses.establishment.ItemPrice
import com.example.jumppark.domain.usecase.GetSavedVoucherUseCase
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.domain.usecase.LaunchEntryUseCase
import com.example.jumppark.presentation.presentationUtils.isNetworkAvailable
import com.example.jumppark.ui.uiUtils.getDifferMinBetweenEntryExit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.util.Date

class ParkViewModel(
    private val app: Application,
    private val getEstablishmentUseCase: GetStablishmentInformationUseCase,
    private val launchEntryUseCase: LaunchEntryUseCase,
    private val getSavedVoucherUseCase: GetSavedVoucherUseCase
) : AndroidViewModel(app) {
    private val _establishmentLiveData = MutableLiveData<Resource<EstablishmentResponse>>()
    private val establishmentLiveData: LiveData<Resource<EstablishmentResponse>> get() = _establishmentLiveData

    private val _totalPayableValueLiveData = MutableLiveData<Double>()
    private val totalPayableValueLiveData: LiveData<Double> get() = _totalPayableValueLiveData

    private val _selectedPaymentMethodLiveData = MutableLiveData<String>()
    private val selectedPaymentMethodLiveData: LiveData<String> get() = _selectedPaymentMethodLiveData

    private val _consummatedMinutesLiveData = MutableLiveData<Int>()
    private val consummatedMinutesLiveData: LiveData<Int> get() = _consummatedMinutesLiveData

    private val _selectedPaymentMethodIdLiveData = MutableLiveData<Int>()
    private val selectedPaymentMethodIdLiveData: LiveData<Int> get() = _selectedPaymentMethodIdLiveData


    fun fetchEstablishmentInformations(establishmentId: String, userId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isNetworkAvailable(app)) {
                    _establishmentLiveData.postValue(Resource.Loading())
                    val requestResult = getEstablishmentUseCase.execute(
                        establishmentId = establishmentId,
                        userId = userId
                    )
                    _establishmentLiveData.postValue(requestResult)
                } else {
                    _establishmentLiveData.postValue(
                        Resource.Error(
                            data = null,
                            message = app.getString(R.string.internet_is_not_available)
                        )
                    )
                }
            } catch (e: Exception) {
                _establishmentLiveData.postValue(
                    Resource.Error(
                        data = null,
                        message = e.message.toString()
                    )
                )
            }
        }

    fun getEstablishmentData(): LiveData<Resource<EstablishmentResponse>> {
        return establishmentLiveData
    }

    fun getSelectedPaymentMethodValue(): LiveData<String> {
        return selectedPaymentMethodLiveData
    }

    fun setSelectedPaymentMethodValue(data: String) {
        _selectedPaymentMethodLiveData.value = data
    }

    fun getSelectedPaymentMethodId(): LiveData<Int> {
        return selectedPaymentMethodIdLiveData
    }

    fun setSelectedPaymentMethodId(data: Int) {
        _selectedPaymentMethodIdLiveData.value = data
    }

    fun getTotalPayableValue(): LiveData<Double> {
        return totalPayableValueLiveData
    }

    fun saveVoucher(voucher: Voucher) = viewModelScope.launch {
        launchEntryUseCase.execute(voucher)
    }

    fun getVouchers() = liveData {
        getSavedVoucherUseCase.execute().collect(FlowCollector { list ->
            emit(list)
        })
    }

    fun getParkedVouchers() = liveData {
        getSavedVoucherUseCase.execute(true).collect(FlowCollector { list ->
            emit(list)
        })
    }

    fun getTolerance(): Int {
        return _establishmentLiveData.value?.data?.data?.prices?.get(0)?.tolerance ?: 0
    }

    fun getmaximumPeriod(): Int {
        return _establishmentLiveData.value?.data?.data?.prices?.get(0)?.maximumPeriod ?: 0
    }

    fun getmaximumValue(): String {
        return _establishmentLiveData.value?.data?.data?.prices?.get(0)?.maximumValue ?: ""
    }

    fun getPrices(): List<ItemPrice>? {
        return _establishmentLiveData.value?.data?.data?.prices?.get(0)?.items
    }

    private fun calcLowerValuePosToleranceToPayableValue(entryDate: Date?) {
        _totalPayableValueLiveData.value = 0.0
        _consummatedMinutesLiveData.value = getDifferMinBetweenEntryExit(entryDate)
        val consummatedMinutes = consummatedMinutesLiveData.value
        var payableValue = totalPayableValueLiveData.value


        val priceItems = getPrices()
        if (consummatedMinutes != null) {
            if (consummatedMinutes > getTolerance()) {
                if (priceItems != null) {
                    payableValue = priceItems[0].price.toDouble()
                    for (itemPrice in priceItems) {
                        payableValue =
                            if (itemPrice.price.toDouble() < payableValue ?: 0.0) itemPrice.price.toDouble() else payableValue
                    }
                }
            }
        }
        _totalPayableValueLiveData.value = payableValue ?: 0.0
    }


    private fun calcPayableTableValue(entryDate: Date?) {
        calcLowerValuePosToleranceToPayableValue(entryDate)
        val priceItems = getPrices()
        val consummatedMinutes = consummatedMinutesLiveData.value
        var payableValue = totalPayableValueLiveData.value
        if (priceItems != null) {
            for (itemPrice in priceItems) {
                if (consummatedMinutes ?: 0 >= (itemPrice.period + getTolerance())) {
                    payableValue =
                        if (itemPrice.price.toDouble() > payableValue ?: 0.0) itemPrice.price.toDouble() else payableValue
                }
            }
        }
        _totalPayableValueLiveData.value = payableValue ?: 0.0
    }

    fun calcSinceAndLimitsToFinalPayableValue(entryDate: Date?): Double {
        calcPayableTableValue(entryDate)
        var initialPayableValue = totalPayableValueLiveData.value
        val consummatedMinutes = consummatedMinutesLiveData.value
        val priceItems = getPrices()
        if (priceItems != null) {
            for (itemPrice in priceItems) {
                if (itemPrice.since > 1 && consummatedMinutes ?: 0 >= itemPrice.since) {
                    val posConsummatedMinutes = consummatedMinutes ?: 0 - itemPrice.since
                    val posConsummatedHours = (posConsummatedMinutes / itemPrice.period)
                    val sinceValue = posConsummatedHours * (itemPrice.price.toDouble())
                    initialPayableValue = sinceValue + initialPayableValue!!
                }
            }
        }

        _totalPayableValueLiveData.value = initialPayableValue ?: 0.0

        return if (consummatedMinutes ?: 0 <= getmaximumPeriod() && initialPayableValue ?: 0.0 > getmaximumValue().toDouble()) {
            _totalPayableValueLiveData.value = getmaximumValue().toDouble()
            return totalPayableValueLiveData.value ?: 0.0
        } else {
            return totalPayableValueLiveData.value ?: 0.0
        }
    }


}