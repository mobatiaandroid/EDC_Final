package com.edc.ad.BaseActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edc.ad.R

class AccountBase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_base)
    }
}