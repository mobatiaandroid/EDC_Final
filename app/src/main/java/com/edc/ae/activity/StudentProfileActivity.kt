package com.edc.ae.activity

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.edc.ae.adapter.SurveyAnswerAdapter
import com.edc.ae.adapter.SurveyListAdapter
import com.edc.ae.api.RetrofitClient
import com.edc.ae.model.SurveySubmitModel
import com.edc.ae.model.SurveySubmitQuestionModel
import com.edc.ae.util.*
import com.mobatia.edcsurvey.survey.model.QuestionsModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class StudentProfileActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var title: TextView
    lateinit var studentNameTxt: TextView
    lateinit var studentNumberTxt: TextView
    lateinit var studentTrafficNumberValueTxt: TextView
    lateinit var studentTryFileNumberValueTxt: TextView
    lateinit var studentBranchNumberValueTxt: TextView
    lateinit var studentBranchNameValueTxt: TextView
    lateinit var studentEmailValueTxt: TextView
    lateinit var genderImg: ImageView
    lateinit var backBtn: ImageView
    var studentName:String?=""
    var studentNumber:String?=""
    var trafficNumber:String?=""
    var tryfileNumber:String?=""
    var branchNumber:Int?=-1
    var branchName:String?=""
    var gender:String?=""
    var email:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)
        mContext = this
        initUI()

        callStudentProfileApi()

    }

    fun initUI()
    {
        title=findViewById(R.id.title)
        genderImg=findViewById(R.id.genderImg)
        studentNameTxt=findViewById(R.id.studentNameTxt)
        studentNumberTxt=findViewById(R.id.studentNumberTxt)
        studentTrafficNumberValueTxt=findViewById(R.id.studentTrafficNumberValueTxt)
        studentTryFileNumberValueTxt=findViewById(R.id.studentTryFileNumberValueTxt)
        studentBranchNumberValueTxt=findViewById(R.id.studentBranchNumberValueTxt)
        studentBranchNameValueTxt=findViewById(R.id.studentBranchNameValueTxt)
        studentEmailValueTxt=findViewById(R.id.studentEmailValueTxt)
        backBtn=findViewById(R.id.backBtn)


        title.text="Student Profile"
        backBtn.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun callStudentProfileApi() {
        var progressBarDialog: ProgressBarDialog? = null
        progressBarDialog = this.let { ProgressBarDialog(this) }
        progressBarDialog.show()
        //  dataArrayList= ArrayList()

        lifecycleScope.launch {
            try {

                val call = RetrofitClient.get.getStudentProfile(
                    "Bearer "+ PreferenceManager.getAccessToken(mContext as Activity)
                )

                when(call.status){
                    200,201 -> {
                        progressBarDialog.dismiss()
                        studentName= call.data!!.fullName
                        studentNameTxt.setText(studentName)
                        studentNumber= call.data.studentNo.toString()
                        studentNumberTxt.setText(studentNumber)
                        trafficNumber=call.data.trafficNo
                        studentTrafficNumberValueTxt.setText(trafficNumber)
                        tryfileNumber=call.data.tryFileNo
                        studentTryFileNumberValueTxt.setText(tryfileNumber)
                        branchNumber=call.data.branch
                        studentBranchNumberValueTxt.setText(branchNumber.toString())
                        email=call.data.emailAddress
                        studentEmailValueTxt.setText(email)
                        branchName=call.data.branchName
                        studentBranchNameValueTxt.setText(branchName)
                        gender=call.data.gender
                        if(gender.equals("M"))
                        {
                            genderImg.setImageResource(R.drawable.male)
                        }
                        else if (gender.equals("F"))
                        {
                            genderImg.setImageResource(R.drawable.female)
                        }
                        else{
                            genderImg.setImageResource(R.drawable.male)
                        }


                    }
                    401 -> {
                        progressBarDialog.dismiss()
                        CommonMethods.callTokenRefreshAPI(this@StudentProfileActivity)
                        callStudentProfileApi()
                    }
                    else -> {

                    }
                }

            }

            catch (httpException: HttpException) {
                progressBarDialog.dismiss()

                val responseErrorBody = httpException.response()!!.errorBody()
                val response = responseErrorBody!!.string()
                val obj = JSONObject(response)
                var status_code=obj.getString("status")
                var message = obj.getString("message")
                if(status_code.equals("401"))
                {
                    CommonMethods.callTokenRefreshAPI(this@StudentProfileActivity)
                    callStudentProfileApi()
                }
                CommonMethods.showLoginErrorPopUp(
                    mContext,
                    "Alert",
                    message
                )

            }
        }
    }

}