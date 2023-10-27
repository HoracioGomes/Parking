package com.example.jumppark.ui.uiUtils

import java.util.Calendar

fun getDate(): String {
    val cal = Calendar.getInstance()
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH) + 1
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val min = cal.get(Calendar.MINUTE)
    val seg = cal.get(Calendar.SECOND)
    return "$day/$month/$year $hour:$min:$seg"
}