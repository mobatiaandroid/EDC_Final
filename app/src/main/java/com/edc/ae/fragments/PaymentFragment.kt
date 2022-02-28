package com.edc.ae.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.activity.HomeBaseUserActivity
import com.edc.ae.R
import com.edc.ae.activity.HomeBaseGuestActivity
import com.edc.ae.activity.NotificationDetailActivity
import com.edc.ae.adapter.CostAdapter
import com.edc.ae.adapter.CourseAdapter
import com.edc.ae.adapter.SelectorListAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.CourseCostModel
import com.edc.ae.model.Courses
import com.edc.ae.util.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.payment.paymentsdk.PaymentSdkActivity
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import kotlinx.android.synthetic.main.activity_enroll.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_payment.recycler
import kotlinx.android.synthetic.main.fragment_news_letter.navBtn
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import kotlin.math.roundToInt

class PaymentFragment : Fragment() , CallbackPaymentInterface {


    lateinit var selectCourse: ConstraintLayout
    lateinit var table: TableLayout
    lateinit var payButton: ConstraintLayout
    lateinit var textTotal: TextView
    lateinit var textPrice: TextView
    lateinit var textPrice2: TextView
    var temp = 0
    var valueSelectCourse = ""
    var cost = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectCourse = view.findViewById(R.id.constraintCourseSelect)
        table = view.findViewById(R.id.table)
        payButton = view.findViewById(R.id.paymentButton)
        textTotal = view.findViewById(R.id.textTotal)
        textPrice = view.findViewById(R.id.totalPrice)
        textPrice2 = view.findViewById(R.id.totalPrice2)

        callAPI()
//        if (AppController.costList.isEmpty()) {
//            table.visibility = View.GONE
//            payButton.visibility = View.GONE
//            textTotal.visibility = View.GONE
//            textPrice2.visibility = View.GONE
//            textPrice.visibility = View.GONE
//        }

        selectCourse.setOnClickListener {
            showCourseSelectSheet()

        }
        val profileId = "89872"
        val serverKey = "SMJN26ZRWB-JDBRTBJZBG-J9TGTNL99W"
        val clientKey = "CKKMQ7-G9266D-2R7269-669P72"
        val locale = PaymentSdkLanguageCode.EN
        val screenTitle = "EDC Course Payment"
        val cartId = "123456"
        val cartDesc = "cart description"
        val currency = "AED"
        val amount = 871.50

        val tokeniseType = PaymentSdkTokenise.NONE
        val transType = PaymentSdkTransactionType.SALE;
        val tokenFormat =  PaymentSdkTokenFormat.Hex32Format()
        val billingData = PaymentSdkBillingDetails(
            "City",
            "AE",
            "email1@domain.com",
            "",
            "", "AbhuDhabi",
            "Abu Dhabi", "" // set values from preference
        )
        val configData = PaymentSdkConfigBuilder(profileId, serverKey, clientKey, amount ?: 0.0, currency)
            .setCartDescription(cartDesc)
            .setLanguageCode(locale)
            .setBillingData(billingData)
            .setMerchantCountryCode("AE") // ISO alpha 2
//            .setShippingData(shippingData)
            .setCartId(cartId)
            .setMerchantIcon(resources.getDrawable(R.drawable.playstore_icon))
            .setTransactionType(transType)
            .showBillingInfo(true)
            .showShippingInfo(false)
            .forceShippingInfo(false)
            .setScreenTitle(screenTitle)
//            .setMerchantIcon(resources.getDrawable(R.drawable.playstore_icon))

