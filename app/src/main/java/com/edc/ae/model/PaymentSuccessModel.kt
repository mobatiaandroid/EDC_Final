package com.edc.ae.model

data class PaymentSuccessModel(
    val `data`: Data?,
    val exception: Exception?,
    val message: String?,
    val srs_exception: List<Any?>?,
    val status: Int?,
    val success: Boolean?,
    val validation: String?
) {
    data class Data(
        val amount: Int?,
        val cource_code: String?,
        val cource_name: String?,
        val device_meta: String?,
        val id: Int?,
        val man_rec_no: String?,
        val merchant_order_reference: String?,
        val order_id: Int?,
        val order_reference: String?,
        val paytab_payment_response: String?,
        val paytab_query_transaction_response: Any?,
        val recieptNo: String?,
        val remarks: String?,
        val status: Int?,
        val student_id: Int?
    )

    class Exception
}