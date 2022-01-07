package com.edc.ad.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.mobatia.edcsurvey.survey.model.QuestionsModel

class SurveyDetailActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var backImg: ImageView
    lateinit var title: TextView
    lateinit var surveyname: String
    lateinit var surveyListRecycler: RecyclerView
    lateinit var questionsArrayList : ArrayList<QuestionsModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_detail)
        mContext = this
        initUI()


    }

    fun initUI()
    {
        questionsArrayList = intent.getSerializableExtra("mylist") as ArrayList<QuestionsModel>
        surveyname = intent.getStringExtra("survey_name").toString()
        Log.e("Question ",questionsArrayList.size.toString())
        backImg=findViewById(R.id.backBtn)
        title=findViewById(R.id.title)
        title.setText(surveyname)
//        surveyListRecycler=findViewById(R.id.surveyListRecycler)
//        var linearLayoutManager = LinearLayoutManager(mContext)
//        surveyListRecycler.layoutManager = linearLayoutManager
//        var surveyQuestionAdapter= SurveyQuestionRecyclerAdapter(questionsArrayList,mContext)
//        surveyListRecycler.adapter=surveyQuestionAdapter

    }




}