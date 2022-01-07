package com.edc.ad.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        submitBtn.setBackgroundResource(R.drawable.curved_rectangle_grey);
        editname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  TODO("Not yet implemented")

                if (p0?.length!! > 0) {
                    if (editEmail.text?.trim()?.length!! > 0 && editPassword.text?.trim()?.length!! > 0 && editConfirmPassword.text?.trim()?.length!! > 0) {
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

        editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    if (editname.text?.trim()?.length!! > 0 && editPassword.text?.trim()?.length!! > 0 && editConfirmPassword.text?.trim()?.length!! > 0) {
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
        editPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    if (editEmail.text?.trim()?.length!! > 0 && editname.text?.trim()?.length!! > 0 && editConfirmPassword.text?.trim()?.length!! > 0) {
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

        editConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    if (editEmail.text?.trim()?.length!! > 0 && editPassword.text?.trim()?.length!! > 0 && editname.text?.trim()?.length!! > 0) {
                        submitBtn.isEnabled = true
                        submitBtn.isClickable = true
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle);
                    } else {
                        submitBtn.isEnabled = false
                        submitBtn.isClickable = false
                        submitBtn.setBackgroundResource(R.drawable.curved_rectangle_grey);
                    }

                    if (editPassword.length() == p0.length) {
                        if (editPassword.text?.trim()!!.equals(p0)) {

                        } else {
                            CommonMethods.showLoginErrorPopUp(
                                this@SignupActivity,
                                "Alert",
                                "Password Mismatch"
                            )
                            editConfirmPassword.text!!.clear()
                        }
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

                    CommonMethods.showLoginErrorPopUp(this, "Alert", "Please fill all the fields.")
                } else if (editConfirmPassword.text.toString().trim().equals("")) {

                    CommonMethods.showLoginErrorPopUp(this, "Alert", "Please fill all the fields.")
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

        backButton.setOnClickListener {
            finish()
        }
    }


    private fun callAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = this?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

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

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()


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
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}