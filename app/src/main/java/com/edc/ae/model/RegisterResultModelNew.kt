package com.edc.ae.model

data class RegisterResultModelNew(
    val `data`: Data?,
    val exception: Exception?,
    val message: String?,
    val srs_exception: List<Any?>?,
    val status: Int?,
    val success: Boolean?,
    val validation: String?
) {
    data class Data(
        val message: String?,
        val registrationNo: Int?,
        val status: String?,
        val studentNo: Int?
    )

    class Exception
}