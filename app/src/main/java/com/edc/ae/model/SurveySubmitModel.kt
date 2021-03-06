package com.edc.ae.model

import com.google.gson.annotations.SerializedName
import java.util.*

class SurveySubmitModel (
        @SerializedName("student_id") val student_id: String,
        @SerializedName("survey_id") val survey_id: String,
        @SerializedName("questions") val questions:ArrayList<SurveySubmitQuestionModel>
)