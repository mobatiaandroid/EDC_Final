package com.mobatia.edcsurvey.survey.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SurveyDataModel (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("questions") val questions: ArrayList<QuestionsModel>

): Serializable