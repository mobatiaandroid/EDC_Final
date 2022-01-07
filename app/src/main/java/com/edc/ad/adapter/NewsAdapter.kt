package com.edc.ad.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edc.ad.R
import com.edc.ad.activity.NewsDetailActivity
import com.edc.ad.activity.PDFViewActivity
import com.edc.ad.model.NewsResponseModel
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*
import kotlin.collections.ArrayList


class NewsAdapter(val arrayList: ArrayList<NewsResponseModel.Data>, val activity: Activity): RecyclerView.Adapter<NewsAdapter.VH>(){

    class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        var newsTitle: TextView? = null
        init {
            newsTitle = itemView.findViewById(R.id.newsTitle)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.VH {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return VH(itemView)
    }


    override fun onBindViewHolder(holder: NewsAdapter.VH, position: Int) {
        holder.newsTitle!!.text = arrayList[position].title
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("image",arrayList[position].file)
            intent.putExtra("title",arrayList[position].title)
            intent.putExtra("description",arrayList[position].description)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}
//class NewsAdapter(
//    val arrayList: ArrayList<NewsResponseModel.Data>,
//    val click: (NewsResponseModel.Data) -> Unit
//) : RecyclerView.Adapter<NewsAdapter.CNCTVH>() {
//
//    inner class CNCTVH(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CNCTVH =
//        CNCTVH(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
//
//    override fun onBindViewHolder(holder: CNCTVH, position: Int) {
//        holder.itemView.apply {
//            arrayList[position].let {
//
//                newsTitle.text = it.title
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