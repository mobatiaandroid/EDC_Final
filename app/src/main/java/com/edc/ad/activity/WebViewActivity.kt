package com.edc.ad.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.edc.ad.R
import android.webkit.WebViewClient
import android.widget.ImageView


class WebViewActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var url: String
    lateinit var webView: WebView
    lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        val incomingIntent = intent
        url = incomingIntent.extras!!.getString("url").toString()
        Log.e("URL",url)
        webView = findViewById(R.id.webView)
        backButton = findViewById(R.id.backBtn)
        webView.getSettings().setJavaScriptEnabled(true);
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        backButton.setOnClickListener {
            finish()
        }
    }
}