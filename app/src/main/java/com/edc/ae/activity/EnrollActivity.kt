package com.edc.ae.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.edc.ae.adapter.SelectorListAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.util.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_enroll.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class EnrollActivity : AppCompatActivity() {
    lateinit var context: Activity
    private var currentTab = CurrentTab.NEW
    private var buttonMode = ButtonMode.SUBMIT

    enum class CurrentTab {
        NEW, EXISTING
    }
    enum class ButtonMode {
        SUBMIT, REGISTER
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enroll)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        showFieldsPartial()
        callEnrollAPI()
        textRegisterButton.text = getString(R.string.submit)
        constraintExistingStudent.setOnClickListener {
            if (currentTab != CurrentTab.EXISTING){
                clearAllFields()
                showFieldsPartial()
                if (editStudentNo.hasFocus()
                    || editTrafficNo.hasFocus()
                    || editTryFileNo.hasFocus()
                    || editMobileNo.hasFocus())
                        currentFocus!!.clearFocus()
                constraintExistingStudent.setBackgroundResource(R.drawable.curved_rectangle)
                constraintNewStudent.setBackgroundResource(0)
                textNew.setTextColor(ContextCompat.getColor(context, R.color.black))
                textExisting.setTextColor(ContextCompat.getColor(context, R.color.white))
                constraintStudentNo.visibility = View.VISIBLE
                currentTab = CurrentTab.EXISTING
                if (buttonMode == ButtonMode.REGISTER){
                    changeRegisterToSubmit()
                }
            }
        }
        constraintNewStudent.setOnClickListener {
            if (currentTab != CurrentTab.NEW){
                clearAllFields()
                showFieldsPartial()
                if (editStudentNo.hasFocus() || editTrafficNo.hasFocus() || editTryFileNo.hasFocus() || editMobileNo.hasFocus()) currentFocus!!.clearFocus()
                constraintNewStudent.setBackgroundResource(R.drawable.curved_rectangle)
                constraintExistingStudent.setBackgroundResource(0)
                textNew.setTextColor(ContextCompat.getColor(context, R.color.white))
                textExisting.setTextColor(ContextCompat.getColor(context, R.color.black))
                constraintStudentNo.visibility = View.GONE
                currentTab = CurrentTab.NEW
                if (buttonMode == ButtonMode.REGISTER){
                    changeRegisterToSubmit()
                }
            }
        }
        textRegisterButton.setOnClickListener {
            if (buttonMode == ButtonMode.SUBMIT){
                if (currentTab == CurrentTab.NEW) {
                    if (editTrafficNo.text.isEmpty()) {
                        Log.e("trafficno",editTrafficNo.text.toString())
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else if(editTryFileNo.text.isEmpty()) {
                        Log.e("tryfile",editTryFileNo.text.toString())
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        callValidateAPI()
                    }
                } else if (currentTab == CurrentTab.EXISTING) {
                    if (editTrafficNo.text.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else if(editTryFileNo.text.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else if(editStudentNo.text.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        callValidateAPI()
                    }
                } else {

                }
            } else if(buttonMode == ButtonMode.REGISTER) {
                if (currentTab == CurrentTab.NEW) {
                    //call register api
                } else if (currentTab == CurrentTab.EXISTING) {
                    //call register api
                } else {

                }
            } else {

            }
        }

    }

    private  fun callValidateAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = ProgressBarDialog(context)
        progressBarDialog.show()
        PreferenceManager.setTrafficNo(this,editTrafficNo.text.toString())
        PreferenceManager.setTryFileNo(this,editTryFileNo.text.toString())
        PreferenceManager.setStudentNo(this,editStudentNo.text.toString())
        val paramObject = JsonObject().apply {
            addProperty("try_file_number", PreferenceManager.getTryFileNo(context))
            addProperty("traffic_number", PreferenceManager.getTrafficNo(context))
            //  paramObject.put("email", edtEmail.text.toString())
            //    paramObject.put("password", edtPassword.text.toString())
        }
        lifecycleScope.launch {
            try {
                Log.e("token",PreferenceManager.getAccessToken(context).toString())
                val call = RetrofitClient.get.getValidationResult(
                    "Bearer " + PreferenceManager.getAccessToken(context), paramObject
                )
                Log.e("Response",call.toString())
                when (call.status) {
                    200 -> {
                        progressBarDialog.dismiss()
                        showFieldsAll()
                        editTryFileNo.setText(PreferenceManager.getTryFileNo(context))
                        editTrafficNo.setText(PreferenceManager.getTrafficNo(context))

                        textNameEnglish.text = call.data.fullName
                        textNameArabic.text = call.data.fullNameArabic
//                        textEmiratesID.text = call.data.emiratesID
                        textEmiratesID.setText(call.data.emiratesID)
                        textBranch.text = call.data.branchName
//                        textTrainingLanguage.text = call.data.trainingLanguage.toString()
                        textNationality.text = call.data.nationality
//                        textMotherTongue.text = call.data.motherTongue.toString()
//                        textEducation.text = call.data.educationLevel.toString()
                        textDOB.text = call.data.birthDate.toString()
                        textGender.text = call.data.gender.toString()
                        arrowTryFileNo.setImageResource(R.drawable.valid_check)
                        arrowTrafficNo.setImageResource(R.drawable.valid_check)
                        arrowTryFileNo.visibility = View.VISIBLE
                        arrowTrafficNo.visibility = View.VISIBLE
                        editTrafficNo.isFocusable = false
                        editTryFileNo.isFocusable = false

                        changeSubmitToRegister()
                        //set values to Preference
                        textRegisterButton.setOnClickListener {
                            if (editMobileNo.text.isEmpty()  || editMobileNo.length() != 10) {
                                Toast.makeText(context, "Please provide a valid Mobile number", Toast.LENGTH_SHORT).show()

                            } else if(textNameEnglish.text == "" ||
                                textNameArabic.text == "" ||
                            textEmiratesID.text.isEmpty() ||
                            textTrainingLanguage.text == "" ||
                            textNationality.text == "" ||
                            textMotherTongue.text == "" ||
                            textEducation.text == "" ||
                            textDOB.text == "" ||
                            textGender.text == ""){
                                Toast.makeText(context, "Fields Cannot be left empty", Toast.LENGTH_SHORT).show()
                            } else if(textEmiratesID.length() == 15){
                                Toast.makeText(context, "Emirates ID must be 15 characters", Toast.LENGTH_SHORT).show()
                            }else {
//                                callRegisterAPI()
                            }
                        }
                        editTryFileNo.setOnClickListener { showEditBottomSheet() }
                        editTrafficNo.setOnClickListener { showEditBottomSheet() }
                        editStudentNo.setOnClickListener { showEditBottomSheet() }


                    }
                    400 -> {
                        arrowTrafficNo.setImageResource(R.drawable.invalid_close)
                        arrowTryFileNo.setImageResource(R.drawable.invalid_close)
                        Toast.makeText(context, "Validation Error", Toast.LENGTH_SHORT).show()
                    }

                }
            }catch (httpException: HttpException) {
                progressBarDialog.dismiss()

                val responseErrorBody = httpException.response()!!.errorBody()
                val response = responseErrorBody!!.string()
                val obj = JSONObject(response)
                var status_code=obj.getString("status")
                var message = obj.getString("message")
                CommonMethods.showLoginErrorPopUp(
                    context,
                    "Alert",
                    message
                )

            }
        }

            //api call
            //validation success

            //validation failure

    }

    private fun callRegisterAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = ProgressBarDialog(context)
        progressBarDialog.show()

        val paramObject = JsonObject().apply {
//            addProperty("try_file_number", PreferenceManager.getTryFileNo(context))
//            addProperty("traffic_number", PreferenceManager.getTrafficNo(context))
            //  paramObject.put("email", edtEmail.text.toString())
            //    paramObject.put("password", edtPassword.text.toString())
//            addProperty("tra")
//            "traffic_number": "12345678",
//            "try_file_number": "12345678",
//            "name_english": "Test",
//            "name_arabic": "ادريان توليدو كورتيز",
//            "emirates_id": "784198074147913",
//            "branch": "1",
//            "training_language": "EN",
//            "nationality": "66",
//            "mother_tongue": "12",
//            "education_level": "4",
//            "date_of_birth": "2016-02-22",
//            "gender": "M",
//            "mobile_number": "8157864536",
//            "registration_type": "1"
        }
//        lifecycleScope.launch {
//            try {
//
//                val call = RetrofitClient.get.getRegisterResult(
//                    "Bearer " + PreferenceManager.getAccessToken(context), paramObject
//                )
//                Log.e("Response",call.toString())
//                when (call.status) {
//                    200 -> {
//                        progressBarDialog.dismiss()
//
//
//                    }
//
//                }
//            }catch (httpException: HttpException) {
//                progressBarDialog.dismiss()
//
//                val responseErrorBody = httpException.response()!!.errorBody()
//                val response = responseErrorBody!!.string()
//                val obj = JSONObject(response)
//                var status_code=obj.getString("status")
//                var message = obj.getString("message")
//                CommonMethods.showLoginErrorPopUp(
//                    context,
//                    "Alert",
//                    message
//                )
//
//            }
//        }
    }

    private fun showEditBottomSheet() {
        val dialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_edit, null)
        val submitButton = view.findViewById<Button>(R.id.buttonSubmit)
        val trafficNo = view.findViewById<EditText>(R.id.trafficNo)
        val tryFileNo = view.findViewById<EditText>(R.id.tryFileNo)
        submitButton.setOnClickListener {
            if (trafficNo.text.isEmpty()) {
                Toast.makeText(this, "Cannot be left Empty", Toast.LENGTH_SHORT).show()
            } else if (tryFileNo.text.isEmpty()) {
                Toast.makeText(this, "Cannot be left Empty", Toast.LENGTH_SHORT).show()
            } else {
                PreferenceManager.setTrafficNo(this,trafficNo.text.trim().toString())
                PreferenceManager.setTryFileNo(this,tryFileNo.text.trim().toString())
                callValidateAPI()
                dialog.dismiss()
            }
        }


        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showFieldsAll() {
        constrainNameEnglish.visibility = View.VISIBLE
        constraintNameArabic.visibility = View.VISIBLE
        constraintEmiratesID.visibility = View.VISIBLE
        constraintBranch.visibility = View.VISIBLE
        constraintTrainingLanguage.visibility = View.VISIBLE
        constraintNationality.visibility = View.VISIBLE
        constraintEducation.visibility = View.VISIBLE
        constraintMotherTongue.visibility = View.VISIBLE
        constraintDOB.visibility = View.VISIBLE
        constraintGender.visibility = View.VISIBLE
        constraintMobileNo.visibility = View.VISIBLE
        constraintTrainingLanguage.setOnClickListener {
            showSelectorTrainingLanguage()
        }
        constraintMotherTongue.setOnClickListener {
            showSelectorMotherTongue()

        }
        constraintNationality.setOnClickListener {
            showSelectorNationality()

        }
        constraintEducation.setOnClickListener {
            showSelectorEducation()

        }
        
    }

    private fun showSelectorEducation() {
        val dialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_selct, null)
        val title = view.findViewById<TextView>(R.id.selectTitle)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        title.text = "Select Education Level"
        var selectorList :ArrayList<String> =  ArrayList()
        for (each in AppController.educationLevelList) {
            selectorList.add(each.name)
        }
        var selectAdapter = SelectorListAdapter(selectorList,context)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recycler.adapter = selectAdapter
        recycler.addOnItemClickListener(object : OnItemClickListener{
            override fun onItemClicked(position: Int, view: View) {
                PreferenceManager.setEducation(context, AppController.educationLevelList[position].id)
                textEducation.text = AppController.educationLevelList[position].name
                dialog.dismiss()
            }
        })
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showSelectorNationality() {
        val dialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_selct, null)
        val title = view.findViewById<TextView>(R.id.selectTitle)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        title.text = "Select Nationality"
        var selectorList :ArrayList<String> =  ArrayList()
        for (each in AppController.nationalityList) {
            selectorList.add(each.name)
        }

        var selectAdapter = SelectorListAdapter(selectorList,context)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recycler.adapter = selectAdapter
        recycler.addOnItemClickListener(object : OnItemClickListener{
            override fun onItemClicked(position: Int, view: View) {
                PreferenceManager.setNationality(context, AppController.nationalityList[position].id)
                textNationality.text = AppController.nationalityList[position].name
                dialog.dismiss()
            }
        })
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showSelectorMotherTongue() {
        val dialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_selct, null)
        val title = view.findViewById<TextView>(R.id.selectTitle)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        title.text = "Select Mother Tongue"
        var selectorList :ArrayList<String> =  ArrayList()
        for (each in AppController.motherTongueList) {
            selectorList.add(each.name)
        }
        var selectAdapter = SelectorListAdapter(selectorList,context)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recycler.adapter = selectAdapter
        recycler.addOnItemClickListener(object : OnItemClickListener{
            override fun onItemClicked(position: Int, view: View) {
                PreferenceManager.setMotherTongue(context, AppController.motherTongueList[position].id)
                textMotherTongue.text = AppController.motherTongueList[position].name
                dialog.dismiss()
            }
        })
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showSelectorTrainingLanguage() {
        val dialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_selct, null)
        val title = view.findViewById<TextView>(R.id.selectTitle)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        title.text = "Select Training Language"
        var selectorList :ArrayList<String> =  ArrayList()
        for (each in AppController.trainingLanguageList) {
            selectorList.add(each.languageName)
        }
        Log.e("List", selectorList.toString())
        var selectAdapter = SelectorListAdapter(selectorList,context)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recycler.adapter = selectAdapter
        recycler.addOnItemClickListener(object : OnItemClickListener{
            override fun onItemClicked(position: Int, view: View) {
                PreferenceManager.setTrainingLanguage(context, AppController.trainingLanguageList[position].languageCode)
                textTrainingLanguage.text = AppController.trainingLanguageList[position].languageName
                dialog.dismiss()
            }
        })
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }


    private fun showFieldsPartial() {
        //Button change
//        if (buttonMode == ButtonMode.SUBMIT) changeButtonMode()
//        else changeButtonMode()
        //Tab change
        if (currentTab == CurrentTab.NEW)
            constraintStudentNo.visibility = View.GONE
        else
            constraintStudentNo.visibility = View.VISIBLE
        //valid check
        arrowStudentNo.visibility = View.GONE
        arrowTryFileNo.visibility = View.GONE
        arrowTrafficNo.visibility = View.GONE

        constrainNameEnglish.visibility = View.GONE
        constraintNameArabic.visibility = View.GONE
        constraintEmiratesID.visibility = View.GONE
        constraintBranch.visibility = View.GONE
        constraintTrainingLanguage.visibility = View.GONE
        constraintNationality.visibility = View.GONE
        constraintEducation.visibility = View.GONE
        constraintMotherTongue.visibility = View.GONE
        constraintDOB.visibility = View.GONE
        constraintGender.visibility = View.GONE
        constraintMobileNo.visibility = View.GONE
    }

    private fun clearAllFields() {
        editStudentNo.text.clear()
        editTrafficNo.text.clear()
        editTryFileNo.text.clear()
        PreferenceManager.setTryFileNo(context,"")
        PreferenceManager.setTrafficNo(context,"")
        PreferenceManager.setStudentNo(context,"")
        textNameEnglish.text = ""
        textNameArabic.text = ""
        textEmiratesID.text.clear()
        textTrainingLanguage.text = ""
        textNationality.text = ""
        textMotherTongue.text = ""
        textEducation.text = ""
        textDOB.text = ""
        textGender.text = ""
        editMobileNo.text.clear()
    }

    private fun callEnrollAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = ProgressBarDialog(context)
        progressBarDialog.show()
        AppController.educationLevelList.clear()
        AppController.nationalityList.clear()
        AppController.trainingLanguageList.clear()
        AppController.motherTongueList.clear()
        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getEnrollDetailsResponse()

                when (call.status) {
                    200 -> {
                        progressBarDialog.dismiss()
                        Log.e("Enroll Response",call.data.educationLevel[1].toString())
                        AppController.educationLevelList.addAll(call.data.educationLevel)
                        AppController.motherTongueList.addAll(call.data.motherTongue)
                        AppController.trainingLanguageList.addAll(call.data.trainingLanguage)
                        AppController.nationalityList.addAll(call.data.nationality)
//                        for ( each in AppController.nationalityList){
//                            if (each.id == 3) textNationality.text = each.name
//                        }

                        Log.e("Education Levels", AppController.educationLevelList.toString())

                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun changeSubmitToRegister(){
        buttonMode = ButtonMode.REGISTER
        textRegisterButton.text = getString(R.string.register)
    }
    private fun changeRegisterToSubmit(){
        buttonMode = ButtonMode.SUBMIT
        textRegisterButton.text = getString(R.string.submit)
    }
}