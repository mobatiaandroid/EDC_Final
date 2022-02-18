package com.edc.ae.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.mobatia.edcsurvey.survey.model.SurveyDataModel

class SurveyListAdapter (private var surveyArrayList: ArrayList<SurveyDataModel>, private var mContext: Context) :

    RecyclerView.Adapter<SurveyListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var surveyNameTxt: TextView = view.findViewById(R.id.notifyTitle)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_survey_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = surveyArrayList[position]
        holder.surveyNameTxt.text = surveyArrayList.get(position).title

    }
    override fun getItemCount(): Int {

        return surveyArrayList.size

    }
}