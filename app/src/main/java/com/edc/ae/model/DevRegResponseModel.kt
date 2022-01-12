package com.edc.ae.model

import com.google.gson.annotations.SerializedName

data class DevRegResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("exception")
    val exception: Exception,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)

data class Data(
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("device_id")
    val device_id: String,
    @SerializedName("device_identifier")
    val device_identifier: String,
    @SerializedName("device_type")
    val device_type: String,
    @SerializedName("login_status")
    val login_status: Int,
    @SerializedName("updated_at")
    val updated_at: String
)

class Exception