package com.edc.ad.model

data class HomeResponseData(
    val socialmedia: List<SocialmediaModel>,
    val to_mail: String
)