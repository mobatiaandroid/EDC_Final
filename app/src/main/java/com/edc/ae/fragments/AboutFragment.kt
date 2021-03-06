package com.edc.ae.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.edc.ae.activity.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.api.RetrofitClient
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import kotlinx.android.synthetic.main.fragment_about_new.*
import kotlinx.coroutines.launch

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_new, container, false)
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


        callAboutAPI()

    }


    private fun callAboutAPI() {

        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getAboutUs()

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()
                        var aboutdata = call.data.about
                        var dataLoad = "<html><body style = 'padding: 0.5em; font-family: nunito'>" + aboutdata + "</body></html>"
                        dataLoad.let {
                            webAbout.loadData(it, "text/html", "utf-8")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()


            }
        }
    }
}

