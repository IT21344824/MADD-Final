package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.madd.Client_Agent.Client_Agent_All
import com.example.madd.Feed_Back.FeedBackaAdd
import com.example.madd.screens.ProfileCLientMainActivity

class ClientMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)

        val agentsButton = findViewById<Button>(R.id.Client_Agents)
        agentsButton.setOnClickListener {
            val intent = Intent(this, Client_Agent_All::class.java)
            startActivity(intent)
        }
        val feedBackButton = findViewById<Button>(R.id.button4)
        feedBackButton.setOnClickListener {
            val intent = Intent(this, FeedBackaAdd::class.java)
            startActivity(intent)
        }
        val profileButton= findViewById<Button>(R.id.button5)
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileCLientMainActivity::class.java)
            startActivity(intent)
        }
    }
}