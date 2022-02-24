package com.edc.ae.model

data class ValidationResultModel(
    val `data`: Data,
    val exception: Exception,
    val message: String,
    val srs_exception: List<Any>,
    val status: Int,
    val success: Boolean,
    val validation: String
) {
    data class Data(
        val birthDate: Any,
        val branch: Int,
        val branchName: String,
        val confirmPassword: Any,
        val educationLevel: Any,
        val emailAddress: Any,
        val emailExists: Boolean,
        val emiratesID: String,
        val fullName: String,
        val fullNameArabic: String,
        val gender: Any,
        val iBranch: Any,
        val iEducationLevel: Int,
        val iMotherTongue: Int,
        val iNationality: Int,
        val language: Any,
        val mobileNo: Any,
        val motherTongue: Any,
        val nationality: String,
        val newStudent: Any,
        val password: Any,
        val registrationDetails: Any,
        val studentNo: Int,
        val studentStatus: Int,
        val trafficNo: String,
        val trafficSuccess: Boolean,
        val trainingLanguage: Any,
        val tryBranchCd: Int,
        val tryFileNo: String,
        val userDetails: Any
    )

    class Exception
}