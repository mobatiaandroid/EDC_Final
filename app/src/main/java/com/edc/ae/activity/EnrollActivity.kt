package com.edc.ae.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.edc.ae.R
import com.edc.ae.api.RetrofitClient
import com.edc.ae.util.AppController
import com.edc.ae.util.ProgressBarDialog
import kotlinx.android.synthetic.main.activity_enroll.*
import kotlinx.coroutines.launch

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
        textRegisterButton.text = getString(R.string.submit)
        
        constraintExistingStudent.setOnClickListener {
            if (currentTab != CurrentTab.EXISTING){
                clearAllFields()
                showFieldsPartial()
                if (editStudentNo.hasFocus() || editTrafficNo.hasFocus() || editTryFileNo.hasFocus() || editMobileNo.hasFocus()) currentFocus!!.clearFocus()
                constraintExistingStudent.setBackgroundResource(R.drawable.curved_rectangle)
                constraintNewStudent.setBackgroundResource(0)
                textNew.setTextColor(ContextCompat.getColor(context, R.color.black))
                textExisting.setTextColor(ContextCompat.getColor(context, R.color.white))
                constraintStudentNo.visibility = View.VISIBLE
                currentTab = CurrentTab.EXISTING
                if (buttonMode == ButtonMode.REGISTER){
                   changeButtonMode()
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
                    changeButtonMode()
                }
            }
        }
        textRegisterButton.setOnClickListener {
            //set button mode to submit on validation success
            if (buttonMode == ButtonMode.SUBMIT){
                if (currentTab == CurrentTab.NEW) {
                    if (editTrafficNo.text.trim().isNotEmpty() && editTryFileNo.text.isNotEmpty()) {
                        changeButtonMode()
                        // call validation api
                        // on success show all fields and valid check
//                        arrowStudentNo.visibility = View.VISIBLE
                        arrowTryFileNo.visibility = View.VISIBLE
                        arrowTrafficNo.visibility = View.VISIBLE
                        showFieldsAll()
                        callEnrollAPI()

                    } else {
                        // field empty show error
                    }
                } else {
                    //current tab existing student
                    if (editTrafficNo.text.trim().isNotEmpty() && editTryFileNo.text.isNotEmpty() && editStudentNo.text.isNotEmpty()
                    ) {
                        changeButtonMode()
                        // call validation api
                        // on success show all fields and valid check
                        arrowStudentNo.visibility = View.VISIBLE
                        arrowTryFileNo.visibility = View.VISIBLE
                        arrowTrafficNo.visibility = View.VISIBLE
                        showFieldsAll()
                        callEnrollAPI()

                    } else {
                        // field empty show error
                    }
                }
            } else {
                val intent: Intent = Intent(context, PaymentActivity::class.java)
                startActivity(intent)
            }


//            overridePendingTransition(0, 0)
        }
//        Toast.makeText(this, buttonMode.toString(), Toast.LENGTH_SHORT).show()
        editTrafficNo.doOnTextChanged { text, start, before, count ->
            if (buttonMode == ButtonMode.REGISTER) {
                arrowTrafficNo.setImageResource(R.drawable.invalid_close)
                arrowStudentNo.setImageResource(R.drawable.invalid_close)
                arrowTryFileNo.setImageResource(R.drawable.invalid_close)

                changeButtonMode()
            }
        }
        editStudentNo.doOnTextChanged { text, start, before, count ->
            if (buttonMode == ButtonMode.REGISTER) {
                arrowTrafficNo.setImageResource(R.drawable.invalid_close)
                arrowStudentNo.setImageResource(R.drawable.invalid_close)
                arrowTryFileNo.setImageResource(R.drawable.invalid_close)
                changeButtonMode()

            }
        }
        editTryFileNo.doOnTextChanged { text, start, before, count ->
            if (buttonMode == ButtonMode.REGISTER) {
                arrowTrafficNo.setImageResource(R.drawable.invalid_close)
                arrowStudentNo.setImageResource(R.drawable.invalid_close)
                arrowTryFileNo.setImageResource(R.drawable.invalid_close)
                changeButtonMode()
            }
        }


        backBtn.setOnClickListener {
            val intent = Intent(context, HomeBaseUserActivity::class.java)
            startActivity(intent)
        }

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
    }


    private fun showFieldsPartial() {
        //Button change
//        if (buttonMode == ButtonMode.SUBMIT) changeButtonMode()
//        else changeButtonMode()
        //Tab change
        if (currentTab == CurrentTab.NEW) constraintStudentNo.visibility = View.GONE
        else constraintStudentNo.visibility = View.VISIBLE
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
        textNameEnglish.text = ""
        textNameArabic.text = ""
        textEmiratesID.text = ""
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
        AppController.educationLevelList
        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getEnrollDetailsResponse()

                when (call.status) {
                    200 -> {
                        progressBarDialog.dismiss()
                        Log.e("Enroll Response",call.data.educationLevel[1].toString())
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun changeButtonMode() {


        if (buttonMode == ButtonMode.SUBMIT){
            buttonMode = ButtonMode.REGISTER
            textRegisterButton.text = getString(R.string.register)
        } else {
            buttonMode = ButtonMode.SUBMIT
            textRegisterButton.text = getString(R.string.submit)
        }
        Toast.makeText(this, buttonMode.toString(), Toast.LENGTH_SHORT).show()
    }
}