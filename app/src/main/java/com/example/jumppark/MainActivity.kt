package com.example.jumppark

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jumppark.databinding.ActivityMainBinding
import com.example.jumppark.presentation.factory.BaseViewModelFactory
import com.example.jumppark.presentation.factory.ParkViewModelFactory
import com.example.jumppark.presentation.factory.UserViewModelFactory
import com.example.jumppark.presentation.viewmodel.BaseViewModel
import com.example.jumppark.presentation.viewmodel.ParkViewModel
import com.example.jumppark.presentation.viewmodel.UserViewModel
import com.example.jumppark.ui.uiUtils.SharedPreferencesKeys
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var baseViewModelFactory: BaseViewModelFactory

    @Inject
    lateinit var parkViewModelFactory: ParkViewModelFactory

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var loading: AlertDialog
    lateinit var mainBnv: BottomNavigationView
    lateinit var mToogle: ActionBarDrawerToggle

    lateinit var baseViewModel: BaseViewModel
    lateinit var parkViewModel: ParkViewModel
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        configActivityBinding()
        setContentView(mainActivityBinding.root)
        configBottomNavigation()
        configToolBar()
        configDrawer()
        initVisibilityWidgetsObservers()
        configAlertLoading()

    }

    private fun initVisibilityWidgetsObservers() {
        observeToolBar()
        observeBottomNavBar()
        observeDrawer()
    }

    private fun configDrawer() {
        mToogle = ActionBarDrawerToggle(
            this, mainActivityBinding.appDrawer,
            R.string.open_drawer, R.string.close_drawer
        )
        mToogle.syncState()
        mainActivityBinding.appDrawer.addDrawerListener(mToogle)
        mainActivityBinding.mainNavView.setNavigationItemSelectedListener(this)
        mainActivityBinding.mainNavView.itemIconTintList = null
        val userNameMenu = mainActivityBinding.mainNavView.getHeaderView(0)
        val userNameTv = userNameMenu.findViewById<TextView>(R.id.name_usuario_header)
        userNameTv.text = sharedPreferences.getString(SharedPreferencesKeys.username.toString(), "")
    }

    private fun configAlertLoading() {
        val progresBar = layoutInflater.inflate(R.layout.progressbar_layout, null)
        loading = AlertDialog.Builder(this)
            .setView(progresBar)
            .setCancelable(false)
            .create()
        loading.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun initViewModels() {
        baseViewModel = ViewModelProvider(this, baseViewModelFactory)[BaseViewModel::class.java]
        parkViewModel = ViewModelProvider(
            this,
            parkViewModelFactory
        )[ParkViewModel::class.java]
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    private fun configActivityBinding() {
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun configBottomNavigation() {
        mainBnv = mainActivityBinding.mainBnv
    }

    private fun configToolBar() {
        setSupportActionBar(mainActivityBinding.mainToolbar);
        val icMenu = mainActivityBinding.mainToolbar.findViewById<ImageView>(R.id.ic_drawer_menu)
        icMenu.setOnClickListener {
            mainActivityBinding.appDrawer.openDrawer(GravityCompat.START)
        }
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
                mainActivityBinding.mainToolbar.visibility = View.VISIBLE
            } else {
                mainActivityBinding.mainToolbar.visibility = View.GONE
            }

        })
    }

    private fun observeDrawer() {
        baseViewModel.drawerVisibility.observe(this, Observer { isVisible ->
            if (isVisible) {
                mainActivityBinding.appDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                mainActivityBinding.appDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        })
    }

    fun showLoading() {
        if (!loading.isShowing) {
            loading.show()
        }
    }

    fun hideLoading() {
        if (loading.isShowing) {
            loading.dismiss()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}