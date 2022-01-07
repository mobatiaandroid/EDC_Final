package com.mobatia.edcsurvey.survey.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OptionsModel :Serializable{
    @SerializedName("value") var value: Int=0
    @SerializedName("isSelected") var select: Int=0
    @SerializedName("label") var label: String=""

}