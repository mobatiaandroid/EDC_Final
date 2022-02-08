package com.edc.ae.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.edc.ae.R
import kotlinx.android.synthetic.main.activity_enroll.*

class EnrollActivity : AppCompatActivity() {
    lateinit var context: Context
    var currentTab = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enroll)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        constraintExistingStudent.setOnClickListener {
            if (currentTab != 2){
                constraintExistingStudent.setBackgroundResource(R.drawable.curved_rectangle)
                constraintNewStudent.setBackgroundResource(0)
                textNew.setTextColor(ContextCompat.getColor(context, R.color.black))
                textExisting.setTextColor(ContextCompat.getColor(context, R.color.white))
                constraintStudentNo.visibility = View.VISIBLE
                currentTab = 2
            }
        }
        constraintNewStudent.setOnClickListener {
            if (currentTab != 1){
                constraintNewStudent.setBackgroundResource(R.drawable.curved_rectangle)
                constraintExistingStudent.setBackgroundResource(0)
                textNew.setTextColor(ContextCompat.getColor(context, R.color.white))
                textExisting.setTextColor(ContextCompat.getColor(context, R.color.black))
                constraintStudentNo.visibility = View.GONE
                currentTab = 1
            }
        }
    }
}