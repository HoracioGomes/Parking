package com.example.jumppark.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jumppark.MainActivity
import com.example.jumppark.MainActivity.Companion.baseViewModel
import com.example.jumppark.ui.uiUtils.FragmentNames

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        switchViewsVisibility()
    }

    private fun switchViewsVisibility() {
        val childFragmentName = this.javaClass.simpleName
        when (childFragmentName) {
            FragmentNames.LoginFragment.toString() -> {
                baseViewModel.setBottomNavVisibility(false)
                baseViewModel.setToolBarVisibility(false)
            }

            FragmentNames.SplashScreenFragment.toString() -> {
                baseViewModel.setBottomNavVisibility(false)
                baseViewModel.setToolBarVisibility(false)
            }

            else -> {
                baseViewModel.setBottomNavVisibility(true)
                baseViewModel.setToolBarVisibility(true)

            }
        }
    }

    protected fun setToolBarTitle(title: String) {
        (activity as MainActivity).supportActionBar?.title = title
    }
}