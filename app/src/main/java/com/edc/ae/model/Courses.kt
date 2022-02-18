package com.edc.ae.model

import com.google.gson.annotations.SerializedName

data class Courses(
    @SerializedName("vcFeeDesc") var vcFeeDesc : String,
    @SerializedName("decAmount") var decAmount : String,

)
