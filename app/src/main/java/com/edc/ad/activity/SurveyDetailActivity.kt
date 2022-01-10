package com.edc.ad.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.edc.ad.R
import com.edc.ad.adapter.SurveyAnswerAdapter
import com.edc.ad.api.RetrofitClient
import com.edc.ad.model.SurveySubmitModel
import com.edc.ad.model.SurveySubmitQuestionModel
import com.edc.ad.util.CommonMethods
import com.edc.ad.util.OnItemClickListener
import com.edc.ad.util.PreferenceManager
import com.edc.ad.util.addOnItemClickListener
import com.google.gson.JsonObject
import com.mobatia.edcsurvey.survey.model.QuestionsModel
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.coroutines.launch
import java.lang.Exception

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
    lateinit var survey_id: String
    lateinit var surveyAnswerRecycler: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var questionsArrayList : ArrayList<QuestionsModel>
    lateinit var suveyArrayList : ArrayList<SurveySubmitQuestionModel>
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
        survey_id = intent.getStringExtra("survey_id").toString()
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

        if (currentQuestionCount-1==0)
        {
            previousImg.alpha=0.5f
            nextImg.alpha=1.0f
        }
        else{
            previousImg.alpha=1.0f
            nextImg.alpha=1.0f
        }
        if (questionsArrayList.get(currentQuestionCount-1).answer_type==1)
        {
            val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
            surveyAnswerRecycler.layoutManager = layoutManager
        }
        else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==2)
        {
            Log.e("COUNT","COUNT!!")
            val layoutManager1 = GridLayoutManager(mContext,2)
            surveyAnswerRecycler.layoutManager = layoutManager1
        }
        else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==3)
        {
            val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
            surveyAnswerRecycler.layoutManager = layoutManager
        }
        else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==4)
        {
            val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
            surveyAnswerRecycler.layoutManager = layoutManager
        }
        var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
        surveyAnswerRecycler.adapter=surveyQuestionAdapter
        var count=currentQuestionCount.toString()
        var quest=questionsArrayList.size.toString()
        textView8.text="Questions "+count+" / "+quest
        progressBar.setProgress(currentQuestionCount,true)
        questionTxt.setText(questionsArrayList.get(currentQuestionCount-1).question)
        surveyAnswerRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                var found:Boolean=false
                var pos:Int=-1;
                for (i in 0..questionsArrayList.get(currentQuestionCount-1).options.size-1)
                {
                    if(questionsArrayList.get(currentQuestionCount-1).options.get(i).select==1)
                    {
                        found=true
                        pos=i
                    }
                }

                if (found)
                {
                    if (position==pos)
                    {
                        questionsArrayList.get(currentQuestionCount-1).options.get(position).select=0
                        questionsArrayList.get(currentQuestionCount-1).options.get(position).selectedPos=-1
                        CommonMethods.clickedPos=-1
                        var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
                        surveyAnswerRecycler.adapter=surveyQuestionAdapter
                    }
                    else
                    {
                        questionsArrayList.get(currentQuestionCount-1).options.get(position).select=1
                        questionsArrayList.get(currentQuestionCount-1).options.get(position).selectedPos=position
                        CommonMethods.clickedPos=position
                        questionsArrayList.get(currentQuestionCount-1).options.get(pos).select=0
                        var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
                        surveyAnswerRecycler.adapter=surveyQuestionAdapter
                    }
                }
                else
                {
                    questionsArrayList.get(currentQuestionCount-1).options.get(position).select=1
                    questionsArrayList.get(currentQuestionCount-1).options.get(position).selectedPos=position
                    CommonMethods.clickedPos=position
                    var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
                    surveyAnswerRecycler.adapter=surveyQuestionAdapter
                }

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
                if (questionsArrayList.get(currentQuestionCount-1).answer_type==1)
                {
                    val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
                    surveyAnswerRecycler.layoutManager = layoutManager
                }
                else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==2)
                {
                    Log.e("COUNT","COUNT!!")
                    val layoutManager1 = GridLayoutManager(mContext,2)
                    surveyAnswerRecycler.layoutManager = layoutManager1
                }
                else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==3)
                {
                    val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
                    surveyAnswerRecycler.layoutManager = layoutManager
                }
                else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==4)
                {
                    val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
                    surveyAnswerRecycler.layoutManager = layoutManager
                }
                var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
                surveyAnswerRecycler.adapter=surveyQuestionAdapter

            }
            else
            {

                suveyArrayList= ArrayList()
                for (i in 0..questionsArrayList.size-1)
                {
                    var optionvalue=""
                    for (j in 0..questionsArrayList.get(i).options.size-1)
                    {

                        if (questionsArrayList.get(i).options.get(j).select==1)
                        {
                            optionvalue=questionsArrayList.get(i).options.get(j).value.toString()
                        }

                    }

                    if (optionvalue.equals(""))
                   {

                   }
                    else{
                        Log.e("IT ENTERS",optionvalue)
                        var model=SurveySubmitQuestionModel(questionsArrayList.get(i).id,optionvalue)
                        suveyArrayList.add(model)

                   }

                }
                Log.e("Array",suveyArrayList.size.toString())
                Log.e("Survey ","IDDD")
                var mModel=SurveySubmitModel(PreferenceManager.getStudentID(mContext as Activity).toString(),survey_id,suveyArrayList)
                callSubmitApi(mModel)

            }
            if (currentQuestionCount-1==0)
            {
                previousImg.alpha=0.5f
            }
            else{
                previousImg.alpha=1.0f
            }
        })

        previousImg.setOnClickListener(View.OnClickListener {

            Log.e("CURRENT",currentQuestionCount.toString())
            if (currentQuestionCount!=1)
            {

                currentQuestionCount-=1
                var count=currentQuestionCount.toString()
                var quest=questionsArrayList.size.toString()
                textView8.text="Questions "+count+" / "+quest
                progressBar.setProgress(currentQuestionCount,true)
                questionTxt.setText(questionsArrayList.get(currentQuestionCount-1).question)
                if (questionsArrayList.get(currentQuestionCount-1).answer_type==1)
                {
                    val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
                    surveyAnswerRecycler.layoutManager = layoutManager
                }
                else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==2)
                {
                    Log.e("COUNT","COUNT!!")
                    val layoutManager1 = GridLayoutManager(mContext,2)
                    surveyAnswerRecycler.layoutManager = layoutManager1
                }
                else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==3)
                {
                    val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
                    surveyAnswerRecycler.layoutManager = layoutManager
                }
                else  if (questionsArrayList.get(currentQuestionCount-1).answer_type==4)
                {
                    val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
                    surveyAnswerRecycler.layoutManager = layoutManager
                }
                var surveyQuestionAdapter= SurveyAnswerAdapter(questionsArrayList.get(currentQuestionCount-1).options,mContext,questionsArrayList.get(currentQuestionCount-1).answer_type)
                surveyAnswerRecycler.adapter=surveyQuestionAdapter

            }

            if (currentQuestionCount-1==0)
            {
                previousImg.alpha=0.5f
            }
            else{
                previousImg.alpha=1.0f
            }

        })



    }


    private fun callSubmitApi( model:SurveySubmitModel) {



        lifecycleScope.launch {
            try {

                val call = RetrofitClient.get.submitSurvey(
                    "Bearer "+ PreferenceManager.getAccessToken(this@SurveyDetailActivity),
                    model
                )

                when (call.status){
                    200,201 -> {
                        Toast.makeText(this@SurveyDetailActivity, "Your Survey has been submitted successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else -> {
                        println("Error")
                    }
                }

            } catch (e: Exception){
                Toast.makeText(this@SurveyDetailActivity, "Something went wrong", Toast.LENGTH_SHORT).show()

                e.printStackTrace()
            }
        }
    }

}