package com.mobatia.edcsurvey.survey.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class QuestionsModel (
    @SerializedName("id") val id: Int,
    @SerializedName("question") val question: String,
    @SerializedName("answer_type") val answer_type: Int,
    @SerializedName("options") val options: ArrayList<OptionsModel>

):Serializable