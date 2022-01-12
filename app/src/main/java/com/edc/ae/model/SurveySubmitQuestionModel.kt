package com.edc.ae.model

import com.google.gson.annotations.SerializedName

class SurveySubmitQuestionModel (
        @SerializedName("question_id") val question_id: Int,
        @SerializedName("option_id") val option_id: String
        )