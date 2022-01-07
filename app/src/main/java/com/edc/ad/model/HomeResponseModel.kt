package com.edc.ad.model

data class HomeResponseModel(
    val `data`: HomeResponseData,
    val exception: HomeException,
    val message: String,
    val status: Int,
    val success: Boolean
)