package com.edc.ae.model

data class EnrollDetailsModel(
    val `data`: Data,
    val exception: Exception,
    val message: String,
    val status: Int,
    val success: Boolean,
    val validation: String
) {
    data class Data(
        val educationLevel: ArrayList<EducationLevel>,
        val motherTongue: ArrayList<MotherTongue>,
        val nationality: ArrayList<Nationality>,
        val trainingLanguage: ArrayList<TrainingLanguage>
    ) {
        data class EducationLevel(
            val id: Int,
            val name: String
        )

        data class MotherTongue(
            val id: Int,
            val name: String
        )

        data class Nationality(
            val id: Int,
            val name: String,
            val code: String
        )

        data class TrainingLanguage(
            val languageCode: String,
            val languageName: String
        )
    }

    class Exception
}