package com.edc.ae.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.edc.ae.util.AppController

class CostAdapter(context: Context): RecyclerView.Adapter<CostAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val no = itemView.findViewById<View>(R.id.no) as TextView
        val vcFeeDesc = itemView.findViewById<View>(R.id.name) as TextView
        val decAmount = itemView.findViewById<View>(R.id.cost) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cost, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.no.text = (position+1).toString()
        Log.e("Desc", holder.vcFeeDesc.text as String)
        holder.vcFeeDesc.text = AppController.costList[position].elementDescription
        val amount =  String.format("%.2f", AppController.costList[position].elementCost)
        holder.decAmount.text = amount
    }

    override fun getItemCount(): Int {
        return AppController.costList.size
    }
}