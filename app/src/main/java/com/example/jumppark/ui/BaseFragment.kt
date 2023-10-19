package com.example.jumppark.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jumppark.MainActivity.Companion.baseViewModel

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        switchBottomNavBarVisibility()
    }

    private fun switchBottomNavBarVisibility() {
        val childFragmentName = this.javaClass.simpleName
        when (childFragmentName) {
            FragmentNames.LoginFragment.toString() -> baseViewModel.setBottomNavVisibility(false)
            FragmentNames.SplashScreenFragment.toString() -> baseViewModel.setBottomNavVisibility(false)
            else -> baseViewModel.setBottomNavVisibility(true)
        }
    }
}