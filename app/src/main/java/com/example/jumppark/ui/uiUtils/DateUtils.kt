package com.example.jumppark.ui.uiUtils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))
fun getStringDate(): String {
    val cal = Calendar.getInstance()
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH) + 1
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val min = cal.get(Calendar.MINUTE)
    val seg = cal.get(Calendar.SECOND)
    return "$day/$month/$year $hour:$min:$seg"
}

fun getFormatedFinalDate(): Date? {
    val dateString = simpleDateFormat.format(Date())
    return simpleDateFormat.parse(dateString)
}

fun formatStringToDate(dateString: String?): Date? {
    return dateString?.let { simpleDateFormat.parse(it) }
}

fun getDifferMinBetweenEntryExit(entryDate: Date?): Int {
    val finalDate: Date? = getFormatedFinalDate()

    val entryMil: Long = entryDate?.time ?: 0
    val finalMil: Long = finalDate?.time ?: 0

    val diffMil: Long = finalMil - entryMil
    val diffMinutes: Int = (diffMil / (1000 * 60)).toInt()

    return diffMinutes
}

