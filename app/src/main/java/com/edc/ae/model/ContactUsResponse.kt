package com.edc.ae.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ContactUsResponse(
    val `data`: List<ContactData>,
    val exception: Exception,
    val message: String,
    val status: Int,
    val success: Boolean,
    val validation: String
) {
    @Parcelize

    data class ContactData(
        val branch_addresss: String,
        val branch_name: String,
        val email: String,
        val latitude: String,
        val longitude: String,
        val phone: List<String>
    ): Parcelable

    class Exception
}
