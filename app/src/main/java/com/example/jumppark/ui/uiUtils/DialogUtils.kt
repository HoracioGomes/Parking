package com.example.jumppark.ui.uiUtils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnClickListener
import com.example.jumppark.databinding.DialogLayoutBinding

class DialogUtils(private val context: Context) {
    private val dialogBuilder = AlertDialog.Builder(context)
    private var alertDialog: AlertDialog? = null

    fun showDefaultDialog(title: String, text: String, confirm: OnClickListener?, cancel: OnClickListener?) {
        val layoutInflater = LayoutInflater.from(context)
        val dialogLayoutBinding = DialogLayoutBinding.inflate(layoutInflater, null, false)
        dialogLayoutBinding.titleDialog.text = title
        dialogLayoutBinding.textDialog.text = text
        dialogLayoutBinding.dialogConfirmBtn.setOnClickListener(confirm)

        dialogLayoutBinding.dialogCancelBtn.setOnClickListener(cancel)
        dialogBuilder.setView(dialogLayoutBinding.root)
        alertDialog = dialogBuilder.create()
        alertDialog?.show()
    }

}