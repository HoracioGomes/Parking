package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.model.responses.logout.LogoutResponse
import com.example.jumppark.domain.usecase.GetLoginUseCase
import com.example.jumppark.domain.usecase.GetLogoutUseCase
import com.example.jumppark.presentation.presentationUtils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val app: Application,
    private val loginUseCase: GetLoginUseCase,
    private val logoutUseCase: GetLogoutUseCase
) : AndroidViewModel(app) {
    private val _userLoginLiveData: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    private val _userLogoutLiveData: MutableLiveData<Resource<LogoutResponse>> = MutableLiveData()

    val userLoginLiveData: LiveData<Resource<LoginResponse>> get() = _userLoginLiveData
    val userLogoutLiveData: LiveData<Resource<LogoutResponse>> get() = _userLogoutLiveData

    fun fetchLogin(user: UserLogin) {
        viewModelScope.launch(Dispatchers.IO) {
            _userLoginLiveData.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val requestResult = loginUseCase.execute(user)
                    _userLoginLiveData.postValue(requestResult)
                } else {
                    _userLoginLiveData.postValue(Resource.Error(data = null, message = app.getString(R.string.internet_is_not_available)))
                }
            } catch (e: Exception) {
                _userLoginLiveData.postValue(Resource.Error(data = null, message = e.message.toString()))
            }
        }
    }

    fun fetchLogout(userId: String, establishmentId: String, sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _userLogoutLiveData.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val requestResult = logoutUseCase.execute(
                        userId = userId,
                        establishmentId = establishmentId,
                        sessionId = sessionId
                    )
                    _userLogoutLiveData.postValue(requestResult)
                } else {
                    _userLogoutLiveData.postValue(Resource.Error(data = null, message = app.getString(R.string.internet_is_not_available)))
                }
            } catch (e: Exception) {
                _userLogoutLiveData.postValue(Resource.Error(data = null, message = e.message.toString()))
            }
        }
    }

    fun getLoginData(): LiveData<Resource<LoginResponse>> {
        return userLoginLiveData
    }

    fun getLogoutData(): LiveData<Resource<LogoutResponse>> {
        return userLogoutLiveData
    }
}