package com.edc.ae.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.edc.ae.R
import com.edc.ae.activity.*
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.DevRegResponseModel
import com.edc.ae.model.SocialmediaModel
import com.edc.ae.util.AuthenticationError
import com.edc.ae.util.CommonMethods
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home_user.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.util.*

class UserHomeFragment : Fragment() {
    var socialData: List<SocialmediaModel> = Collections.emptyList()
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricManager: BiometricManager
    lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mContext = requireContext()
        PreferenceManager.setEnrollStatus(mContext,"no")
        setupBiometricAuthentication()
        checkBiometricFeatureState()

        return inflater.inflate(R.layout.fragment_home_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
            txtLogin.text = "LOGIN"
        } else {
            txtLogin.text = "LOGOUT"
            if (isBiometricFeatureAvailable()) {
                biometricPrompt.authenticate(buildBiometricPrompt())
            }

        }
//        if (activity?. let { PreferenceManager.getStudentStatus(it) } == "2") {
//            constraintEnroll.visibility = View.VISIBLE
//
//        } else if(activity?. let { PreferenceManager.getStudentStatus(it) } == "3") {
//            constraintNewsLetter.visibility = View.GONE
//            constraintPayment.visibility = View.VISIBLE
//            constraintEnroll.visibility = View.GONE
//        } else if(activity?. let { PreferenceManager.getStudentStatus(it) } == "4") {
//            constraintNewsLetter.visibility = View.VISIBLE
//            constraintPayment.visibility = View.GONE
//            constraintEnroll.visibility = View.GONE
//        } else {
//            constraintEnroll.visibility = View.GONE
//
//        }

//        if (activity?.let { PreferenceManager.getEnrollStatus(it) } == "no") {
//            constraintEnroll.visibility = View.VISIBLE
//        } else {
//            constraintEnroll.visibility = View.GONE
//
//        }
        navBtn.setOnClickListener { _ ->
            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }
        callDeviceRegistrationAPI()
//        callAPI()// to check status

        callHomeAPI()
        constraintNewsLetter.setOnClickListener {
            findNavController().navigate(R.id.newsLetterFragment)
        }

