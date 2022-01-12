package com.edc.ae.model

data class ServiceResponseModel(
    val `data`: List<Data>,
    val exception: Exception,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val description: String,
        val `file`: String,
        val id: Int,
        val title: String
    )

    class Exception(
    )
}