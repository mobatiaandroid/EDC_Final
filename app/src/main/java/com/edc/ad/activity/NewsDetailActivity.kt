package com.edc.ad.activity

import android.content.ClipDescription
import android.content.Context
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.edc.ad.R

class NewsDetailActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var image: String
    lateinit var description: String
    lateinit var title: String
    lateinit var textViewTitle: TextView
    lateinit var backButton: ImageView
    lateinit var shareButton: ImageView
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {

        val incomingIntent = intent
        position = incomingIntent.extras!!.getInt("position")
        image = incomingIntent.extras!!.getString("image").toString()
        description = incomingIntent.extras!!.getString("description").toString()
        title = incomingIntent.extras!!.getString("title").toString()
        textViewTitle = findViewById(R.id.title)
        backButton = findViewById(R.id.backBtn)
        shareButton = findViewById(R.id.shareBtn)
        textViewTitle.text = title
    }
}