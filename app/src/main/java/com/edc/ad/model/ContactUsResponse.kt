package com.edc.ad.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ContactUsResponse(
    @SerializedName("data")
    var `data`: List<ContactData>?,
    @SerializedName("exception")
    var exception: HomeException?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("success")
    var success: Boolean?
) {
    @Parcelize
    data class ContactData(
        @SerializedName("branch_addresss")
        var branchAddresss: String?,
        @SerializedName("branch_name")
        var branchName: String?,
        @SerializedName("email")
        var email: String?,
        @SerializedName("phone")
        var phone: List<String>?
    ): Parcelable
}