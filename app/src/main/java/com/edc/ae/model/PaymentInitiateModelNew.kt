package com.edc.ae.model

data class PaymentInitiateModelNew(
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
        val client_key: String?,
        val currency: String?,
        val order_id: String?,
        val order_reference: String?,
        val profile_id: String?,
        val server_key: String?
    )

    class Exception
}