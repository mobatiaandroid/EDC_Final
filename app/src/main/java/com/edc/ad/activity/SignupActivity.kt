package com.edc.ad.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.edc.ad.R
import com.edc.ad.api.RetrofitClient
import com.edc.ad.util.CommonMethods
import com.edc.ad.util.ProgressBarDialog
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.submitBtn
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup)

        submitBtn.alpha=0.5f
        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
        editname.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    if (editEmail.text!!.trim().isNotEmpty()
                        && editname.text!!.trim().isNotEmpty()
                        && editConfirmPassword.text!!.trim().isNotEmpty()
                        && editPassword.text!!.trim().isNotEmpty()){
                        submitBtn.isEnabled = true
                        submitBtn.isClickable = true
                        submitBtn.alpha=1.0f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    } else {
                            submitBtn.isEnabled = false
                            submitBtn.isClickable = false
                        submitBtn.alpha=0.5f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    }
                } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                    submitBtn.alpha=0.5f
                    submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        editEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    if (editEmail.text!!.trim().isNotEmpty()
                        && editname.text!!.trim().isNotEmpty()
                        && editConfirmPassword.text!!.trim().isNotEmpty()
                        && editPassword.text!!.trim().isNotEmpty()
                    ) {
                        submitBtn.isEnabled = true
                        submitBtn.isClickable = true
                        submitBtn.alpha=1.0f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                        submitBtn.alpha=0.5f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    }
                } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                    submitBtn.alpha=0.5f
                    submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        editPassword.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    if (editEmail.text!!.trim().isNotEmpty()
                        && editname.text!!.trim().isNotEmpty()
                        && editConfirmPassword.text!!.trim().isNotEmpty()
                        && editPassword.text!!.trim().isNotEmpty()
                    ) {
                        submitBtn.isEnabled = true
                        submitBtn.isClickable = true
                        submitBtn.alpha=1.0f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                        submitBtn.alpha=0.5f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    }
                } else {
                    submitBtn.isEnabled = false
                    submitBtn.isClickable = false
                    submitBtn.alpha=0.5f
                    submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        editConfirmPassword.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    if (editEmail.text!!.trim().isNotEmpty()
                        && editname.text!!.trim().isNotEmpty()
                        && editConfirmPassword.text!!.trim().isNotEmpty()
                        && editPassword.text!!.trim().isNotEmpty()
                    ) {
                        submitBtn.isEnabled = true
                        submitBtn.isClickable = true
                        submitBtn.alpha=1.0f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                        submitBtn.alpha=0.5f
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                    }
//                    try{
//                        if (editPassword.length() == s.length) {
//                            if (editPassword.text?.trim()!!.equals(s)) {
//
//                            } else {
//                                CommonMethods.showLoginErrorPopUp(
//                                    this@SignupActivity,
//                                    "Alert",
//                                    "Password Mismatch"
//                                )
//                                editConfirmPassword.text!!.clear()
//                            }
//                        }
//                    }catch (e:Exception){
//                        Log.e("Error",e.toString())
//                    }
                } else {
                    submitBtn.isEnabled = false
                    submitBtn.isClickable = false
                    submitBtn.alpha=0.5f
                    submitBtn.setBackgroundResource(R.drawable.curved_rectangle)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        try{
            submitBtn.setOnClickListener {

                if (editname.text.toString().trim().equals("")) {

                    CommonMethods.showLoginErrorPopUp(this, "Alert", "Please fill all the fields.")
                } else if (editEmail.text.toString().trim().equals("")) {

                    CommonMethods.showLoginErrorPopUp(this, "Alert", "Please fill all the fields.")
                } else if (!editEmail.text.toString().trim().equals("")) {
                    var emailPattern = CommonMethods.isEmailValid(editEmail.text.toString())
                    if (!emailPattern) {
                        CommonMethods.showLoginErrorPopUp(
                            this@SignupActivity,
                            "Alert",
                            "Enter a Valid Email."
                        )
                    } else if (editPassword.text.toString().trim().equals("")) {

                        CommonMethods.showLoginErrorPopUp(
                            this,
                            "Alert",
                            "Please fill all the fields."
                        )
                    } else if (editConfirmPassword.text.toString().trim().equals("")) {

                        CommonMethods.showLoginErrorPopUp(
                            this,
                            "Alert",
                            "Please fill all the fields."
                        )
                    } else {
                        callAPI()

                    }
                } else if (editPassword.text.toString().trim().equals("")) {

                    CommonMethods.showLoginErrorPopUp(this, "Alert", "Please fill all the fields.")
                } else if (editConfirmPassword.text.toString().trim().equals("")) {

                    CommonMethods.showLoginErrorPopUp(this, "Alert", "Please fill all the fields.")
                } else {
                    callAPI()

                }

            }
        }catch (e: Exception){
            Log.e("Error",e.toString())
        }

        backButton.setOnClickListener {
            finish()
        }
    }


    private fun callAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = ProgressBarDialog(this)
        progressBarDialog.show()

        lifecycleScope.launch {
            try {

                val paramObject = JsonObject().apply {
                    addProperty("fullname", editname.text.toString());
                    addProperty("email", editEmail.text.toString());
                    addProperty("password", editPassword.text.toString());
                    addProperty("c_password", editConfirmPassword.text.toString())
                }
                //  paramObject.put("email", edtEmail.text.toString())
                //    paramObject.put("password", edtPassword.text.toString())
                val call = RetrofitClient.get.userRegister(paramObject)
                Log.e("WORKS","ENTERS")
                Log.e("response",call.status.toString())

                when (call.status) {
                    200 -> {
                        progressBarDialog.dismiss()
                        Toast.makeText(
                            this@SignupActivity,
                            "Registration Successfull",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent: Intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)


                        overridePendingTransition(0, 0)
                        finish()
                    }
                    400 -> {
                        progressBarDialog.dismiss()
                        Toast.makeText(
                            this@SignupActivity,
                            "The email has already been taken.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }

            catch (e: Exception) {
                Log.e("WORKS","ENTERS")
               if (e.toString().equals("retrofit2.HttpException: HTTP 400 Bad Request"))
               {

                   progressBarDialog.dismiss()
                   Toast.makeText(
                           this@SignupActivity,
                           "Something went wrong",
                           Toast.LENGTH_LONG
                   ).show()
               }
                e.printStackTrace()
            }
        }
    }

}