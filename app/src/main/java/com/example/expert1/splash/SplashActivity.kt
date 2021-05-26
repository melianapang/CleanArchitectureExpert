package com.example.expert1.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.expert1.R
import com.example.expert1.MainActivity
import com.example.expert1.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Animations
        binding.tvAppNameTitle.animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        binding.logo.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        binding.tvMotto.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        //to go to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}