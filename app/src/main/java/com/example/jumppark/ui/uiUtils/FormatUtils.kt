package com.example.jumppark.ui.uiUtils

import java.text.DecimalFormat

fun formatPrices(minutes: Int?, value: String?): String {
    val formatedValue = value?.replace(".", ",")

    if (minutes != null && value != null) {
        if (minutes < 60) {
            return "Até $minutes Minutos - R$ $formatedValue"
        } else {
            val hour = minutes / 60
            val min = minutes % 60

            val formatedHour = if (hour == 1) "$hour hora" else if (hour > 1) "$hour horas" else ""
            val formatedMin =
                if (min == 1) "$min minuto" else if (hour > 1) " e $min minutos" else ""
            return "Até $formatedHour$formatedMin - R$ $formatedValue"
        }
    }

    return ""
}

fun formatMinutes(minutes: Int?): String {
    if (minutes != null) {
        if (minutes < 60) {
            return if (minutes == 1) "$minutes minuto" else if (minutes > 1) "$minutes minutos" else ""
        } else {
            val hour = minutes / 60
            val min = minutes % 60

            val formatedHour = if (hour == 1) "$hour hora" else if (hour > 1) "$hour horas" else "00:"
            val formatedMin =
                if (min == 1) "$min minuto" else if (hour > 1) " e $min minutos" else "00"

            return "$formatedHour$formatedMin"
        }
    }
    return "00:00"
}

fun formatDoubleToReais(valor: Double?): String {
    val formato = DecimalFormat("R$ #,##0.00")
    return formato.format(valor)
}

