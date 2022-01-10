package com.mobatia.edcsurvey.survey.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OptionsModel :Serializable{
    @SerializedName("value") var value: Int=0
    @SerializedName("isSelected") var select: Int=0
    @SerializedName("select_pos0") var selectedPos0: Int=0
    @SerializedName("select_pos1") var selectedPos1: Int=0
    @SerializedName("select_pos2") var selectedPos2: Int=0
    @SerializedName("select_pos3") var selectedPos3: Int=0
    @SerializedName("select_pos4") var selectedPos4: Int=0
    @SerializedName("select_pos5") var selectedPos5: Int=0
    @SerializedName("label") var label: String=""

}