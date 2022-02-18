package com.edc.ae.model

data class RegisterResponseModel(
    val `data`: Data,
    val exception: Exception,
    val message: String,
    val validation: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val credentials: Credentials,
        val student_id: String,
        val student_name: String,
        val student_status: Int
    ) {
        data class Credentials(
            val access_token: String,
            val expires_in: Int,
            val refresh_token: String,
            val token_type: String
        )
    }

    class Exception
}