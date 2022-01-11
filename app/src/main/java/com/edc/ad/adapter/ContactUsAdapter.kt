package com.edc.ad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.edc.ad.model.ContactUsResponse
import kotlinx.android.synthetic.main.item_contactus.view.*
import java.util.*

class ContactUsAdapter(
    val arrayList: ArrayList<ContactUsResponse.ContactData>,
    val click: (ContactUsResponse.ContactData) -> Unit
) : RecyclerView.Adapter<ContactUsAdapter.CNCTVH>() {

    inner class CNCTVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CNCTVH =
        CNCTVH(LayoutInflater.from(parent.context).inflate(R.layout.item_contactus, parent, false))

    override fun onBindViewHolder(holder: CNCTVH, position: Int) {
        holder.itemView.apply {
            arrayList[position].let {

                contactUsTitle.text = it.branch_name

                setOnClickListener { _ ->
                    click.invoke(it)
                }
                if (position == 0) {
                    click.invoke(it)

                }

            }
        }
    }

    override fun getItemCount(): Int = arrayList.size
}
