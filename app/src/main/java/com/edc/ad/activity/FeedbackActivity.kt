package com.edc.ad.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.edc.ad.R
import com.edc.ad.api.RetrofitClient
import com.edc.ad.util.CommonMethods
import com.edc.ad.util.PreferenceManager
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.activity_feedback.backButton
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.submitBtn
import kotlinx.coroutines.launch
import java.lang.Exception

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feedback)
        submitBtn.isEnabled = false
        submitBtn.isClickable = false
        submitBtn.alpha=0.5f
        submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
        editSubject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  TODO("Not yet implemented")

                if (p0?.length!! > 0) {
                    if (editData.text?.trim()?.length!! > 0) {
                        submitBtn.isEnabled = true
                        submitBtn.isClickable = true
                        submitBtn.alpha=1.0f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                    } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                        submitBtn.alpha=0.5f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                    }
                } else {
                    submitBtn.isEnabled = false
                    submitBtn.isClickable = false
                    submitBtn.alpha=0.5f
                    submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //  TODO("Not yet implemented")
            }

        })

        editData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    if (editSubject.text?.trim()?.length!! > 0) {
                        submitBtn.isEnabled = true
                        submitBtn.isClickable = true
                        submitBtn.alpha=1.0f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                    } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                        submitBtn.alpha=0.5f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                    }
                } else {
                    submitBtn.isEnabled = false
                    submitBtn.isClickable = false
                    submitBtn.alpha=0.5f
                    submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        submitBtn.setOnClickListener {

            if (editSubject.text.toString().trim().equals("")) {

                CommonMethods.showLoginErrorPopUp(this, "Alert", "Please fill all the fields.")
            } else if (editData.text.toString().trim().equals("")) {

                CommonMethods.showLoginErrorPopUp(this, "Alert", "Please fill all the fields.")
            } else {

                feedbackAPICall()
            }

        }

        backButton.setOnClickListener{
            finish()
        }
    }


    private fun feedbackAPICall() {

        val rawData = JsonObject().apply {
            addProperty("title",editSubject.text.toString())
            addProperty("description",editData.text.toString())
            addProperty("student_id",PreferenceManager.getStudentID(this@FeedbackActivity))
        }

        lifecycleScope.launch {
            try {

                val call = RetrofitClient.get.giveFeedBack(
                    "Bearer "+ PreferenceManager.getAccessToken(this@FeedbackActivity),
                    rawData
                )

                when (call.status){
                    200,201 -> {
                        Toast.makeText(this@FeedbackActivity, "Feedback registered Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else -> {
                        println("Error")
                    }
                }

            } catch (e: Exception){
                Toast.makeText(this@FeedbackActivity, "Something went wrong", Toast.LENGTH_SHORT).show()

                e.printStackTrace()
            }
        }
    }

}