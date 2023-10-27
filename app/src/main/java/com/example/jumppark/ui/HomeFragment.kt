package com.example.jumppark.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jumppark.MainActivity
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.databinding.FragmentHomeBinding
import com.example.jumppark.ui.uiUtils.SharedPreferencesKeys
import com.example.jumppark.ui.uiUtils.formatDoubleToReais
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
        LoadRemoteData()
        setDataObserver()
    }

    private fun setDataObserver() {
        baseViewModel.loadedData.observe(viewLifecycleOwner, Observer { loaded ->
            if (loaded) {
                setInformationsIntoLayout()
                hideProgressbar()
            }
        })
    }

    private fun setInformationsIntoLayout() {
        binding.parkingVehicles.text = baseViewModel.getTotalParkedVehicles().toString()
        binding.informationsInclude.paymentsAmountCollected.text =
            formatDoubleToReais(baseViewModel.getTotalVouchersValue())
        binding.informationsInclude.billCollected.text =
            formatDoubleToReais(baseViewModel.getTotalBillPayment())
        binding.informationsInclude.moneyCollected.text =
            formatDoubleToReais(baseViewModel.getTotalMoneyPayment())
        binding.informationsInclude.pixCollected.text =
            formatDoubleToReais(baseViewModel.getTotalPixPayment())
        binding.informationsInclude.creditCollected.text =
            formatDoubleToReais(baseViewModel.getTotalCreditPayment())
        binding.informationsInclude.debitCollected.text =
            formatDoubleToReais(baseViewModel.getTotalDebitPayment())
    }

    private fun LoadRemoteData() {

        parkViewModel.getEstablishmentData()
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        loadLocalData()
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

            }

        parkViewModel.fetchEstablishmentInformations(
            establishmentId = "${
                sharedPreferences.getString(
                    "${SharedPreferencesKeys.establishmentId}",
                    ""
                )
            }",
            userId = "${sharedPreferences.getString("${SharedPreferencesKeys.userId}", "")}"
        )

    }

}