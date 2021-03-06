package com.edc.ae.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edc.ae.R
import com.edc.ae.model.ServiceResponseModel
import java.util.*
import com.edc.ae.activity.PDFViewActivity


class ServicesAdapter(
    val arrayList: ArrayList<ServiceResponseModel.Data>,
    val activity: Activity
): RecyclerView.Adapter<ServicesAdapter.VH>(){
    class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        var serviceTitle: TextView? = null
        init {
            serviceTitle = itemView.findViewById(R.id.serviceTitle)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_services, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.serviceTitle!!.text = arrayList[position].title
        holder.itemView.setOnClickListener {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(pdf)))
                val intent = Intent(activity, PDFViewActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("url",arrayList[position].file)
                activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}
//class ServicesAdapter(
//    val arrayList: ArrayList<ServiceResponseModel.Data>,
//    val click: (ServiceResponseModel.Data) -> Unit
//) : RecyclerView.Adapter<ServicesAdapter.CNCTVH>() {
//
//    inner class CNCTVH(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CNCTVH =
//        CNCTVH(LayoutInflater.from(parent.context).inflate(R.layout.item_services, parent, false))
//
//    override fun onBindViewHolder(holder: CNCTVH, position: Int) {
//        holder.itemView.apply {
//            arrayList[position].let {
//
//                serviceTitle.text = it.title
//
//                setOnClickListener { _ ->
//                    click.invoke(it)
//                }
//            }
//        }
//    }
//
//    override fun getItemCount(): Int = arrayList.size
//}