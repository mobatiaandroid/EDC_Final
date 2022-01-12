package com.edc.ae.model

data class AboutUsResponseModel(
    val `data`: AboutData,
    val exception: AboutUsException,
    val message: String,
    val status: Int,
    val success: Boolean
)