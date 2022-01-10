package com.edc.ad.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.edc.ad.util.CommonMethods
import com.google.android.gms.common.internal.service.Common
import com.mobatia.edcsurvey.survey.model.OptionsModel
import com.mobatia.edcsurvey.survey.model.SurveyDataModel

class SurveyAnswerAdapter (private var surveyArrayList: ArrayList<OptionsModel>, private var mContext: Context , private var answerType: Int) :

    RecyclerView.Adapter<SurveyAnswerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var surveyNameTxt: TextView = view.findViewById(R.id.notifyTitle)
        var smileyTxt: TextView = view.findViewById(R.id.smileyTxt)
        var starTxt: TextView = view.findViewById(R.id.starTxt)
        var numberTxt: TextView = view.findViewById(R.id.numberTxt)
        var starImg: ImageView = view.findViewById(R.id.starImg)
        var textImg: ImageView = view.findViewById(R.id.textImg)
        var imageView18: ImageView = view.findViewById(R.id.imageView18)
        var smileyImg: ImageView = view.findViewById(R.id.smileyImg)
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
            if (surveyArrayList.get(position).select==1)
            {
                holder.textAnswerConst.setBackgroundResource(R.drawable.curved_rectangle_orange_survey)
                holder.textImg.setImageResource(R.drawable.check)
            }
            else{
                holder.textAnswerConst.setBackgroundResource(R.drawable.rectangle_rect_white_with_grey)
                holder.textImg.setImageResource(R.drawable.curved_rectangle_grey)
            }
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

            if (surveyArrayList.get(position).select==1)
            {
                if (surveyArrayList.get(position).label.equals("Happy"))
                {
                    holder.smileyImg.setImageResource(R.drawable.happy)
                }
                else if (surveyArrayList.get(position).label.equals("Sad"))
                {
                    holder.smileyImg.setImageResource(R.drawable.sad)
                } else if (surveyArrayList.get(position).label.equals("Neutral"))
                {
                    holder.smileyImg.setImageResource(R.drawable.neutral)

                } else if (surveyArrayList.get(position).label.equals("Excited"))
                {
                    holder.smileyImg.setImageResource(R.drawable.excited)
                }  else if (surveyArrayList.get(position).label.equals("Angry"))
                {
                    holder.smileyImg.setImageResource(R.drawable.angry)
                }
                holder.smileyConstraint.setBackgroundResource(R.drawable.curved_rectangle_orange_survey)
            }
            else{
                if (surveyArrayList.get(position).label.equals("Happy"))
                {
                    holder.smileyImg.setImageResource(R.drawable.happy_notselected)
                }
                else if (surveyArrayList.get(position).label.equals("Sad"))
                {
                    holder.smileyImg.setImageResource(R.drawable.sad_notselected)
                } else if (surveyArrayList.get(position).label.equals("Neutral"))
                {
                    holder.smileyImg.setImageResource(R.drawable.neutral_notselected)

                } else if (surveyArrayList.get(position).label.equals("Excited"))
                {
                    holder.smileyImg.setImageResource(R.drawable.excited_notselected)
                }  else if (surveyArrayList.get(position).label.equals("Angry"))
                {
                    holder.smileyImg.setImageResource(R.drawable.angry_notselected)
                }
                holder.smileyConstraint.setBackgroundResource(R.drawable.rectangle_rect_white_with_grey)
            }
        }
        else if (answerType==3)
        {
            Log.e("LABEL",surveyArrayList.get(position).label)
            holder.textAnswerConst.visibility=View.GONE
            holder.smileyConstraint.visibility=View.GONE
            holder.numberConstraint.visibility=View.GONE
            holder.starConstraint.visibility=View.VISIBLE
            if (surveyArrayList.get(position).selectedPos0==1)
            {
                holder.starImg.setImageResource(R.drawable.star_selected)
            }
            else
            {
                holder.starImg.setImageResource(R.drawable.star)
            }
            holder.starTxt.setText(surveyArrayList.get(position).label)
        }
        else if (answerType==4)
        {
            holder.textAnswerConst.visibility=View.GONE
            holder.smileyConstraint.visibility=View.GONE
            holder.numberConstraint.visibility=View.VISIBLE
            holder.starConstraint.visibility=View.GONE
            holder.numberTxt.setText(surveyArrayList.get(position).label)
            if (surveyArrayList.get(position).select==1)
            {
                holder.imageView18.setBackgroundResource(R.drawable.curved_rectangle_orange_survey)
            }
            else{
                holder.imageView18.setBackgroundResource(R.drawable.rectangle_rect_white_with_grey)
            }
        }


    }
    override fun getItemCount(): Int {

        return surveyArrayList.size

    }
}