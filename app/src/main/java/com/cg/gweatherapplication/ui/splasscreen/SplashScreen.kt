package com.cg.gweatherapplication.ui.splasscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.cg.gweatherapplication.view.MainActivity
import com.cg.gweatherapplication.R

class SplashScreen : AppCompatActivity() {
    private var TIME_OUT:Long = 2500
    lateinit var iv: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        iv= findViewById(R.id.logoIV)
        val anim= AnimationUtils.loadAnimation(this, R.anim.splash_screen)
        iv.startAnimation(anim)
        loadSplashScreen()
    }
    private fun loadSplashScreen(){
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },TIME_OUT)
    }

}