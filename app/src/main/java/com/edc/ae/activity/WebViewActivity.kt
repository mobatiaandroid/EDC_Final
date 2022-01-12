package com.edc.ae.activity

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.edc.ae.R
import android.webkit.WebViewClient
import android.widget.ImageView

import android.widget.Toast








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
        var progressDialog = ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(this@WebViewActivity, "Error:$description", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        webView.loadUrl(url)
        backButton.setOnClickListener {
            finish()
        }
    }
}