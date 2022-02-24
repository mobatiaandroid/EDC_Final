package com.edc.ae.model

import com.google.gson.annotations.SerializedName

class StudentProfileDataModel (

    @SerializedName("userDetails") val userDetails: StudentProfileUserDetailsModel

)