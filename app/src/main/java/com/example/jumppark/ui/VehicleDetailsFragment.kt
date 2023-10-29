package com.example.jumppark.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.navArgs
import com.example.jumppark.MainActivity
import com.example.jumppark.R
import com.example.jumppark.data.model.Voucher
import com.example.jumppark.databinding.FragmentVehicleDetailsBinding
import com.example.jumppark.presentation.viewmodel.ParkViewModel
import com.example.jumppark.ui.uiUtils.DialogUtils
import com.example.jumppark.ui.uiUtils.formatDoubleToReais
import com.example.jumppark.ui.uiUtils.formatMinutes
import com.example.jumppark.ui.uiUtils.formatStringToDate
import com.example.jumppark.ui.uiUtils.getDifferMinBetweenEntryExit
import java.util.Date

class VehicleDetailsFragment : BaseFragment() {
    private lateinit var bind: FragmentVehicleDetailsBinding
    private var selectedVoucher: Voucher? = null
    private var selectedPaymentName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarTitle(getString(R.string.vehicle_details_toolbar_title))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vehicle_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val parkViewModel = (activity as MainActivity).parkViewModel
        bind = FragmentVehicleDetailsBinding.bind(view)
        val args: VehicleDetailsFragmentArgs by navArgs()
        selectedVoucher = args.selectedVoucher

        configDetailsFields(selectedVoucher)
        configPaymentMethods(parkViewModel)

        bind.btnGetExit.setOnClickListener {
            configDialog()
        }
    }

    private fun configDialog() {
        context?.let { context ->
            DialogUtils(context).showDefaultDialog(
                "Teste",
                "testando",
                dialogConfirmAction,
                dialogCancelAction
            )
        }
    }

    private val dialogConfirmAction =
        OnClickListener { Log.d("teste", "Clicou confirm") }

    private val dialogCancelAction =
        OnClickListener { Log.d("teste", "Clicou calcel") }

    private fun configDetailsFields(selectedVoucher: Voucher?) {
        if (selectedVoucher != null) {
            bind.includeDetailsVehicle.modelTextView.text = selectedVoucher?.model
            bind.includeDetailsVehicle.colorTextView.text = selectedVoucher?.color
            bind.includeDetailsVehicle.plateTextView.text = selectedVoucher?.plate
            bind.includeDetailsVehicle.entryHourTextView.text = selectedVoucher?.entryDate
            bind.includeDetailsVehicle.predictedPeriodTextView.text =
                formatMinutes(selectedVoucher?.predictedMin)
            bind.includeDetailsVehicle.predictedValueTextView.text =
                formatDoubleToReais(selectedVoucher?.predictedValue)

            bind.includeDetailsVehicle.consummatedPeriod.text =
                setConsummatedPeriod(formatStringToDate(selectedVoucher.entryDate))

            bind.includeDetailsVehicle.totalPayable.text =
                setTotalPayable(formatStringToDate(selectedVoucher.entryDate))

        }
    }

    private fun setConsummatedPeriod(entryDate: Date?): String {
        return formatMinutes(getDifferMinBetweenEntryExit(entryDate))
    }

    private fun setTotalPayable(entryDate: Date?): String {
        return formatDoubleToReais(parkViewModel.calcSinceAndLimitsToFinalPayableValue(entryDate))
    }

    private fun configPaymentMethods(parkViewModel: ParkViewModel) {
        val paymentsMethod = parkViewModel.getEstablishmentData().value?.data?.data?.paymentMethods
        if (paymentsMethod != null) {
            for (method in paymentsMethod) {
                val radioButton = RadioButton(activity)
                radioButton.text = method.paymentMethodName
                radioButton.setOnClickListener {
                    selectedPaymentName = method.paymentMethodName
                }
                bind.paymentsMethodRadioGroup.addView(radioButton)
            }
        }
    }
}