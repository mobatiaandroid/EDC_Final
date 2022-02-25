package com.edc.ae.model

data class CourseCostModel(
    val `data`: List<Data>,
    val exception: Exception,
    val message: String,
    val srs_exception: List<Any>,
    val status: Int,
    val success: Boolean,
    val validation: String
) {
    data class Data(
        val elementCost: Double,
        val elementDescription: String,
        val elementId: String,
        val orderId: Int
    )

    class Exception
}