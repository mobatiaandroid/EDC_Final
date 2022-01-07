package com.edc.ad.model

data class NewsResponseModel(
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