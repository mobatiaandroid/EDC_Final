package com.edc.ad.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.mobatia.edcsurvey.survey.model.OptionsModel
import com.mobatia.edcsurvey.survey.model.SurveyDataModel

class SurveyAnswerAdapter (private var surveyArrayList: ArrayList<OptionsModel>, private var mContext: Context , private var answerType: Int) :

    RecyclerView.Adapter<SurveyAnswerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var surveyNameTxt: TextView = view.findViewById(R.id.notifyTitle)
        var smileyTxt: TextView = view.findViewById(R.id.smileyTxt)
        var starTxt: TextView = view.findViewById(R.id.starTxt)
        var numberTxt: TextView = view.findViewById(R.id.numberTxt)
        var textAnswerConst: ConstraintLayout = view.findViewById(R.id.textAnswerConst)
        var smileyConstraint: ConstraintLayout = view.findViewById(R.id.smileyConstraint)
        var starConstraint: ConstraintLayout = view.findViewById(R.id.starConstraint)
        var numberConstraint: ConstraintLayout = view.findViewById(R.id.numberConstraint)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_survey_answer, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = surveyArrayList[position]
        if (answerType==1)
        {
            holder.textAnswerConst.visibility=View.VISIBLE
            holder.smileyConstraint.visibility=View.GONE
            holder.starConstraint.visibility=View.GONE
            holder.numberConstraint.visibility=View.GONE
            holder.surveyNameTxt.setText(surveyArrayList.get(position).label)
        }
        else if (answerType==2)
        {
            holder.textAnswerConst.visibility=View.GONE
            holder.starConstraint.visibility=View.GONE
            holder.numberConstraint.visibility=View.GONE
            holder.smileyConstraint.visibility=View.VISIBLE
            holder.smileyTxt.setText(surveyArrayList.get(position).label)
        }
        else if (answerType==3)
        {
            Log.e("LABEL",surveyArrayList.get(position).label)
            holder.textAnswerConst.visibility=View.GONE
            holder.smileyConstraint.visibility=View.GONE
            holder.numberConstraint.visibility=View.GONE
            holder.starConstraint.visibility=View.VISIBLE
            holder.starTxt.setText(surveyArrayList.get(position).label)
        }
        else if (answerType==4)
        {
            holder.textAnswerConst.visibility=View.GONE
            holder.smileyConstraint.visibility=View.GONE
            holder.numberConstraint.visibility=View.VISIBLE
            holder.starConstraint.visibility=View.GONE
            holder.numberTxt.setText(surveyArrayList.get(position).label)
        }


    }
    override fun getItemCount(): Int {

        return surveyArrayList.size

    }
}