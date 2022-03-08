package com.edc.ae.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.activity.HomeBaseUserActivity
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
    var startValue = 0
    val limit = 10
    var isLoading:Boolean=false
    var stopLoading=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotifications(startValue,limit)

        recyclerView.adapter = NotificationAdapter(notifyArray)

        navBtn.setOnClickListener { _ ->

            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == notifyArray.size - 1) {
                        //bottom of list!
                        if (!stopLoading)
                        {
                            startValue=startValue+limit
                            getNotifications(startValue,limit)
                            isLoading = true
                        }

                    }
                }
            }
        })
        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(activity, NotificationDetailActivity::class.java)
                intent.putExtra("description", notifyArray.get(position).message)
                intent.putExtra("title", notifyArray.get(position).title)
                startActivity(intent)
            }

        })


    }

    private fun getNotifications(start: Int,limit: Int) {
        lifecycleScope.launch {
            try {

                val call = RetrofitClient.get.getNotifications(start, limit)

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