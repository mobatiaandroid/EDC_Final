package com.edc.ad.activity

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.edc.ad.adapter.SurveyAnswerAdapter
import com.edc.ad.util.OnItemClickListener
import com.edc.ad.util.addOnItemClickListener
import com.mobatia.edcsurvey.survey.model.QuestionsModel

class SurveyDetailActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var backImg: ImageView
    lateinit var nextImg: ImageView
    lateinit var previousImg: ImageView
    lateinit var backBtn: ImageView
    lateinit var title: TextView
    lateinit var textView8: TextView
    lateinit var questionTxt: TextView
    lateinit var surveyname: String
    lateinit var surveyAnswerRecycler: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var questionsArrayList : ArrayList<QuestionsModel>
     var currentQuestionCount:Int=1
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
        progressBar=findViewById(R.id.progressBar)
        title=findViewById(R.id.title)
        nextImg=findViewById(R.id.nextImg)
        textView8=findViewById(R.id.textView8)
        questionTxt=findViewById(R.id.questionTxt)
        previousImg=findViewById(R.id.previousImg)
        backBtn=findViewById(R.id.backBtn)
        surveyAnswerRecycler=findViewById(R.id.surveyAnswerRecycler)
        title.text=surveyname
        progressBar.max=questionsArrayList.size
        progressBar.progressDrawable.setColorFilter(
            mContext.resources.getColor(R.color.edc_orange),
            PorterDuff.Mode.SRC_IN
        )
        backBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

        var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
        surveyAnswerRecycler.adapter=surveyQuestionAdapter
        var count=currentQuestionCount.toString()
        var quest=questionsArrayList.size.toString()
        textView8.text="Questions "+count+" / "+quest
        progressBar.setProgress(currentQuestionCount,true)
        questionTxt.setText(questionsArrayList.get(currentQuestionCount-1).question)
        surveyAnswerRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {



            }

        })

        nextImg.setOnClickListener(View.OnClickListener {

            if (currentQuestionCount!=questionsArrayList.size)
            {
                currentQuestionCount+=1
                var count=currentQuestionCount.toString()
                var quest=questionsArrayList.size.toString()
                textView8.text="Questions "+count+" / "+quest
                progressBar.setProgress(currentQuestionCount,true)
                questionTxt.setText(questionsArrayList.get(currentQuestionCount-1).question)
                var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
                surveyAnswerRecycler.adapter=surveyQuestionAdapter

            }
        })

        previousImg.setOnClickListener(View.OnClickListener {

            if (currentQuestionCount!=1)
            {
                previousImg.alpha=1.0f
                currentQuestionCount-=1
                var count=currentQuestionCount.toString()
                var quest=questionsArrayList.size.toString()
                textView8.text="Questions "+count+" / "+quest
                progressBar.setProgress(currentQuestionCount,true)
                questionTxt.setText(questionsArrayList.get(currentQuestionCount-1).question)
                var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
                surveyAnswerRecycler.adapter=surveyQuestionAdapter

            }
            else
            {
                previousImg.alpha=0.5f
            }
        })



    }




}