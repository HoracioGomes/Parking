package com.example.jumppark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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
        lateinit var mainActivityBinding: ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = ViewModelProvider(this, baseViewModelFactory)[BaseViewModel::class.java]
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        observeBottomNavBar()
    }

    private fun observeBottomNavBar() {
        baseViewModel.bottomNavVisibility.observe(
            this,
            Observer { isVisible ->
                if (isVisible) {
                    mainActivityBinding.mainBnv.visibility = View.VISIBLE
                } else {
                    mainActivityBinding.mainBnv.visibility = View.GONE
                }
            })
    }
}