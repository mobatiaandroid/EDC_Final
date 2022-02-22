package com.edc.ae.util

import android.app.Application
import com.edc.ae.model.EnrollDetailsModel

class AppController: Application() {
    companion object{
        var instance: AppController? = null
        var educationLevelList: List<EnrollDetailsModel.Data.EducationLevel> = ArrayList()
        var nationalityList: List<EnrollDetailsModel.Data.Nationality> = ArrayList()
        var motherTongue: List<EnrollDetailsModel.Data.MotherTongue> = ArrayList()
        var trainingLanguageList: List<EnrollDetailsModel.Data.TrainingLanguage> = ArrayList()
        fun applicationContext() : AppController {
            return instance as AppController
        }
    }
    init {
        instance = this
    }
}