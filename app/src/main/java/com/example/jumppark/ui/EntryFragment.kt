package com.example.jumppark.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.jumppark.MainActivity
import com.example.jumppark.R
import com.example.jumppark.data.model.Voucher
import com.example.jumppark.data.model.responses.establishment.ItemPrice
import com.example.jumppark.databinding.FragmentEntryBinding
import com.example.jumppark.presentation.viewmodel.ParkViewModel
import com.example.jumppark.ui.adapters.PriceSpinnerAdapter
import com.example.jumppark.ui.uiUtils.getDate
import com.google.android.material.snackbar.Snackbar

class EntryFragment : BaseFragment() {
    private lateinit var binding: FragmentEntryBinding
    private var pricesList: MutableList<ItemPrice> = mutableListOf()
    private var selectedPrice: Double = 0.0
    private var selectedPeriod: Int = 0
    private var carModel: String = ""
    private var carPlate: String = ""
    private var carColor: String = ""
    private var establishmentId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarTitle(getString(R.string.entry_toolbar_title))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEntryBinding.bind(view)
        val prices = parkViewModel.getEstablishmentData().value?.data?.data?.prices?.get(0)?.items
        addPricesToList(prices)
        setSpinnerAdapter()
        spinnerListener()
        clickBtnEntry()
    }

    private fun clickBtnEntry() {
        binding.btnEntry.setOnClickListener {
            carModel = binding.etModelo.text.toString()
            carPlate = binding.etPlaca.text.toString()
            carColor = binding.etCor.text.toString()

            if (checkFields()) {
                val entrydate = getDate()
                val voucher = createEntryVoucher(entrydate)
                parkViewModel.saveVoucher(voucher)
                clearInputTexts()
                Snackbar.make(binding.root, "Entrada Registrada", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun clearInputTexts() {
        binding.etModelo.text?.clear()
        binding.etPlaca.text?.clear()
        binding.etCor.text?.clear()
    }

    private fun createEntryVoucher(entryDate: String) = Voucher(
        id = null,
        model = carModel,
        plate = carPlate,
        color = carColor,
        parked = true,
        predictedValue = selectedPrice,
        predictedMin = selectedPeriod,
        paid = false,
        paymentMethodId = null,
        paymentMethodName = null,
        finished = false,
        entryDate = entryDate,
        exitDate = null,
        paymentDate = null,
        establishmentId = establishmentId
    )

    private fun checkFields() = (selectedPrice > 0
            && selectedPeriod > 0
            && carModel.trim().isNotEmpty()
            && carColor.trim().isNotEmpty()
            && carPlate.trim().isNotEmpty()
            && establishmentId > 0)

    private fun spinnerListener() {
        binding.pricesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItemPrice = pricesList[position]
                selectedPrice = selectedItemPrice.price.toDouble()
                selectedPeriod = selectedItemPrice.period
                establishmentId = selectedItemPrice.establishmentId.toLong()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun setSpinnerAdapter() {
        val spinnerAdapter =
            context?.let { PriceSpinnerAdapter(it, pricesList) }

        binding.pricesSpinner.adapter = spinnerAdapter
    }

    private fun addPricesToList(prices: List<ItemPrice>?) {
        if (prices != null) {
            for (itemPrice in prices) {
                if (itemPrice.since == 0) {
                    pricesList.add(itemPrice)
                }
            }

        }
    }

}