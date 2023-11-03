package com.example.jumppark.ui.uiUtils

import androidx.activity.OnBackPressedCallback

object BackButtonHandler {
    private var onBackPressedCallback: OnBackPressedCallback? = null

    fun setOnBackPressed(callback: OnBackPressedCallback) {
        onBackPressedCallback = callback
    }

    fun removeOnBackPressedCallback() {
        onBackPressedCallback = null
    }

    fun onBackPressed() {
        onBackPressedCallback?.handleOnBackPressed()
    }

    fun getCallback(): OnBackPressedCallback? {
        return onBackPressedCallback
    }
}