package com.example.jumppark.data.model.responses.establishment

data class EstablishmentSettings(
    val accountId: Int,
    val cashReserve: String,
    val establishmentId: Int,
    val manualCovenant: Int,
    val manualTime: Int,
    val pathLogo: Any,
    val prePaidExit: Int,
    val preReserveToleranceTime: Any,
    val print: Int,
    val printNote: Any,
    val recurrentClientEntryOption: Int,
    val requireReceiptCovantTypePrice: Int,
    val requireReceiptExpense: Int,
    val reserveActivation: Int,
    val rpsAutomaticGenerate: Any,
    val serviceOnly: Int,
    val withdrawal: Int
)