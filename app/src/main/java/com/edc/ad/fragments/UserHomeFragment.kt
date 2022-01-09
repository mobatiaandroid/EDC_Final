package com.edc.ad.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.edc.ad.BaseActivities.HomeBaseUserActivity
import com.edc.ad.R
import com.edc.ad.activity.ComplaintsActivity
import com.edc.ad.activity.FeedbackActivity
import com.edc.ad.activity.HomeBaseGuestActivity
import com.edc.ad.activity.LoginActivity
import com.edc.ad.api.RetrofitClient
import com.edc.ad.util.PreferenceManager
import kotlinx.android.synthetic.main.fragment_home_user.*
import kotlinx.coroutines.launch
import java.util.*

class UserHomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
            txtLogin.text = "LOGIN"
        } else {
            txtLogin.text = "LOGOUT"

        }
        navBtn.setOnClickListener { _ ->
            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }
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
            activity?.let { it1 -> PreferenceManager.saveLoginStatusFlag(it1, "no") }
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()

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
                        var socialData = call.data.socialmedia

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()


            }
        }
    }
}