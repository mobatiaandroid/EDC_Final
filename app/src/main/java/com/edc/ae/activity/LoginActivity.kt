package com.edc.ae.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.edc.ae.R
import com.edc.ae.api.RetrofitClient
import com.edc.ae.util.CommonMethods
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException


class LoginActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var buildingBgImg: ImageView
    lateinit var buildingBgImg2: ImageView
//    lateinit var carImg: ImageView
    lateinit var passwordTxt: EditText
    lateinit var emailTxt: EditText
    lateinit var submitBtn: Button
    lateinit var createAccount: TextView
    lateinit var forgotPassword: TextView
    lateinit var logo: ImageView
    lateinit var constraintMain: ConstraintLayout
    lateinit var constraintEmail: ConstraintLayout
    lateinit var constraintPassword: ConstraintLayout
    lateinit var txtEmailHint: TextView
    lateinit var txtPasswordHint: TextView

    var progressBarDialog: ProgressBarDialog? = null
    var passwordHideShown: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        initialiseUI()
    }

    @SuppressLint("ResourceAsColor")
    private fun initialiseUI() {
        createAccount = findViewById(R.id.txtCreateAccount)
        passwordTxt = findViewById(R.id.edtPassword)
        submitBtn = findViewById(R.id.submitBtn)
        emailTxt = findViewById(R.id.edtEmail)
//        buildingBgImg = findViewById(R.id.city1)
//        buildingBgImg2 = findViewById(R.id.city2)
        constraintMain = findViewById(R.id.clMain)
        constraintEmail = findViewById(R.id.clEmail)
        constraintPassword = findViewById(R.id.clPasword)
        forgotPassword = findViewById(R.id.txtForgotPassword)

//        carImg = findViewById(R.id.carImg)
    //    txtEmailHint = findViewById(R.id.emailHintTxt)
      //  txtPasswordHint = findViewById(R.id.passwordHintTxt)
        logo = findViewById(R.id.logo)

        emailTxt.setOnFocusChangeListener { view, b ->
            if (b) {
              //  txtEmailHint.visibility = View.VISIBLE
                emailTxt.hint = " "

            } else {
                //txtEmailHint.visibility = View.INVISIBLE
                emailTxt.hint = resources.getString(R.string.email_hint)

            }

        }

        passwordTxt.setOnFocusChangeListener { view, b ->
            if (b) {
               // txtPasswordHint.visibility = View.VISIBLE
                passwordTxt.hint = " "


            } else {
               // txtPasswordHint.visibility = View.INVISIBLE
                passwordTxt.hint = resources.getString(R.string.password_hint)

            }

        }

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val calculatedWidth = metrics.widthPixels

        submitBtn.setBackgroundResource(R.drawable.curved_rectangle)

        submitBtn.alpha = 0.5f
        submitBtn.isEnabled = false
        submitBtn.isClickable = false

//        val animation: Animation = TranslateAnimation(0F, calculatedWidth / 3.8f, 0F, 0F)
//        animation.duration = 0
//        animation.fillAfter = true
//        carImg.startAnimation(animation)
        progressBarDialog = ProgressBarDialog(this)
        val animUpDown: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.logo_animation
        )


        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(data: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(data: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (data?.length!! > 0) {
                    if (edtEmail.text?.trim()?.length!! > 0) {

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

            override fun afterTextChanged(p0: Editable?) {
                // TODO("Not yet implemented")
            }

        })

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // TODO("Not yet implemented")
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // TODO("Not yet implemented")

                if (p0?.length!! > 0) {

                    if (edtPassword.text?.trim()?.length!! > 0) {

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

            override fun afterTextChanged(p0: Editable?) {
                //  TODO("Not yet implemented")
            }

        })
//        logo.startAnimation(animUpDown)
        Handler().postDelayed({
            constraintMain.visibility = View.VISIBLE
        }, 1000)
        val cityAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.car_right_small)

//        passwordHideShowImg.setOnClickListener(View.OnClickListener {
//            if (passwordHideShown) {
//                passwordHideShown = false
//                passwordTxt.transformationMethod = PasswordTransformationMethod.getInstance()
//            } else {
//                passwordHideShown = true
//                passwordTxt.transformationMethod = HideReturnsTransformationMethod.getInstance();
//            }
//
//        })

        createAccount.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, SignupActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        })
        forgotPassword.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_forgot_password, null)
            val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
            val editEmail = view.findViewById<EditText>(R.id.editEmail)
            buttonSubmit.alpha = 0.5f
            buttonSubmit.isEnabled = false
            buttonSubmit.isClickable = false
            editEmail.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.isNotEmpty()){
                        val emailPattern =
                            CommonMethods.isEmailValid(s.toString().trim())
                        if(emailPattern)
                        {
                            buttonSubmit.alpha = 1.0f
                            buttonSubmit.isEnabled = true
                            buttonSubmit.isClickable = true
                        }
                        else
                        {
                            buttonSubmit.alpha = 0.5f
                            buttonSubmit.isEnabled = false
                            buttonSubmit.isClickable = false
                        }

                    }else{
                        buttonSubmit.alpha = 0.5f
                        buttonSubmit.isEnabled = false
                        buttonSubmit.isClickable = false
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
            buttonSubmit.setOnClickListener {
                if (editEmail.text?.trim()?.length!! > 0)
                {
                    val emailPattern = CommonMethods.isEmailValid(editEmail.text.toString())
                    if (!emailPattern) {
                        Toast.makeText(this, "Enter a Valid Email", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this, "A mail was sent to your email ID", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                } else{
                    Toast.makeText(this, "Cannot be left empty", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
        submitBtn.setOnClickListener(View.OnClickListener {

            if (edtEmail.text.toString().trim().equals("")) {
                val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
                //emailBgImg.startAnimation(shake)
                // emailHintTxt.startAnimation(shake)
                constraintEmail.startAnimation(shake)
                CommonMethods.showLoginErrorPopUp(context, "Alert", "Field cannot be empty.")
            } else {
                var emailPattern = CommonMethods.isEmailValid(edtEmail.text.toString())
                if (!emailPattern) {
                    CommonMethods.showLoginErrorPopUp(context, "Alert", "Enter a Valid Email.")
                } else {
                    if (edtPassword.text.toString().trim().equals("")) {
                        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
                        constraintPassword.startAnimation(shake)

                        CommonMethods.showLoginErrorPopUp(
                            context,
                            "Alert",
                            "Field cannot be empty."
                        )
                    } else {

                        if (CommonMethods.isInternetAvailable(context)) {

                            callAPI()
                            //   callLoginApi(emailTxt.text.toString(), passwordTxt.text.toString())
                        } else {
                            CommonMethods.showLoginErrorPopUp(
                                context,
                                "Alert",
                                "Network error occurred. Please check your internet connection and try again later."
                            )
                        }

                    }
                }
            }
        })


    }

    private fun callAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = this.let { ProgressBarDialog(it) }
        progressBarDialog.show()

        lifecycleScope.launch {
            try {

                val paramObject = JsonObject().apply {
                    addProperty("email", edtEmail.text.toString())
                    addProperty("password",edtPassword.text.toString()) }
              //  paramObject.put("email", edtEmail.text.toString())
            //    paramObject.put("password", edtPassword.text.toString())
                val call = RetrofitClient.get.userLogin(paramObject)

                when (call.status) {
                    200 -> {
                        progressBarDialog.dismiss()
                        PreferenceManager.saveLoginStatusFlag(this@LoginActivity, "yes")

                        var access_token = call.data.credentials.access_token
                        var refresh_token = call.data.credentials.refresh_token
                        var user = call.data.student_name
                        var student_id = call.data.student_id
                        PreferenceManager.saveAccessToken(this@LoginActivity, access_token)
                        PreferenceManager.saveRefreshToken(this@LoginActivity, refresh_token)
                        PreferenceManager.saveUserName(this@LoginActivity, user)
                        PreferenceManager.saveStudentID(this@LoginActivity, student_id)
                        val intent: Intent = Intent(this@LoginActivity, HomeBaseUserActivity::class.java)
                        startActivity(intent)


                        overridePendingTransition(0, 0)
                        finish()
                    }
                }

            } catch (httpException: HttpException) {
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
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent: Intent = Intent(this, HomeBaseGuestActivity::class.java)
        startActivity(intent)
        finish()
    }
}
