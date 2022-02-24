package com.edc.ae.model

import com.google.gson.annotations.SerializedName
import com.mobatia.edcsurvey.survey.model.SurveyDataModel

class StudentResponseModel  (
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: StudentProfileDataModel

)