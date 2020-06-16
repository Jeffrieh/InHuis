package com.example.inhuis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import kotlinx.coroutines.delay

class SplashScreenActivity : AppCompatActivity() {

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler.postDelayed({

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
        }, 1500) // aantal seconden splash screen 1,5
    }

}