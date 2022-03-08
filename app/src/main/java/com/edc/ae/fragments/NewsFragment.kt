package com.edc.ae.Fragments

import android.app.Activity
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
import com.edc.ae.adapter.NewsAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.NewsResponseModel
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.navBtn
import kotlinx.android.synthetic.main.fragment_service.*
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    val newsArray = arrayListOf<NewsResponseModel.Data>()
    var startValue = 0
    val limit = 10
    var isLoading:Boolean=false
    var stopLoading=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsRec.adapter = NewsAdapter(newsArray, context as Activity)
        navBtn.setOnClickListener { _ ->
            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }
        newsRec.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager =
                    newsRec.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == newsArray.size - 1) {
                        //bottom of list!
                        if (!stopLoading)
                        {
                            startValue=startValue+limit
                            callAPI(startValue,limit)
                            isLoading = true
                        }

                    }
                }
            }
        })

//        newsRec.adapter = NewsAdapter(newsArray) {
//            //
//
//            //Intent intent=
//        }


        callAPI(startValue, limit)
    }

    private fun callAPI(startValue: Int, limit: Int) {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getNewsData(startValue, limit)

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()

                        newsArray.addAll(call.data)
                        newsRec.adapter?.notifyDataSetChanged()
                        isLoading = false

                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}