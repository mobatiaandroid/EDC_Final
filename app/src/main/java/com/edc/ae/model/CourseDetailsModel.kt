package com.edc.ae.model

data class CourseDetailsModel(
    val `data`: List<Data>,
    val exception: Exception,
    val message: String,
    val srs_exception: List<Any>,
    val status: Int,
    val success: Boolean,
    val validation: String
) {
    data class Data(
        val courseCost: Any,
        val text: String,
        val value: String
    )

    class Exception
}