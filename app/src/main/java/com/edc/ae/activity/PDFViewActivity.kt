package com.edc.ae.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.edc.ae.R

class PDFViewActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var url: String
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        val incomingIntent = intent
        position =
            incomingIntent.extras!!.getInt("position")
        url = incomingIntent.extras!!.getString("url").toString()
        Log.e("Url",url)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        finish()
    }
}