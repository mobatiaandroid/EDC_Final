package com.edc.ae.util

import android.app.Application
import com.edc.ae.model.EnrollDetailsModel

class AppController: Application() {
    companion object{
        var instance: AppController? = null
        var educationLevelList: ArrayList<EnrollDetailsModel.Data.EducationLevel> = ArrayList()
        var nationalityList: ArrayList<EnrollDetailsModel.Data.Nationality> = ArrayList()
        var motherTongueList: ArrayList<EnrollDetailsModel.Data.MotherTongue> = ArrayList()
        var trainingLanguageList: ArrayList<EnrollDetailsModel.Data.TrainingLanguage> = ArrayList()
        fun applicationContext() : AppController {
            return instance as AppController
        }
    }
    init {
        instance = this
    }
}