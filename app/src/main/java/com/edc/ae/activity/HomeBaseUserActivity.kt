package com.edc.ae.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.edc.ae.R
import com.edc.ae.util.PreferenceManager
import kotlinx.android.synthetic.main.activity_user_base.*
import kotlinx.android.synthetic.main.activity_user_base.aboutBtn
import kotlinx.android.synthetic.main.activity_user_base.contactBtn
import kotlinx.android.synthetic.main.activity_user_base.homeBtn
import kotlinx.android.synthetic.main.activity_user_base.newsBtn
import kotlinx.android.synthetic.main.activity_user_base.newsLttrBtn
import kotlinx.android.synthetic.main.activity_user_base.notificationBtn
import kotlinx.android.synthetic.main.activity_user_base.servicesBtn

class HomeBaseUserActivity : AppCompatActivity() {

    lateinit var context: Activity
    private lateinit var drawerLayout: DrawerLayout
    private var navControl: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_base)
        context = this
        Log.e("status",PreferenceManager.getStudentStatus(context).toString())
        if (PreferenceManager.getStudentStatus(context) == "2") {
            paymentBtn.visibility = View.GONE
            paymentInstructionBtn.visibility = View.GONE
        } else if(PreferenceManager.getStudentStatus(context) == "3") {
            paymentBtn.visibility = View.VISIBLE
            paymentInstructionBtn.visibility = View.VISIBLE
        } else if(PreferenceManager.getStudentStatus(context) == "4") {
            paymentBtn.visibility = View.GONE
            paymentInstructionBtn.visibility = View.GONE
        }
        servicesBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.serviceFragment)
        }
        notificationBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.notificationFragment)
        }
        aboutBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.aboutFragment)
        }
        newsBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.newsFragment)
        }
        newsLttrBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.newsLetterFragment)
        }
        homeBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.homeFragment)
        }
        feedbackBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.feedbackFragment)
        }

        paymentInstructionBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.paymentInstructionFragment)
        }
        complaintBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.complaintFragment)
        }

        settingsBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.settingsFragment)
        }
        paymentBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.paymentFragmentNew)
        }

//        contactBtn.setOnClickListener { _ ->
//            if (drawerLayout.isOpen) drawerLayout.close()
//
//            navControl?.navigate(R.id.contactFragment)
//        }
        contactBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.contactFragment)
        }
        surveyBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.surveyFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        drawerLayout = findViewById(R.id.drawerLYT)
        navControl = findNavController(R.id.fragmentContainerView)
    }

    fun openNav() {
        println("DATA: SATA")
        drawerLayout = findViewById(R.id.drawerLYT)
        drawerLayout.openDrawer(Gravity.LEFT, true)
    }

    override fun onBackPressed() {
        if (drawerLayout != null && drawerLayout.isOpen) {
            drawerLayout.close()
        } else {
            if (navControl?.currentDestination!!.id != R.id.homeFragment) {
                navControl?.popBackStack()
                navControl?.navigate(R.id.homeFragment)

            } else super.onBackPressed()
        }
    }
}