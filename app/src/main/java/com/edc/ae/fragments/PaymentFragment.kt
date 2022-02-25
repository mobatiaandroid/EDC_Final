package com.edc.ae.fragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.edc.ae.activity.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.adapter.CourseAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.Courses
import com.edc.ae.util.PreferenceManager
import com.edc.ae.util.ProgressBarDialog
import com.payment.paymentsdk.PaymentSdkActivity
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_payment.recycler
import kotlinx.android.synthetic.main.fragment_news_letter.navBtn
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.coroutines.launch

class PaymentFragment : Fragment() , CallbackPaymentInterface {

    lateinit var  instructionTxt:TextView
    var instruction:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        instructionTxt=view.findViewById(R.id.instructionTxt)
        val profileId = "89872"
        val serverKey = "SMJN26ZRWB-JDBRTBJZBG-J9TGTNL99W"
        val clientKey = "CKKMQ7-G9266D-2R7269-669P72"
        val locale = PaymentSdkLanguageCode.EN
        val screenTitle = "Test SDK"
        val cartId = "123456"
        val cartDesc = "cart description"
        val currency = "AED"
        val amount = 20.0

        val tokeniseType = PaymentSdkTokenise.NONE
        val transType = PaymentSdkTransactionType.SALE;
        val tokenFormat =  PaymentSdkTokenFormat.Hex32Format()
        val billingData = PaymentSdkBillingDetails(
            "City",
            "AE",
            "email1@domain.com",
            "name",
            "phone", "AbhuDhabi",
            "Abhu Dhabi", "" // set values from preference
        )
        val configData = PaymentSdkConfigBuilder(profileId, serverKey, clientKey, amount ?: 0.0, currency)
            .setCartDescription(cartDesc)
            .setLanguageCode(locale)
            .setBillingData(billingData)
            .setMerchantCountryCode("AE") // ISO alpha 2
//            .setShippingData(shippingData)
            .setCartId(cartId)
            .setTransactionType(transType)
            .showBillingInfo(true)
            .showShippingInfo(false)
            .forceShippingInfo(false)
            .setScreenTitle(screenTitle)
            .build()

        navBtn.setOnClickListener { _ ->
            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }
        paymentButton.setOnClickListener {
//            PaymentSdkActivity.startCardPayment(requireActivity(), configData, callback = requireActivity())
            PaymentSdkActivity.startCardPayment(context as Activity,configData, callback = this)
        }
        val orderList: ArrayList<Courses> = ArrayList()
        val temp1: Courses = Courses("Course 1","200")
        val temp2: Courses = Courses("Course 2","200")
        val temp3: Courses = Courses("Course 3","200")
        val temp4: Courses = Courses("Course 4","200")
        val temp5: Courses = Courses("Course 5","200")
        orderList.add(temp1)
        orderList.add(temp2)
        orderList.add(temp3)
        orderList.add(temp4)
        orderList.add(temp5)
        recycler.adapter = CourseAdapter(requireActivity(),orderList,"5")
        recycler.layoutManager = LinearLayoutManager(context)

        callAPI()
    }

    private fun callAPI() {
//        var progressBarDialog: ProgressBarDialog? = null
//        progressBarDialog = activity?.let { ProgressBarDialog(it) }
//        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
//                val call = RetrofitClient.get.getPaymentInstruction()
//
//                when (call.status) {
//                    200 -> {
//                        progressBarDialog?.dismiss()
//                        instruction=call.data.paymentinstruction
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            instructionTxt.text = Html.fromHtml(instruction, Html.FROM_HTML_MODE_COMPACT)
//                        } else {
//                            instructionTxt.text = Html.fromHtml(instruction)
//                        }
//
//
//                    }
//                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onError(error: PaymentSdkError) {
        Toast.makeText(context, error.msg, Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentCancel() {
        Toast.makeText(context, "Payment Cancelled", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        Toast.makeText(context, "Payment Cancelled", Toast.LENGTH_SHORT).show()

    }
}