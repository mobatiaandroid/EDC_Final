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
    var studentName:String=""
    var studentNumber:String=""
    var trafficNumber:String=""
    var tryfileNumber:String=""
    var branchNumber:Int=-1
    var branchName:String=""
    var gender:String=""
    var email:String=""
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


        title.text="Student Profile"
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
                        if (call.data.userDetails.applicantNameEn.equals("")|| call.data.userDetails.applicantNameEn==null || call.data.userDetails.applicantNameEn.equals("null"))
                        {
                            studentName=""
                        }
                        else
                        {
                            studentName=call.data.userDetails.applicantNameEn
                            studentNameTxt.setText(studentName)
                        }

                        if (call.data.userDetails.studentNo.equals("")|| call.data.userDetails.studentNo==null || call.data.userDetails.studentNo.equals("null"))
                        {
                            studentNumber=""
                        }
                        else
                        {
                            studentNumber=call.data.userDetails.studentNo
                            studentNumberTxt.setText(studentNumber)
                        }

                        if (call.data.userDetails.trafficNumber.equals("")|| call.data.userDetails.trafficNumber==null || call.data.userDetails.trafficNumber.equals("null"))
                        {
                            trafficNumber=""
                        }
                        else
                        {
                            trafficNumber=call.data.userDetails.trafficNumber
                            studentTrafficNumberValueTxt.setText(trafficNumber)
                        }

                        if (call.data.userDetails.tryFileNumber.equals("")|| call.data.userDetails.tryFileNumber==null || call.data.userDetails.tryFileNumber.equals("null"))
                        {
                            tryfileNumber=""
                        }
                        else
                        {
                            tryfileNumber=call.data.userDetails.trafficNumber
                            studentTryFileNumberValueTxt.setText(tryfileNumber)
                        }

                        if (call.data.userDetails.branch==-1|| call.data.userDetails.branch==null )
                        {
                            branchNumber=-1
                        }
                        else
                        {
                            branchNumber=call.data.userDetails.branch
                            studentBranchNumberValueTxt.setText(branchNumber.toString())
                        }

                        if (call.data.userDetails.branchName.equals("")|| call.data.userDetails.branchName==null || call.data.userDetails.branchName.equals("null"))
                        {
                            branchName=""
                        }
                        else
                        {
                            branchName=call.data.userDetails.branchName
                            studentBranchNameValueTxt.setText(branchName)
                        }
                        if (call.data.userDetails.email.equals("")|| call.data.userDetails.email==null || call.data.userDetails.email.equals("null"))
                        {
                            email=""
                        }
                        else
                        {
                            email=call.data.userDetails.email
                            studentEmailValueTxt.setText(email)
                        }
                         if (call.data.userDetails.branchName.equals("")|| call.data.userDetails.branchName==null || call.data.userDetails.branchName.equals("null"))
                        {
                            gender=""
                        }
                        else
                        {
                            gender=call.data.userDetails.branchName
                           if (gender.equals("M"))
                           {

                           }
                            else
                           {

                           }
                        }





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
                CommonMethods.showLoginErrorPopUp(
                    mContext,
                    "Alert",
                    message
                )

            }
        }
    }

}