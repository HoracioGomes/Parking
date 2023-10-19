package com.example.jumppark.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.jumppark.R
import com.example.jumppark.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : BaseFragment() {

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
            findNavController()
                .navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())
        }, 3000)
    }


}