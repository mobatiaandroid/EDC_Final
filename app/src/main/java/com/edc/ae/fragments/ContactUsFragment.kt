package com.edc.ae.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.edc.ae.BaseActivities.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.adapter.ContactUsAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.ContactUsResponse
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.coroutines.launch

class ContactUsFragment : Fragment() {
    val contactUsArray = arrayListOf<ContactUsResponse.ContactData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
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

        contactRec.adapter = ContactUsAdapter(contactUsArray) {
            findNavController().navigate(
                R.id.contactBottomSheet,
                bundleOf("data" to it)
            )
        }
        callAPI()

    }

    private fun callAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getContactUs()

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()

                        contactUsArray.addAll(call.data)
                        contactRec.adapter?.notifyDataSetChanged()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}