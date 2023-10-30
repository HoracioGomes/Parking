package com.example.jumppark.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
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
import com.example.jumppark.ui.uiUtils.getStringDate
import com.google.android.material.snackbar.Snackbar
import java.util.Date

class VehicleDetailsFragment : BaseFragment() {
    private lateinit var bind: FragmentVehicleDetailsBinding
    private var selectedVoucher: Voucher? = null
    var getExitDialog: AlertDialog? = null
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
        configButtonsListener(parkViewModel, view)
    }

    private fun configButtonsListener(
        parkViewModel: ParkViewModel,
        view: View
    ) {
        bind.btnGetExit.setOnClickListener {
            if (parkViewModel.getSelectedPaymentMethodValue().value?.isNotEmpty() == true) {
                getExitDialog = configDialog()
                getExitDialog?.show()
            } else {
                Snackbar.make(
                    view,
                    getString(R.string.alert_payment_method_not_selected), Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        bind.btnBackParkedList.setOnClickListener {
            findNavController().navigate(VehicleDetailsFragmentDirections.actionVehicleDetailsFragmentToParkedVehiclesFragment())
        }
    }

    private fun configDialog(): AlertDialog? {
        context?.let { context ->
            return DialogUtils(context).createDefaultDialog(
                getString(R.string.title_dialog_get_exit),
                getString(R.string.text_dialog_get_exit),
                dialogConfirmAction,
                dialogCancelAction
            )
        }

        return null
    }

    private val dialogConfirmAction =
        OnClickListener {

            selectedVoucher?.paymentMethodName = parkViewModel.getSelectedPaymentMethodValue().value
            selectedVoucher?.paymentMethodId =
                parkViewModel.getSelectedPaymentMethodId().value?.toLong()
            selectedVoucher?.parked = false
            selectedVoucher?.exitDate = getStringDate()
            selectedVoucher?.finished = true
            selectedVoucher?.paid = true
            selectedVoucher?.paidValue = parkViewModel.getTotalPayableValue().value ?: 0.0
            selectedVoucher?.paymentDate = getStringDate()

            selectedVoucher?.let { voucher -> parkViewModel.saveVoucher(voucher) }

            getExitDialog?.dismiss()

            Snackbar.make(bind.root, "Registrado!", Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(VehicleDetailsFragmentDirections.actionVehicleDetailsFragmentToParkedVehiclesFragment())
        }

    private val dialogCancelAction =
        OnClickListener {
            getExitDialog?.dismiss()
        }

    private fun configDetailsFields(selectedVoucher: Voucher?) {
        if (selectedVoucher != null) {
            bind.includeDetailsVehicle.modelTextView.text = selectedVoucher.model
            bind.includeDetailsVehicle.colorTextView.text = selectedVoucher.color
            bind.includeDetailsVehicle.plateTextView.text = selectedVoucher.plate
            bind.includeDetailsVehicle.entryHourTextView.text = selectedVoucher.entryDate
            bind.includeDetailsVehicle.predictedPeriodTextView.text =
                formatMinutes(selectedVoucher.predictedMin)
            bind.includeDetailsVehicle.predictedValueTextView.text =
                formatDoubleToReais(selectedVoucher.predictedValue)

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
        parkViewModel.setSelectedPaymentMethodValue("")
        parkViewModel.setSelectedPaymentMethodId(0)

        val paymentsMethod = parkViewModel.getEstablishmentData().value?.data?.data?.paymentMethods
        if (paymentsMethod != null) {
            for (method in paymentsMethod) {
                val radioButton = RadioButton(activity)
                radioButton.text = method.paymentMethodName
                radioButton.buttonTintList =
                    activity?.let {
                        ContextCompat.getColorStateList(
                            it.applicationContext,
                            R.color.radio_button_selector
                        )
                    }
                radioButton.setOnClickListener {
                    parkViewModel.setSelectedPaymentMethodValue(method.paymentMethodName)
                    parkViewModel.setSelectedPaymentMethodId(method.primitivePaymentMethodId)
                }
                bind.paymentsMethodRadioGroup.addView(radioButton)
                bind.paymentsMethodRadioGroup.gravity = Gravity.CENTER_HORIZONTAL
            }
        }
    }
}