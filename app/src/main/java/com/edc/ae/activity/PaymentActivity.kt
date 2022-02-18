package com.edc.ae.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.edc.ae.R
import com.edc.ae.adapter.CourseAdapter
import com.edc.ae.model.Courses
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        val orderList: ArrayList<Courses> = ArrayList()
        val temp1: Courses = Courses("Course 1","200")
        val temp2: Courses = Courses("Course 2","200")
        val temp3: Courses = Courses("Course 3","200")
        val temp4: Courses = Courses("Course 4","200")
        val temp5: Courses = Courses("Course 5","200")
        orderList.add(temp1)
        orderList.add(temp2)
        orderList.add(temp3)
        orderList.add(temp4)
        orderList.add(temp5)
        recycler.adapter = CourseAdapter(context,orderList,"5")
        recycler.layoutManager = LinearLayoutManager(context)
    }
}