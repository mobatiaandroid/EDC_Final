package com.edc.ae.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.edc.ae.BaseActivities.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.adapter.NewsAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.NewsResponseModel
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.navBtn
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    val newsArray = arrayListOf<NewsResponseModel.Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navBtn.setOnClickListener { _ ->
            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }

//        newsRec.adapter = NewsAdapter(newsArray) {
//            //
//
//            //Intent intent=
//        }
        newsRec.adapter = NewsAdapter(newsArray, context as Activity)

        callAPI()
    }

    private fun callAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getNewsData()

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()

                        newsArray.addAll(call.data)
                        newsRec.adapter?.notifyDataSetChanged()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}