package com.example.jumppark.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.jumppark.MainActivity
import com.example.jumppark.R
import com.example.jumppark.presentation.viewmodel.BaseViewModel
import com.example.jumppark.presentation.viewmodel.ParkViewModel
import com.example.jumppark.presentation.viewmodel.UserViewModel
import com.example.jumppark.ui.uiUtils.FragmentNames

open class BaseFragment : Fragment() {
    protected lateinit var baseViewModel: BaseViewModel
    protected lateinit var parkViewModel: ParkViewModel
    protected lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = (activity as MainActivity).baseViewModel
        parkViewModel = (activity as MainActivity).parkViewModel
        userViewModel = (activity as MainActivity).userViewModel
        switchViewsVisibility()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    protected fun loadInitialLocalData() {
        showProgressbar()
        baseViewModel.setLoadedData(false)
        parkViewModel.getVouchers().observe(viewLifecycleOwner, Observer { list ->
            baseViewModel.setVouchers(list)
        })

    }

    protected fun loadParkedLocalData() {
        showProgressbar()
        baseViewModel.setLoadedDataParkedList(false)
        parkViewModel.getParkedVouchers().observe(viewLifecycleOwner, Observer { list ->
            baseViewModel.setParkedVouchers(list)
        })

    }

    protected fun showProgressbar() {
        (activity as MainActivity).showLoading()

    }

    protected fun hideProgressbar() {
        (activity as MainActivity).hideLoading()

    }

}