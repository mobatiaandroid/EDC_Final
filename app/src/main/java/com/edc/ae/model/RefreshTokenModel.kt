package com.edc.ae.model

data class RefreshTokenModel(
    val `data`: Data,
    val exception: Exception,
    val message: String,
    val srs_exception: List<Any>,
    val status: Int,
    val success: Boolean,
    val validation: String
) {
    data class Data(
        val credentials: Credentials
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