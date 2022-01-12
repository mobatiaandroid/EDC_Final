package com.edc.ae.model

data class HomeResponseData(
    val socialmedia: List<SocialmediaModel>,
    val to_mail: String
)