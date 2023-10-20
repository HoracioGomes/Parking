package com.example.jumppark.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.jumppark.MainActivity.Companion.establishmentViewModel
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarTitle("Home")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)

        establishmentViewModel.fetchEstablishmentInformations(
            establishmentId = "",
            userId = "")
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
                        val message = response.message
                        message?.let {
                            Snackbar.make(binding.root,
                                it, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }

            })
    }

    private fun showProgressbar() {
        binding.homeProgressbar.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.homeProgressbar.visibility = View.GONE

    }

}