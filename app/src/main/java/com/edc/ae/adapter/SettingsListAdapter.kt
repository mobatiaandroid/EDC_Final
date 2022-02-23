package com.edc.ae.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.mobatia.edcsurvey.survey.model.SurveyDataModel

class SettingsListAdapter (private var surveyArrayList: ArrayList<String>, private var mContext: Context) :

    RecyclerView.Adapter<SettingsListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var surveyNameTxt: TextView = view.findViewById(R.id.notifyTitle)
        var clickConstraint: ConstraintLayout = view.findViewById(R.id.clickConstraint)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_settings_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = surveyArrayList[position]
        holder.surveyNameTxt.text = surveyArrayList.get(position).toString()
//        holder.clickConstraint.setOnClickListener(View.OnClickListener {
//
//            if (position==0)
//            {
//                val intent = Intent()
//                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                val uri = Uri.fromParts("package", mContext.packageName, null)
//                intent.data = uri
//                mContext.startActivity(intent)
//            }
//            else if (position==2)
//            {
//
//            }
//        })

    }
    override fun getItemCount(): Int {

        return surveyArrayList.size

    }
}