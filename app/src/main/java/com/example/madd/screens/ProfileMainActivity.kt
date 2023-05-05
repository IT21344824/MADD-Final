package com.example.madd.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.madd.R

class ProfileMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_main)

        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val textView = findViewById<TextView>(R.id.textView)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val linearLayout3 = findViewById<LinearLayout>(R.id.linearLayout3)

        // Add click listeners for the buttons in the linearLayout3
        for (i in 0 until linearLayout3.childCount) {
            val child = linearLayout3.getChildAt(i)
            child.setOnClickListener {
                // TODO: Handle button click
            }
        }
    }
}