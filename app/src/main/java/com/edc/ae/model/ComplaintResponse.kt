package com.edc.ae.model


import com.google.gson.annotations.SerializedName

data class ComplaintResponse(
    @SerializedName("data")
    var `data`: ComplaintData?,
    @SerializedName("exception")
    var exception: Exception?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("success")
    var success: Boolean?
) {
    data class ComplaintData(
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("created_by")
        var createdBy: String?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("student_id")
        var studentId: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("updated_by")
        var updatedBy: String?
    )

}