            .build()

//        configData.setMerchantIcon(resources.getDrawable(R.drawable.playstore_icon))
        paymentButton.setOnClickListener {
            if (temp == 1){
                var progressBarDialog: ProgressBarDialog? = null
                progressBarDialog = activity?.let { ProgressBarDialog(it) }
                progressBarDialog?.show()
                val paramObject = JsonObject().apply {
                    addProperty("order_id", "5656")
                    addProperty("fee_code", "ELVC")
                    addProperty("cource_name", "test")
                    addProperty("amount", "1")
                    addProperty("device_type", "2")
                    addProperty("device_name", "Android10")
                    addProperty("app_version", "4.1")
                }
                progressBarDialog!!.hide()
                PaymentSdkActivity.startCardPayment(context as Activity,configData, callback = this)

                lifecycleScope.launch {
//                    try {
//                        val call = RetrofitClient.get.initiatePayment("Bearer " + PreferenceManager.getAccessToken(requireActivity()), paramObject)
//
//                        when (call.status) {
//                            200 -> {
//                                progressBarDialog?.dismiss()
//                                PaymentSdkActivity.startCardPayment(context as Activity,configData, callback = this)
//
//                            }
//                            401 -> {
//                                progressBarDialog?.dismiss()
////                                CommonMethods.callTokenRefreshAPI(requireActivity())
////                                callCourseCostAPI()
//                            }
//                        }
//
//                    } catch (httpException: HttpException) {
//                        progressBarDialog!!.dismiss()
//                        val responseErrorBody = httpException.response()!!.errorBody()
//                        val response = responseErrorBody!!.string()
//                        val obj = JSONObject(response)
//                        var status_code=obj.getString("status")
//                        var message = obj.getString("message")
//                        CommonMethods.showLoginErrorPopUp(
//                            requireActivity(),
//                            "Alert",
//                            message
//                        )
//
//                    }
                }
//                Toast.makeText(context, "Pressed", Toast.LENGTH_SHORT).show()

            } else {
                CommonMethods.showLoginErrorPopUp(requireActivity(), "","Select at least 1 Course")
            }
//            PaymentSdkActivity.startCardPayment(requireActivity(), configData, callback = requireActivity())

        }
        navBtn.setOnClickListener { _ ->
            if (activity?.let { PreferenceManager.getLoginStatus(it) } == "no") {
                (activity as HomeBaseGuestActivity).openNav()

            } else {
                (activity as HomeBaseUserActivity).openNav()

            }
        }

//        val orderList: ArrayList<Courses> = ArrayList()
//        val temp1: Courses = Courses("Course 1","200")
//        val temp2: Courses = Courses("Course 2","200")
//        val temp3: Courses = Courses("Course 3","200")
//        val temp4: Courses = Courses("Course 4","200")
//        val temp5: Courses = Courses("Course 5","200")
//        orderList.add(temp1)
//        orderList.add(temp2)
//        orderList.add(temp3)
//        orderList.add(temp4)
//        orderList.add(temp5)
//        recycler.adapter = CourseAdapter(requireActivity(),orderList,"5")
//        recycler.layoutManager = LinearLayoutManager(context)



    }

    private fun showCourseSelectSheet() {
        val dialog = BottomSheetDialog(requireActivity(),R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_selct, null)
        val title = view.findViewById<TextView>(R.id.selectTitle)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        title.text = "Select Course"
        var selectorList :ArrayList<String> =  ArrayList()
        for (each in AppController.courseList) {
            selectorList.add(each.text)
        }
//        for (each in AppController.educationLevelList) {
//            selectorList.add(each.name)
//        }
        var selectAdapter = SelectorListAdapter(selectorList,requireActivity())
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
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
                val call = RetrofitClient.get.getCourseCost("Bearer " + PreferenceManager.getAccessToken(requireActivity()), paramObject)

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()
                        AppController.costList.addAll(call.data)
                        if (AppController.costList.isNotEmpty()) {
                            recycler.adapter = CostAdapter(context as Activity)
                            recycler.layoutManager = LinearLayoutManager(context)

                            for (each in AppController.costList) {
                                total += each.elementCost.toFloat()
                            }
                            val roundoff = String.format("%.2f", total)
                            cost = total
                            textPrice.text = roundoff.toString()
                            textPrice2.text = roundoff.toString()
                            temp = 1
                        } else {
                            Toast.makeText(context, "Selected Course Unavailable", Toast.LENGTH_SHORT).show()
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
                var status_code=obj.getString("status")
                var message = obj.getString("message")
                CommonMethods.showLoginErrorPopUp(
                    requireActivity(),
                    "Alert",
                    message
                )

            }
        }
//        val temp = CourseCostModel.Data(380,"ELVC" ,"E Learning LV C1 Course",780)
//        AppController.costList.add(temp)
//        AppController.costList.add(temp)
//        AppController.costList.add(temp)
//        AppController.costList.add(temp)
//        recycler.adapter = CostAdapter(context as Activity)
//        recycler.layoutManager = LinearLayoutManager(context)

        if (AppController.costList.isNotEmpty()) {
//            table.visibility = View.VISIBLE
//            payButton.visibility = View.VISIBLE
//            textTotal.visibility = View.VISIBLE
//            textPrice2.visibility = View.VISIBLE
//            textPrice.visibility = View.VISIBLE
            recycler.adapter = CostAdapter(context as Activity)
            recycler.layoutManager = LinearLayoutManager(context)
            Log.e("cost",AppController.costList.toString())
            for (each in AppController.costList) {
                cost += each.elementCost.toDouble()
            }
            textPrice.text = cost.toString()
            textPrice2.text = cost.toString()

//
//            table.visibility = View.VISIBLE
//            payButton.visibility = View.VISIBLE
//            textTotal.visibility = View.VISIBLE
//            textPrice2.visibility = View.VISIBLE
//            textPrice.visibility = View.VISIBLE

        }



    }

    private fun callAPI() {
        AppController.courseList.clear()
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = activity?.let { ProgressBarDialog(it) }
        progressBarDialog?.show()

        lifecycleScope.launch {
            try {
                val call = RetrofitClient.get.getCourseDetails("Bearer "+ PreferenceManager.getAccessToken(
                    context as Activity
                ))

                when (call.status) {
                    200 -> {
                        progressBarDialog?.dismiss()
                        AppController.courseList.addAll(call.data)
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
        Toast.makeText(context, "Payment Success", Toast.LENGTH_SHORT).show()
        Log.e("Detail", paymentSdkTransactionDetails.toString())
        val intent = Intent(activity, HomeBaseUserActivity::class.java)
        startActivity(intent)

        //send result to sachu
    }
}