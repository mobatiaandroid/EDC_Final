package com.edc.ad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.mobatia.edcsurvey.survey.model.OptionsModel
import com.mobatia.edcsurvey.survey.model.SurveyDataModel

class SurveyAnswerAdapter (private var surveyArrayList: ArrayList<OptionsModel>, private var mContext: Context , private var answerType: Int) :

    RecyclerView.Adapter<SurveyAnswerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var surveyNameTxt: TextView = view.findViewById(R.id.notifyTitle)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_survey_answer, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = surveyArrayList[position]
        holder.surveyNameTxt.setText(surveyArrayList.get(position).label)

    }
    override fun getItemCount(): Int {

        return surveyArrayList.size

    }
}