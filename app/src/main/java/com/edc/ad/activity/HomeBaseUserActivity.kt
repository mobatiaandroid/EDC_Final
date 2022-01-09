package com.edc.ad.BaseActivities

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.edc.ad.R
import kotlinx.android.synthetic.main.activity_guest_base.*
import kotlinx.android.synthetic.main.activity_user_base.*
import kotlinx.android.synthetic.main.activity_user_base.aboutBtn
import kotlinx.android.synthetic.main.activity_user_base.contactBtn
import kotlinx.android.synthetic.main.activity_user_base.homeBtn
import kotlinx.android.synthetic.main.activity_user_base.newsBtn
import kotlinx.android.synthetic.main.activity_user_base.newsLttrBtn
import kotlinx.android.synthetic.main.activity_user_base.notificationBtn
import kotlinx.android.synthetic.main.activity_user_base.servicesBtn

class HomeBaseUserActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private var navControl: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_base)
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
        complaintBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.complaintFragment)
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