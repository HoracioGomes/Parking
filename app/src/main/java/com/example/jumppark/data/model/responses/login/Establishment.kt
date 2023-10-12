package com.example.jumppark.data.model.responses.login

data class Establishment(
    val accountId: Int,
    val addresses: List<Addresse>,
    val appPermission: Int,
    val cashReserve: String,
    val companyName: String,
    val debitWarning: Int,
    val documentId: String,
    val email: String,
    val establishmentCode: String,
    val establishmentId: Int,
    val establishmentName: String,
    val establishmentTypes: List<Int>,
    val phone: String,
    val premiumPackage: Int,
    val rpsCount: Int,
    val serviceOrderCount: Int,
    val status: Int,
    val userId: Int,
    val vacanciesMarks: Int,
    val vacanciesOthers: Int
)