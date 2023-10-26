package com.example.jumppark

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.jumppark.databinding.ActivityMainBinding
import com.example.jumppark.presentation.factory.BaseViewModelFactory
import com.example.jumppark.presentation.factory.EstablishmentViewModelFactory
import com.example.jumppark.presentation.factory.UserViewModelFactory
import com.example.jumppark.presentation.viewmodel.BaseViewModel
import com.example.jumppark.presentation.viewmodel.EstablishmentViewModel
import com.example.jumppark.presentation.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var baseViewModelFactory: BaseViewModelFactory

    @Inject
    lateinit var establishmentViewModelFactory: EstablishmentViewModelFactory

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

    lateinit var mainActivityBinding: ActivityMainBinding

    lateinit var progressBar: ProgressBar

    lateinit var mainBnv: BottomNavigationView
    companion object {
        lateinit var baseViewModel: BaseViewModel
        lateinit var establishmentViewModel: EstablishmentViewModel
        lateinit var userViewModel: UserViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        configActivityBinding()
        setContentView(mainActivityBinding.root)
        configProgressBar()
        configBottomNavigation()
        configToolBar()
        observeToolBar()
        observeBottomNavBar()
    }
    private fun initViewModels() {
        baseViewModel = ViewModelProvider(this, baseViewModelFactory)[BaseViewModel::class.java]
        establishmentViewModel = ViewModelProvider(
            this,
            establishmentViewModelFactory
        )[EstablishmentViewModel::class.java]
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    private fun configActivityBinding() {
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun configProgressBar() {
        progressBar = mainActivityBinding.mainProgressbar
    }

    private fun configBottomNavigation() {
        mainBnv = mainActivityBinding.mainBnv
    }

    private fun configToolBar() {
        setSupportActionBar(mainActivityBinding.toolbar);
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

    private fun observeToolBar() {
        baseViewModel.toolBarVisibility.observe(this, Observer { isVisible ->
            if (isVisible) {
                mainActivityBinding.toolbar.visibility = View.VISIBLE
            } else {
                mainActivityBinding.toolbar.visibility = View.GONE
            }

        })
    }
}