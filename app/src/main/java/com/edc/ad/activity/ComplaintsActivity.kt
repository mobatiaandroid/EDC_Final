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
import kotlinx.android.synthetic.main.activity_complaint.*
import kotlinx.android.synthetic.main.activity_complaint.backButton
import kotlinx.android.synthetic.main.activity_complaint.submitBtn
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.launch
import java.lang.Exception


class ComplaintsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_complaint)
        submitBtn.isEnabled = false
        submitBtn.isClickable = false
        submitBtn.setBackgroundResource(R.drawable.curved_rectangle_grey);
        editSubject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  TODO("Not yet implemented")

                if (p0?.length!! > 0) {
                    if (editData.text?.trim()?.length!! > 0) {
                        submitBtn.isEnabled = true
                        submitBtn.isClickable = true
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                    } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle_grey);
                    }
                } else {
                    submitBtn.isEnabled = false
                    submitBtn.isClickable = false
                    submitBtn.setBackgroundResource(R.drawable.curved_rectangle_grey);
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
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                    } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle_grey);
                    }
                } else {
                    submitBtn.isEnabled = false
                    submitBtn.isClickable = false
                    submitBtn.setBackgroundResource(R.drawable.curved_rectangle_grey);
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

                registerComplain()
            }

        }

        backButton.setOnClickListener{
            finish()
        }
    }


    private fun registerComplain() {
        lifecycleScope.launch {

            val rawData = JsonObject().apply {
                addProperty("title",editSubject.text.toString())
                addProperty("description",editData.text.toString())
                addProperty("student_id",PreferenceManager.getStudentID(this@ComplaintsActivity))
            }

            try {
                val call = RetrofitClient.get.createComplaint("Bearer "+PreferenceManager.getAccessToken(this@ComplaintsActivity),rawData)

                when(call.status){
                    200,201 -> {
                        Toast.makeText(this@ComplaintsActivity, "Complaint registered Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        println("Error")
                    }
                }


            } catch (e: Exception){
                e.printStackTrace()
            }

        }
    }

}