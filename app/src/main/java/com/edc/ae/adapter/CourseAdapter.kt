package com.edc.ae.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.edc.ae.model.Courses

class CourseAdapter(context: Context, orderList: ArrayList<Courses>, slNo: String): RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val no = itemView.findViewById<View>(R.id.no) as TextView
        val vcFeeDesc = itemView.findViewById<View>(R.id.name) as TextView
        val decAmount = itemView.findViewById<View>(R.id.cost) as TextView
    }
    val list = orderList
    val number = slNo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_courses, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.no.text = (position+1).toString()
        Log.e("Desc", holder.vcFeeDesc.text as String)
        holder.vcFeeDesc.text = list[position].vcFeeDesc
        holder.decAmount.text = list[position].decAmount.toFloat().toString()
//        if (position % 2 == 0){
//            holder.itemView.setBackgroundResource(android.R.color.holo_orange_light)
//        }else{
//            holder.itemView.setBackgroundResource(android.R.color.holo_blue_dark)
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}