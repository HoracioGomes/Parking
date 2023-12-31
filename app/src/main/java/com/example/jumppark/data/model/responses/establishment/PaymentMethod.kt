package com.example.jumppark.data.model.responses.establishment

data class PaymentMethod(
    val accountId: Int,
    val establishmentPaymentMethodId: Int,
    val paymentMethodName: String,
    val primitivePaymentMethodId: Int,
    val receivingDays: Int,
    val receivingFee: String
)