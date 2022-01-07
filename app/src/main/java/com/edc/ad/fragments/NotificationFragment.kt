package com.edc.ad.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.edc.ad.BaseActivities.HomeBaseUserActivity
import com.edc.ad.R
import com.edc.ad.activity.HomeBaseGuestActivity
import com.edc.ad.adapter.NotificationAdapter
import com.edc.ad.api.RetrofitClient
import com.edc.ad.model.NotificationResponse
import com.edc.ad.util.PreferenceManager
import kotlinx.android.synthetic.main.fragment_notification_new.*
import kotlinx.coroutines.launch

class NotificationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val notifyArray = arrayListOf<NotificationResponse.NotifiData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotifications()

        recyclerView.adapter = NotificationAdapter(notifyArray)

        navBtn.setOnClickListener { _ ->

            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }


    }

    private fun getNotifications() {
        lifecycleScope.launch {
            try {

                val call = RetrofitClient.get.getNotifications()

                when(call.status){
                    200,201 -> {
                        notifyArray.addAll(call.data!!)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    else -> {

                    }
                }

            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}