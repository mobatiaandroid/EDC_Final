package com.edc.ae.model

data class PaymentInitiateModel(
    val `data`: Data?,
    val exception: Exception?,
    val message: String?,
    val srs_exception: List<Any?>?,
    val status: Int?,
    val success: Boolean?,
    val validation: String?
) {
    data class Data(
        val amount: String?,
        val cart_desc: String?,
        val client_key: Any?,
        val currency: Any?,
        val order_reference: String?,
        val profile_id: Any?,
        val server_key: Any?
    )

    class Exception
}