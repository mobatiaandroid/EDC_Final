package com.edc.ae.BaseActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edc.ae.R

class AccountBase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_base)
    }
}