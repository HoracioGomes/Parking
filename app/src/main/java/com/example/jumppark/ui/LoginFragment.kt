package com.example.jumppark.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.databinding.FragmentLoginBinding
import com.example.jumppark.ui.uiUtils.SharedPreferencesKeys
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)
        initObservers()
        binding.btnLogin.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()
            val dataUser = UserLogin(email = email, password = password)
            userViewModel.fetchLogin(dataUser)
        }
    }

    private fun initObservers() {
        userViewModel.getLoginData().observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {

                    sharedPreferences.edit().putString("${SharedPreferencesKeys.username}", response.data?.data?.user?.name)
                        .putString("${SharedPreferencesKeys.token}", response.data?.data?.user?.accessToken)
                        .putString("${SharedPreferencesKeys.establishmentId}", response.data?.data?.session?.establishmentId.toString())
                        .putString("${SharedPreferencesKeys.userId}", response.data?.data?.user?.userId.toString())
                        .putString("${SharedPreferencesKeys.sessonId}", response.data?.data?.session?.sessionId.toString())
                        .apply()

                    hideProgressbar()
                    findNavController().navigate(
                        LoginFragmentDirections
                            .actionLoginFragmentToHomeFragment()
                    )
                }

                is Resource.Error -> {
                    hideProgressbar()

                    response.message.let {
                        Snackbar.make(
                            binding.root,
                            response.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressbar()
                }
            }

        })
    }
}