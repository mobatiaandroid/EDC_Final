package com.edc.ad.Fragments

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

class GuestHomeFragment : Fragment() {
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
            activity?.finish()
        }

        callHomeAPI()
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