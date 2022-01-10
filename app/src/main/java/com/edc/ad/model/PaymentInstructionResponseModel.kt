package com.edc.ad.model

class PaymentInstructionResponseModel (
    val `data`: Data,
    val exception: Exception,
    val message: String,
    val validation: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val paymentinstruction: String
    )

    class Exception(
    )
}