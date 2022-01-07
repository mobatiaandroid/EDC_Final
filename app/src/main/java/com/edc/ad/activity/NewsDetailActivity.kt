package com.edc.ad.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.edc.ad.R
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var imageURL: String
    lateinit var description: String
    lateinit var title: String
    lateinit var textViewTitle: TextView
    lateinit var backButton: ImageView
//    lateinit var shareButton: ImageView
    lateinit var textViewDescription: TextView
    lateinit var imageView: ImageView
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
        imageURL = incomingIntent.extras!!.getString("image").toString()
        description = incomingIntent.extras!!.getString("description").toString()
        title = incomingIntent.extras!!.getString("title").toString()
        textViewTitle = findViewById(R.id.title)
        backButton = findViewById(R.id.backBtn)
//        shareButton = findViewById(R.id.shareBtn)
        imageView = findViewById(R.id.image)
        textViewDescription = findViewById(R.id.description)
        textViewTitle.text = title
        textViewDescription.text = description
        Glide
            .with(context)
            .load(imageURL)
            .centerCrop()
            .into(imageView);
        backButton.setOnClickListener { finish() }
//        shareButton.setOnClickListener {
//            val intent = Intent(Intent.ACTION_SEND)
//            startActivity(Intent.createChooser(intent, "Share Using"))
//        }
    }
}