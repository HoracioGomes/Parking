package com.example.jumppark.data.model.responses.establishment

data class DataX(
    val accounts: List<Account>,
    val cards: List<Any>,
    val categories: List<Category>,
    val commissionSettings: List<Any>,
    val commissioners: List<Any>,
    val costCenters: List<CostCenter>,
    val damageCategories: List<DamageCategory>,
    val damageTypes: List<DamageType>,
    val establishmentSettings: EstablishmentSettings,
    val paymentMethods: List<PaymentMethod>,
    val prices: List<Price>,
    val services: List<Any>,
    val vehicleParts: List<VehiclePart>
)