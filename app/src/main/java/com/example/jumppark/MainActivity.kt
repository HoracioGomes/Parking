package com.example.jumppark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jumppark.databinding.ActivityMainBinding
import com.example.jumppark.presentation.factory.BaseViewModelFactory
import com.example.jumppark.presentation.factory.EstablishmentViewModelFactory
import com.example.jumppark.presentation.viewmodel.BaseViewModel
import com.example.jumppark.presentation.viewmodel.EstablishmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var baseViewModelFactory: BaseViewModelFactory
    @Inject
    lateinit var establishmentViewModelFactory: EstablishmentViewModelFactory
    lateinit var mainActivityBinding: ActivityMainBinding

    companion object {
        lateinit var baseViewModel: BaseViewModel
        lateinit var establishmentViewModel: EstablishmentViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        configToolBar()
        configBottomNavBar()
        observeToolBar()
        observeBottomNavBar()
    }

    private fun initViewModels() {
        baseViewModel = ViewModelProvider(this, baseViewModelFactory)[BaseViewModel::class.java]
        establishmentViewModel = ViewModelProvider(
            this,
            establishmentViewModelFactory
        )[EstablishmentViewModel::class.java]
    }

    private fun configToolBar() {
        setSupportActionBar(mainActivityBinding.toolbar);
    }

    private fun configBottomNavBar() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment?.findNavController()
        navController?.let { mainActivityBinding.mainBnv.setupWithNavController(it) }
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