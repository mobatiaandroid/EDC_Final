package com.mobatia.edcsurvey.survey.model

import com.google.gson.annotations.SerializedName

class SurveyResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ArrayList<SurveyDataModel>

)