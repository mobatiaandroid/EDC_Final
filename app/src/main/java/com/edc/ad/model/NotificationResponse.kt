package com.edc.ad.model


import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("data")
    var `data`: List<NotifiData>?,
    @SerializedName("exception")
    var exception: Exception?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("success")
    var success: Boolean?
) {
    data class NotifiData(
        @SerializedName("message")
        var message: String?,
        @SerializedName("status")
        var status: Int?,
        @SerializedName("title")
        var title: String?
    )

    class Exception
}