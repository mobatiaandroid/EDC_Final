package com.edc.ae.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.edc.ae.R
import com.edc.ae.util.MySingleton
import com.edc.ae.util.PreferenceManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class SplashActivity : AppCompatActivity() {
    lateinit var logo: ImageView
    lateinit var car: ImageView
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context = this
        logo = findViewById(R.id.imgLogo)
        car = findViewById(R.id.car)
        FirebaseApp.initializeApp(context)
        MySingleton.isFingerprintAuthenticated=false
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            val token = task.result
            PreferenceManager.setFCMToken(context as SplashActivity, token)
//            Log.e("Token",token.toString())
        })

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val calculatedWidth = metrics.widthPixels * 1.0f


        val animation: Animation = TranslateAnimation(0F, calculatedWidth / 3.8f, 0F, 0F)
        animation.duration = 1000
        animation.fillAfter = true
        /*val cityAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.car_right)*/
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logo.startAnimation(fadeInAnimation)
        val animUpDown: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.logo_animation
        )


        logo.startAnimation(animUpDown)
        car.startAnimation(animation)
        val backgroundExecutor: ScheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor()

        backgroundExecutor.schedule({


            if (this.let { PreferenceManager.getLoginStatus(it) } == "no") {
                val intent: Intent = Intent(this, HomeBaseGuestActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            } else {
                val intent: Intent = Intent(this, HomeBaseUserActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            }

        }, 2, java.util.concurrent.TimeUnit.SECONDS)

    }
}