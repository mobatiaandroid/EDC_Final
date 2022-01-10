package com.edc.ad.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.BaseActivities.HomeBaseUserActivity
import com.edc.ad.R
import com.edc.ad.activity.HomeBaseGuestActivity
import com.edc.ad.activity.NotificationDetailActivity
import com.edc.ad.activity.SurveyDetailActivity
import com.edc.ad.adapter.NotificationAdapter
import com.edc.ad.api.RetrofitClient
import com.edc.ad.model.NotificationResponse
import com.edc.ad.util.OnItemClickListener
import com.edc.ad.util.PreferenceManager
import com.edc.ad.util.addOnItemClickListener
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

        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(activity, NotificationDetailActivity::class.java)
                intent.putExtra("description", notifyArray.get(position).message);
                intent.putExtra("title", notifyArray.get(position).title);
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