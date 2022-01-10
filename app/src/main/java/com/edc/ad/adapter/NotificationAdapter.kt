package com.edc.ad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.edc.ad.model.NotificationResponse
import kotlinx.android.synthetic.main.notifi_rec_lyt.view.*

class NotificationAdapter(val array: ArrayList<NotificationResponse.NotifiData>): RecyclerView.Adapter<NotificationAdapter.NTFVH>() {

    inner class NTFVH(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NTFVH =
        NTFVH(LayoutInflater.from(parent.context).inflate(R.layout.notifi_rec_lyt,parent,false))

    override fun onBindViewHolder(holder: NTFVH, position: Int) {
        holder.itemView.apply {
            array[position].let {
                notifyTitle.text = it.title

              //  notifyDesc.text = it.message
            }
        }
    }

    override fun getItemCount(): Int = array.size
}