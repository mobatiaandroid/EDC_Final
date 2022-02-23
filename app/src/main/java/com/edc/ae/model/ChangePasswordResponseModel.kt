package com.edc.ae.model

class ChangePasswordResponseModel (

    val exception: LoginResponseModel.Exception,
    val message: String,
    val status: Int,
    val success: Boolean
)