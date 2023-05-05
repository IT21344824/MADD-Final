package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madd.screens.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        val intent = Intent(this, LoginActivity::class.java)

        // Start LoginActivity
        startActivity(intent)

        // Finish SplashActivity so it's removed from the back stack
        finish()
    }
}