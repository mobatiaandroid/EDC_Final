package com.edc.ae.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class StudentProfileUserDetailsModel {
    @SerializedName("studentNo") var studentNo: String=""
    @SerializedName("trafficNumber") var trafficNumber: String=""
    @SerializedName("tryFileNumber") var tryFileNumber: String=""
    @SerializedName("email") var email: String=""
    @SerializedName("applicantNameEn") var applicantNameEn: String=""
    @SerializedName("gender") var gender: String=""
    @SerializedName("branchName") var branchName: String=""
    @SerializedName("branch") var branch: Int=-1


}