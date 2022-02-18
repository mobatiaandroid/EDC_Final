package com.edc.ae.activity

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.edc.ae.R
import kotlinx.android.synthetic.main.activity_guest_base.aboutBtn
import kotlinx.android.synthetic.main.activity_guest_base.contactBtn
import kotlinx.android.synthetic.main.activity_guest_base.homeBtn
import kotlinx.android.synthetic.main.activity_guest_base.newsBtn
import kotlinx.android.synthetic.main.activity_guest_base.newsLttrBtn
import kotlinx.android.synthetic.main.activity_guest_base.notificationBtn
import kotlinx.android.synthetic.main.activity_guest_base.servicesBtn

class HomeBaseGuestActivity : AppCompatActivity() {

    lateinit var context: Context
    private lateinit var drawerLayout: DrawerLayout
    var navControl: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_base)
        context = this
        notificationBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.notificationFragment)
        }
        servicesBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.serviceFragment)
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
        contactBtn.setOnClickListener { _ ->
            if (drawerLayout.isOpen) drawerLayout.close()

            navControl?.navigate(R.id.contactFragment)
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