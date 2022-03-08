package com.edc.ae.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseUserActivity
import com.edc.ae.adapter.CostAdapter
import com.edc.ae.adapter.SelectorListAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.util.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.payment.paymentsdk.PaymentSdkActivity
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import kotlinx.android.synthetic.main.activity_payment.recycler
import kotlinx.android.synthetic.main.fragment_payment.paymentButton
import kotlinx.android.synthetic.main.fragment_payment.selectedCourse
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException


class PaymentFragmentNew : Fragment(), CallbackPaymentInterface {

    lateinit var selectCourse: ConstraintLayout
    lateinit var textTotal: TextView
    lateinit var textPrice: TextView
    lateinit var textPrice2: TextView
    var valueSelectCourse = ""
    var courseID = 0
    var cost = 0.0
    var temp = 0
    var orderRef = ""
    var orderID = ""
    var profileID = ""
    var serverKey = ""
    var clientKey = ""
    var currency = ""
    var amount = ""
    var initiateSuccess = false
    val locale = PaymentSdkLanguageCode.EN
    val screenTitle = "EDC Course Payment"






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectCourse = view.findViewById(R.id.constraintCourseSelect)
        textTotal = view.findViewById(R.id.textTotal)
        textPrice = view.findViewById(R.id.totalPrice)
        textPrice2 = view.findViewById(R.id.totalPrice2)

