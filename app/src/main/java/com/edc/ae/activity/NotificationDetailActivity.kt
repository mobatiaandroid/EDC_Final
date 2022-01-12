package com.edc.ae.activity

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.edc.ae.R

class NotificationDetailActivity : AppCompatActivity() {
    lateinit var context: Context
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
        setContentView(R.layout.activity_notification_detail)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {

        val incomingIntent = intent
        description = incomingIntent.extras!!.getString("description").toString()
        title = incomingIntent.extras!!.getString("title").toString()
        textViewTitle = findViewById(R.id.title)
        backButton = findViewById(R.id.backBtn)
        imageView = findViewById(R.id.image)
        textViewDescription = findViewById(R.id.description)
        textViewTitle.text = title
        textViewDescription.text = description
        backButton.setOnClickListener { finish() }
    }
}