package com.example.jumppark.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.jumppark.R
import com.example.jumppark.databinding.FragmentSplashScreenBinding
import com.example.jumppark.ui.uiUtils.SharedPreferencesKeys
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : BaseFragment() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentSplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSplashScreenBinding.bind(view)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            checkIfLogged()
        }, 3000)
    }

    private fun checkIfLogged() {
        val options = NavOptions.Builder()
            .setPopUpTo(R.id.splashScreenFragment, true)
            .build()
        if ((sharedPreferences.getString(
                "${SharedPreferencesKeys.token}",
                ""
            )?.length ?: 0) > 1
        ) findNavController().navigate(R.id.homeFragment, null, options) else {
            findNavController()
                .navigate(R.id.loginFragment, null, options)
        }
    }


}