        callCourseDetailsAPI()
        selectCourse.setOnClickListener {
            showCourseSelectSheet()
        }
        paymentButton.setOnClickListener {
            if (temp == 1) {
                var progressBarDialog: ProgressBarDialog? = null
                progressBarDialog = activity?.let { ProgressBarDialog(it) }
                progressBarDialog?.show()
                val paramObject = JsonObject().apply {
                    addProperty("order_id", courseID)
                    addProperty("fee_code", valueSelectCourse)
                    addProperty("cource_name", selectedCourse.text.toString())
                    addProperty("amount", textPrice.toString())
                    addProperty("device_type", "2")
                    addProperty("device_name", getDeviceName() )
                    addProperty("app_version", "0.0")
                }
                progressBarDialog!!.hide()

                lifecycleScope.launch {
                    try {
                        val call = RetrofitClient.get.initiatePayment(
                            "Bearer " + PreferenceManager.getAccessToken(requireActivity()),
                            paramObject
                        )
                        Log.e("Response", call.toString())

                        when (call.status) {
                            200 -> {
                                progressBarDialog.dismiss()
                                Log.e("Response", call.toString())
                                orderRef = call.data!!.order_reference.toString()
                                orderID = call.data.order_id.toString()
                                profileID = call.data.profile_id.toString()
                                clientKey = call.data.client_key.toString()
                                serverKey = call.data.server_key.toString()
                                currency = call.data.currency.toString()
                                amount = call.data.amount.toString()
                                initiateSuccess = true

                            }
                            302 -> {
                                CommonMethods.showLoginErrorPopUp(requireActivity(),"",call.message.toString())
                            }
                            401 -> {
                                progressBarDialog.dismiss()
//                                CommonMethods.callTokenRefreshAPI(requireActivity())
//                                callCourseCostAPI()
                            }
                        }

                    } catch (httpException: HttpException) {
                        progressBarDialog!!.dismiss()
                        val responseErrorBody = httpException.response()!!.errorBody()
                        val response = responseErrorBody!!.string()
                        val obj = JSONObject(response)
                        var status_code = obj.getString("status")
                        var message = obj.getString("message")
                        CommonMethods.showLoginErrorPopUp(
                            requireActivity(),
                            "Alert",
                            message
                        )

                    }
                }
                if (initiateSuccess){
                    val billingData = PaymentSdkBillingDetails(
                        "City",
                        "AE",
                        "",
                        "",
                        "", "Abu Dhabi",
                        "Abu Dhabi", "" // set values from preference
                    )
                    val configData =
                        PaymentSdkConfigBuilder(profileID, serverKey, clientKey,
                            (amount ?: 0.0) as Double, currency)
                            .setCartDescription(valueSelectCourse)
                            .setLanguageCode(locale)
                            .setBillingData(billingData)
                            .setMerchantCountryCode("AE") // ISO alpha 2
//            .setShippingData(shippingData)
                            .setCartId(orderID)
                            .setMerchantIcon(resources.getDrawable(R.drawable.playstore_icon))
                            .setTransactionType(PaymentSdkTransactionType.SALE)
                            .showBillingInfo(true)
                            .showShippingInfo(false)
                            .forceShippingInfo(false)
                            .setScreenTitle(screenTitle)
//            .setMerchantIcon(resources.getDrawable(R.drawable.playstore_icon))
                            .build()
                    PaymentSdkActivity.startCardPayment(
                        context as Activity,
                        configData,
                        callback = this@PaymentFragmentNew
                    )

                }

            } else {
                CommonMethods.showLoginErrorPopUp(requireActivity(), "", "Select at least 1 Course")
            }
        }


    }

    private fun getDeviceName(): String {

        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer).toString() + " " + model
        }


    }

    private fun capitalize(string: String?): String {
        if (string == null || string.length === 0) {
            return ""
        }
        val first: Char = string[0]
        return if (Character.isUpperCase(first)) {
            string
        } else {
            Character.toUpperCase(first) + string.substring(1)
        }
    }

    private fun showCourseSelectSheet() {
        val dialog = BottomSheetDialog(requireActivity(), R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_selct, null)
        val title = view.findViewById<TextView>(R.id.selectTitle)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        title.text = "Select Course"
        var selectorList: ArrayList<String> = ArrayList()
        for (each in AppController.courseList) {
            selectorList.add(each.text)
        }
        var selectAdapter = SelectorListAdapter(selectorList, requireActivity())
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = selectAdapter
        recycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                //call cost API
                selectedCourse.text = selectorList[position]
                valueSelectCourse = AppController.courseList[position].value
                callCourseCostAPI()
                dialog.dismiss()
            }
        })

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun callCourseCostAPI() {
        AppController.costList.clear()
        var total = 0.00
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()
        val paramObject = JsonObject().apply {
            addProperty("cource_code", valueSelectCourse)
        }
        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getCourseCost(
                    "Bearer " + PreferenceManager.getAccessToken(requireActivity()), paramObject
                )

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()
                        AppController.costList.addAll(call.data)

                        if (AppController.costList.isNotEmpty()) {
                            courseID = AppController.costList[0].orderId
                            recycler.adapter = CostAdapter(context as Activity)
                            recycler.layoutManager = LinearLayoutManager(context)

                            for (each in AppController.costList) {
                                total += each.elementCost.toFloat()
                            }

                            val roundOff = String.format("%.2f", total)
                            cost = total
                            textPrice.text = roundOff.toString()
                            textPrice2.text = roundOff.toString()
                            temp = 1
                        } else {
                            Toast.makeText(
                                context,
                                "Selected Course Unavailable",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                    401 -> {
                        progressBarDialog?.dismiss()
                        CommonMethods.callTokenRefreshAPI(requireActivity())
                        callCourseCostAPI()
                    }
                }

            } catch (httpException: HttpException) {
                progressBarDialog!!.dismiss()
                val responseErrorBody = httpException.response()!!.errorBody()
                val response = responseErrorBody!!.string()
                val obj = JSONObject(response)
                var status_code = obj.getString("status")
                var message = obj.getString("message")
                CommonMethods.showLoginErrorPopUp(
                    requireActivity(),
                    "Alert",
                    message
                )

            }
        }


        if (AppController.costList.isNotEmpty()) {

            recycler.adapter = CostAdapter(context as Activity)
            recycler.layoutManager = LinearLayoutManager(context)
            Log.e("cost", AppController.costList.toString())
            for (each in AppController.costList) {
                cost += each.elementCost.toDouble()
            }
            textPrice.text = cost.toString()
            textPrice2.text = cost.toString()


        }

    }

    private fun callCourseDetailsAPI() {
        AppController.courseList.clear()
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()
        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getCourseDetails(
                    "Bearer " + PreferenceManager.getAccessToken(
                        context as Activity
                    )
                )

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()
                        AppController.courseList.addAll(call.data)
                    }
                    401 -> {
                        CommonMethods.callTokenRefreshAPI(context as Activity)
                        callCourseDetailsAPI()
                    }
                }

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
        val paymentResponse = paymentSdkTransactionDetails.toString()
        val status = paymentSdkTransactionDetails.paymentResult!!.responseStatus
        val transactionRef = paymentSdkTransactionDetails.transactionReference
        val JSONSTRING = "{$paymentResponse }"
        val paramObject2 = JsonObject().apply {
            addProperty("order_reference", orderRef)
            addProperty("order_id", orderID)
            addProperty("status", status)
            addProperty("merchant_order_reference", transactionRef)
            addProperty("gateway_response", JSONSTRING)
        }
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.paymentSuccess(
                    "Bearer " + PreferenceManager.getAccessToken(
                        context as Activity
                    ), paramObject2
                )

                Log.e("Resp", call.toString())


                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()
                        val intent = Intent(activity, HomeBaseUserActivity::class.java)
                        startActivity(intent)
                    }

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}