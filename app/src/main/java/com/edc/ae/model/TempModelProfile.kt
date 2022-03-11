package com.edc.ae.model

data class TempModelProfile(
    val `data`: Data?,
    val exception: Exception?,
    val message: String?,
    val srs_exception: SrsException?,
    val status: Int?,
    val success: Boolean?,
    val validation: String?
) {
    data class Data(
        val birthDate: String?,
        val branch: String?,
        val branchName: String?,
        val educationLevel: String?,
        val emailAddress: String?,
        val emiratesID: String?,
        val fullName: String?,
        val fullNameArabic: String?,
        val gender: String?,
        val language: String?,
        val license_type_code: String?,
        val mobileNo: String?,
        val motherTongue: String?,
        val nationality: String?,
        val regNo: Int?,
        val studentNo: String?,
        val student_status: Int?,
        val trafficNo: String?,
        val trainingLanguage: String?,
        val training_type_code: String?,
        val tryFileNo: String?,
        val unifiedId: String?,
        val userName: String?
    )

    class Exception

    data class SrsException(
        val birthDate: String?,
        val branch: Int?,
        val branchName: Any?,
        val confirmPassword: Any?,
        val educationLevel: String?,
        val emailAddress: String?,
        val emailExists: Boolean?,
        val emiratesID: String?,
        val fullName: String?,
        val fullNameArabic: String?,
        val gender: String?,
        val iBranch: Any?,
        val iEducationLevel: Int?,
        val iMotherTongue: Int?,
        val iNationality: Int?,
        val language: Any?,
        val mobileNo: String?,
        val motherTongue: Any?,
        val nationality: String?,
        val newStudent: Any?,
        val password: Any?,
        val registrationDetails: RegistrationDetails?,
        val studentNo: Int?,
        val studentStatus: Int?,
        val trafficNo: String?,
        val trafficSuccess: Boolean?,
        val trainingLanguage: Any?,
        val tryBranchCd: Int?,
        val tryFileNo: String?,
        val userDetails: Any?
    ) {
        data class RegistrationDetails(
            val actRemarks: Any?,
            val audVidFlg: String?,
            val autoVehLicFlg: String?,
            val billToFlg: String?,
            val cityCd: Int?,
            val corpCd: String?,
            val dlissDt: String?,
            val dlno: String?,
            val dob: String?,
            val drvnCmp: Int?,
            val eduLang: String?,
            val eduLevl: Int?,
            val eduType: Int?,
            val emePhone1: String?,
            val emePhone2: String?,
            val emirate: String?,
            val fullName: String?,
            val handicapFlg: String?,
            val holdnLicFrm: Int?,
            val holdnLicType: Int?,
            val homePhone: String?,
            val illtFlg: String?,
            val invFlg: String?,
            val licTypeReq: Int?,
            val medTestFlg: String?,
            val mobileNo: String?,
            val mothTong: Int?,
            val nationCd: Int?,
            val occuCd: Int?,
            val payMethod: Int?,
            val payingFor: String?,
            val photoNm: String?,
            val pobox: String?,
            val ppno: String?,
            val practTrnPref: String?,
            val refBrnch: Int?,
            val regDate: String?,
            val regNo: Int?,
            val regcd: Int?,
            val registdBy: String?,
            val resiVisaNo: String?,
            val sexFlg: String?,
            val shdrOu: Any?,
            val shdrRegno: Any?,
            val shortName: String?,
            val sponsorNm: String?,
            val status: String?,
            val studentNo: Int?,
            val traficFileNo1: String?,
            val traficFileNo2: String?,
            val uaelicType: Int?,
            val workPhone: String?
        )
    }
}