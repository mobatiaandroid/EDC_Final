package com.edc.ae.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.edc.ae.BaseActivities.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.activity.NotificationDetailActivity
import com.edc.ae.adapter.NotificationAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.NotificationResponse
import com.edc.ae.util.OnItemClickListener
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.addOnItemClickListener
import kotlinx.android.synthetic.main.fragment_notification_new.*
import kotlinx.coroutines.launch

class NotificationFragment : Fragment() {

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

        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(activity, NotificationDetailActivity::class.java)
                intent.putExtra("description", notifyArray.get(position).message)
                intent.putExtra("title", notifyArray.get(position).title)
                startActivity(intent)
            }

        })


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