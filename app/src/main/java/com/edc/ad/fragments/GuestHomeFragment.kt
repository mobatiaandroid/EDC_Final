package com.edc.ad.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.edc.ad.R
import com.edc.ad.activity.HomeBaseGuestActivity
import com.edc.ad.activity.LoginActivity
import com.edc.ad.api.RetrofitClient
import com.edc.ad.util.PreferenceManager
import kotlinx.android.synthetic.main.fragment_home_guest.*
import kotlinx.android.synthetic.main.fragment_home_guest.navBtn
import kotlinx.coroutines.launch
import java.util.*
import android.app.Activity
import android.provider.Settings
import android.util.Log
import com.edc.ad.activity.WebViewActivity
import com.edc.ad.model.DevRegResponseModel
import com.edc.ad.model.SocialmediaModel
import com.google.gson.JsonObject
import java.util.Collections.emptyList


class GuestHomeFragment : Fragment() {
    var socialData: List<SocialmediaModel> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_guest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
            txtLogin.text = "LOGIN"
        } else {
            txtLogin.text = "LOGOUT"

        }
        navBtn.setOnClickListener { _ ->
            (activity as HomeBaseGuestActivity).openNav()
        }

        constraintNewsLetterGuest.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newsLetterFragment2)
        }

        constraintAboutGuest.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_aboutFragment2)

        }
        constraintNewsGuest.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newsFragment2)

        }
        constraintServicesGuest.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_serviceFragment2)

        }

        constraintContactGuest.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_contactFragment2)

        }


        txtGreeting.text = getGreetingMessage()
        constraintLogin.setOnClickListener {

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        callDeviceRegistrationAPI()
        callHomeAPI()
    }

    @SuppressLint("HardwareIds")
    private fun callDeviceRegistrationAPI() {
        var devRegResponse: DevRegResponseModel
        var androidId = Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ANDROID_ID
        );
        var deviceToken = PreferenceManager.getFCMToken(context as Activity)

        Log.e("gotData",androidId+deviceToken)
        val rawData = JsonObject().apply {
            addProperty("device_type","2")
            addProperty("device_id",deviceToken)
            addProperty("device_identifier",androidId)
        }

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getDevRegResponse("Bearer "+PreferenceManager.getAccessToken(
                    context as Activity),rawData)
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
            i.putExtra("url",socialData[0].url)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
        }
        imgInstagram.setOnClickListener {
            val i = Intent(activity, WebViewActivity::class.java)
            i.putExtra("url",socialData[1].url)
            startActivity(i)
        }
        imgTwitter.setOnClickListener {
            val i = Intent(activity, WebViewActivity::class.java)
            i.putExtra("url",socialData[2].url)
            startActivity(i)
        }
    }
}