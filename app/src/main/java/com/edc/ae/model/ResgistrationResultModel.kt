package com.edc.ae.model

data class ResgistrationResultModel(
    val `data`: List<Any>,
    val exception: Exception,
    val message: String,
    val srs_exception: List<Any>,
    val status: Int,
    val success: Boolean,
    val validation: String
) {
    class Exception
}