        constraintAbout.setOnClickListener {
            findNavController().navigate(R.id.surveyFragment)

        }
        constraintNotification.setOnClickListener {
            findNavController().navigate(R.id.notificationFragment)

        }
        constraintContact.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)

        }
        constraintPayment.setOnClickListener {
            findNavController().navigate(R.id.paymentFragment)
        }
        constraintFeedback.setOnClickListener {
            val intent: Intent = Intent(activity, FeedbackActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(0, 0)
        }
        constraintComplaint.setOnClickListener {
            val intent: Intent = Intent(activity, ComplaintsActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(0, 0)
        }

        /*constraintLayout.setOnClickListener {
            val intent: Intent = Intent(activity, HomeBaseGuestActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(0, 0)
            activity?.finish()
        }*/
        txtGreeting.text = getGreetingMessage()
        txtUser.text = activity?.let { PreferenceManager.getUserName(it) }
        constraintLogin.setOnClickListener {
            callLogoutApi()


        }
        constraintEnroll.setOnClickListener {
            val intent: Intent = Intent(activity, EnrollActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(0, 0)
        }
    }

//    private fun callAPI() {
//
//        lifecycleScope.launch {
//            try {
//
//                val paramObject = JsonObject().apply {
//                    addProperty("email", PreferenceManager.getEmail(context as Activity).toString())
//                    addProperty("password",PreferenceManager.getEmail(context as Activity).toString()) }
//                //  paramObject.put("email", edtEmail.text.toString())
//                //    paramObject.put("password", edtPassword.text.toString())
//                val call = RetrofitClient.get.userLogin(paramObject)
//
//                when (call.status) {
//                    200 -> {
//                        PreferenceManager.setStudentStatus(context as Activity, call.data.student_status.toString())
//                    }
//                }
//
//            } catch (httpException: HttpException) {
//
//                val responseErrorBody = httpException.response()!!.errorBody()
//                val response = responseErrorBody!!.string()
//                val obj = JSONObject(response)
//                var status_code=obj.getString("status")
//                var message = obj.getString("message")
//                CommonMethods.showLoginErrorPopUp(
//                    context as Activity,
//                    "Alert",
//                    message
//                )
//
//            }
//        }
//    }

    private fun callDeviceRegistrationAPI() {
        var devRegResponse: DevRegResponseModel
        var androidId = Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
        var deviceToken = PreferenceManager.getFCMToken(context as Activity)

        Log.e("gotData", androidId + deviceToken)
        val rawData = JsonObject().apply {
            addProperty("device_type", "2")
            addProperty("device_id", deviceToken)
            addProperty("device_identifier", androidId)
        }

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getDevRegResponse(
                    "Bearer " + PreferenceManager.getAccessToken(
                        context as Activity
                    ), rawData
                )
                Log.e("Response", call.toString())
                when (call.status) {
                    201 -> {
                        // progressBarDialog?.dismiss()
                        devRegResponse = call
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()


            }
        }
    }

    private fun callLogoutApi() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = ProgressBarDialog(context as Activity)
        progressBarDialog.show()
        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getLogout(
                    "Bearer " + PreferenceManager.getAccessToken(
                        context as Activity))
                Log.e("Response", call.toString())
                when (call.status) {
                    200 -> {
                         progressBarDialog?.dismiss()
                        activity?.let { it1 -> PreferenceManager.saveLoginStatusFlag(it1, "no") }
                        val intent = Intent(activity, HomeBaseGuestActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                progressBarDialog?.dismiss()

            }
        }
    }


    fun getGreetingMessage(): String {
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        return when (timeOfDay) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            in 21..23 -> "Good Night"
            else -> "Hello"
        }
    }

    private fun callHomeAPI() {

/*
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()
*/

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getHomeData()

                when (call.status) {
                    200 -> {
                        // progressBarDialog?.dismiss()
                        socialData = call.data.socialmedia

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()


            }
        }

        imgFacebook.setOnClickListener {
            val i = Intent(activity, WebViewActivity::class.java)
            i.putExtra("url", socialData[0].url)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
        }
        imgInstagram.setOnClickListener {
            val i = Intent(activity, WebViewActivity::class.java)
            i.putExtra("url", socialData[1].url)
            startActivity(i)
        }
        imgTwitter.setOnClickListener {
            val i = Intent(activity, WebViewActivity::class.java)
            i.putExtra("url", socialData[2].url)
            startActivity(i)
        }
        imgLinkedIn.setOnClickListener {
            val i = Intent(activity, WebViewActivity::class.java)
            i.putExtra("url", socialData[3].url)
            startActivity(i)
        }
    }


    override fun onResume() {
        super.onResume()
        if (activity?.let { PreferenceManager.getEnrollStatus(it) } == "no") {
            constraintEnroll.visibility = View.VISIBLE
        } else {
            constraintEnroll.visibility = View.GONE

        }
    }

    private fun setupBiometricAuthentication() {
        biometricManager = BiometricManager.from(mContext)
        val executor = ContextCompat.getMainExecutor(mContext)
        biometricPrompt = BiometricPrompt(this, executor, biometricCallback)
    }


    private fun checkBiometricFeatureState() {
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> setErrorNotice("Sorry. It seems your device has no biometric hardware")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> setErrorNotice("Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> setErrorNotice("You have not registered any biometric credentials")
            BiometricManager.BIOMETRIC_SUCCESS -> {

            }
        }
    }

    private fun buildBiometricPrompt(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock EDC App")
            .setDescription("Confirm your identity so that we can verify it's you")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(false) //Allows user to authenticate without performing an action, such as pressing a button, after their biometric credential is accepted.
            .build()
    }

    private fun isBiometricFeatureAvailable(): Boolean {
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    private val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            // navigateTo<HomeActivity>()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)

            if (errorCode != AuthenticationError.AUTHENTICATION_DIALOG_DISMISSED.errorCode && errorCode != AuthenticationError.CANCELLED.errorCode) {
                setErrorNotice(errString.toString())
            } else if (errorCode == 10) {
                activity?.finish()
            }
        }
    }

    private fun setErrorNotice(errorMessage: String) {

        Toast.makeText(context, "" + errorMessage, Toast.LENGTH_LONG).show()
    }


}