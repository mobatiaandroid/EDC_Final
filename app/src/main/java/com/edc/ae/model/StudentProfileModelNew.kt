package com.edc.ae.model

data class StudentProfileModelNew(
    val `data`: Data?,
    val exception: Exception?,
    val message: String?,
    val srs_exception: List<Any?>?,
    val status: Int?,
    val success: Boolean?,
    val validation: String?
) {
    data class Data(
        val birthDate: String?,
        val branch: Int?,
        val branchName: String?,
        val educationLevel: Int?,
        val emailAddress: String?,
        val emiratesID: String?,
        val fullName: String?,
        val fullNameArabic: String?,
        val gender: String?,
        val language: String?,
        val licenseTypeCode: Int?,
        val mobileNo: String?,
        val motherTongue: Int?,
        val nationality: Int?,
        val regNo: Int?,
        val studentNo: Int?,
        val trafficNo: String?,
        val trainingLanguage: String?,
        val trainingTypeCode: Int?,
        val tryFileNo: String?,
        val unifiedId: String?,
        val userName: String?
    )

    class Exception
}