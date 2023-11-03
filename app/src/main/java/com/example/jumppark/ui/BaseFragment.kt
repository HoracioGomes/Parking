package com.example.jumppark.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.jumppark.MainActivity
import com.example.jumppark.R
import com.example.jumppark.presentation.viewmodel.BaseViewModel
import com.example.jumppark.presentation.viewmodel.ParkViewModel
import com.example.jumppark.presentation.viewmodel.UserViewModel
import com.example.jumppark.ui.uiUtils.BackButtonHandler
import com.example.jumppark.ui.uiUtils.DialogUtils
import com.example.jumppark.ui.uiUtils.FragmentNames

open class BaseFragment : Fragment() {
    protected lateinit var baseViewModel: BaseViewModel
    protected lateinit var parkViewModel: ParkViewModel
    protected lateinit var userViewModel: UserViewModel
    protected var finishDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = (activity as MainActivity).baseViewModel
        parkViewModel = (activity as MainActivity).parkViewModel
        userViewModel = (activity as MainActivity).userViewModel
        switchViewsVisibility()
    }

    override fun onResume() {
        super.onResume()
        BackButtonHandler.setOnBackPressed(createOnBackPressedCallback())
        BackButtonHandler.getCallback()
            ?.let { requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onPause() {
        super.onPause()
        BackButtonHandler.removeOnBackPressedCallback()
    }


    private fun switchViewsVisibility() {
        val childFragmentName = this.javaClass.simpleName
        when (childFragmentName) {
            FragmentNames.LoginFragment.toString() -> {
                baseViewModel.setBottomNavVisibility(false)
                baseViewModel.setToolBarVisibility(false)
                baseViewModel.setDrawerVisibility(false)
            }

            FragmentNames.SplashScreenFragment.toString() -> {
                baseViewModel.setBottomNavVisibility(false)
                baseViewModel.setToolBarVisibility(false)
                baseViewModel.setDrawerVisibility(false)
            }

            else -> {
                baseViewModel.setBottomNavVisibility(true)
                baseViewModel.setToolBarVisibility(true)
                baseViewModel.setDrawerVisibility(true)
            }
        }
    }

    protected fun setToolBarTitle(title: String) {
        val activity = activity as? MainActivity
        if (activity != null) {
            activity.mainActivityBinding.toolbarTitle.text = title
        }
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

    private fun createOnBackPressedCallback(): OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (this@BaseFragment is HomeFragment) {
                    finishDialog = createFinishDialog()
                    finishDialog?.show()
                } else {
                    findNavController().navigate(R.id.homeFragment)
                }
            }
        }
    }

    private fun createFinishDialog(): AlertDialog? {
        return context?.let {
            DialogUtils(it).createDefaultDialog(
                getString(R.string.exit_the_application_title),
                "", confirmExitDialogClick, cancelExistDialogClick
            )
        }
    }

    private val confirmExitDialogClick = OnClickListener {
        requireActivity().finishAffinity()
    }

    private val cancelExistDialogClick = OnClickListener {
        finishDialog?.dismiss()
    }


}