package com.edc.ae.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.edc.ae.BaseActivities.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.api.RetrofitClient
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import kotlinx.android.synthetic.main.fragment_news_letter.navBtn
import kotlinx.coroutines.launch

class PaymentInstructionFragment : Fragment() {

    lateinit var  instructionTxt:TextView
    var instruction:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_instruction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instructionTxt=view.findViewById(R.id.instructionTxt)
        navBtn.setOnClickListener { _ ->
            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }        }

        callAPI()
    }

    private fun callAPI() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getPaymentInstruction()

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()
                        instruction=call.data.paymentinstruction

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            instructionTxt.text = Html.fromHtml(instruction, Html.FROM_HTML_MODE_COMPACT)
                        } else {
                            instructionTxt.text = Html.fromHtml(instruction)
                        }


                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}