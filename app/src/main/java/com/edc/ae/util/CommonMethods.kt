package com.edc.ae.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.TextView

import com.edc.ae.R
import com.edc.ae.activity.HomeBaseUserActivity
import com.edc.ae.api.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*

class CommonMethods {
    companion object{
        suspend fun callTokenRefreshAPI(context: Activity){
            val paramObject = JsonObject().apply {
                addProperty("refresh_token", PreferenceManager.getRefreshToken(context)) }
            val call = RetrofitClient.get.getRefreshToken(paramObject)

            when (call.status) {
                200 -> {
                    var access_token = call.data.credentials.access_token
                    var refresh_token = call.data.credentials.refresh_token
                    PreferenceManager.saveAccessToken(context , access_token)
                    PreferenceManager.saveRefreshToken(context, refresh_token)
                }
            }
        }
        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun showLoginErrorPopUp(context:Context,head: String, message: String) {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.dialog_alert)
            val text = dialog.findViewById<View>(R.id.textDialog) as TextView
            text.text = message
            dialog.show()
        }

        fun isInternetAvailable(context: Context): Boolean
        {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }
        fun isValidEmail(string: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(string).matches()
        }

         var clickedPos:Int=-1
    }


}