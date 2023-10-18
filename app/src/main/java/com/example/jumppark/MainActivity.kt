package com.example.jumppark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.jumppark.databinding.ActivityMainBinding
import com.example.jumppark.presentation.factory.BaseViewModelFactory
import com.example.jumppark.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var baseViewModelFactory: BaseViewModelFactory
    companion object {
        lateinit var baseViewModel: BaseViewModel
        lateinit var mainActivitybinding: ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivitybinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivitybinding.root)

        baseViewModel = ViewModelProvider(this, baseViewModelFactory)[BaseViewModel::class.java]
    }
}