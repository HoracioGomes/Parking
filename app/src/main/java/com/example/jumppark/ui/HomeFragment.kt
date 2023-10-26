package com.example.jumppark.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jumppark.MainActivity
import com.example.jumppark.MainActivity.Companion.establishmentViewModel
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.databinding.FragmentHomeBinding
import com.example.jumppark.ui.uiUtils.SharedPreferencesKeys
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarTitle(getString(R.string.home_toolbar_title))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        findNavController()?.let { (activity as MainActivity).mainBnv.setupWithNavController(it) }

        establishmentViewModel.fetchEstablishmentInformations(
            establishmentId = "${sharedPreferences.getString("${SharedPreferencesKeys.establishmentId}", "")}",
            userId = "${sharedPreferences.getString("${SharedPreferencesKeys.userId}", "")}"

        )
        establishmentViewModel.getEstablishmentData()
            .observe(viewLifecycleOwner, Observer { response ->

                when (response) {
                    is Resource.Success -> {
                        hideProgressbar()
                        Log.d("Teste", response.data?.data?.paymentMethods?.size.toString())
                    }

                    is Resource.Loading -> {
                        showProgressbar()
                    }

                    is Resource.Error -> {
                        hideProgressbar()
                        val message = response.message
                        message?.let {
                            Snackbar.make(
                                binding.root,
                                it, Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            })
    }

}