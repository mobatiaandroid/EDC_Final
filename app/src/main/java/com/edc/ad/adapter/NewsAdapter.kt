package com.edc.ad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.edc.ad.model.NewsResponseModel
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*

class NewsAdapter(
    val arrayList: ArrayList<NewsResponseModel.Data>,
    val click: (NewsResponseModel.Data) -> Unit
) : RecyclerView.Adapter<NewsAdapter.CNCTVH>() {

    inner class CNCTVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CNCTVH =
        CNCTVH(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))

    override fun onBindViewHolder(holder: CNCTVH, position: Int) {
        holder.itemView.apply {
            arrayList[position].let {

                newsTitle.text = it.title

                setOnClickListener { _ ->
                    click.invoke(it)
                }
            }
        }
    }

    override fun getItemCount(): Int = arrayList.size
}