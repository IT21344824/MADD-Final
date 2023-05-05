package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.madd.Admin_Agent.AdminAllAgents


class AdminMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val agentsButton = findViewById<Button>(R.id.AdminAgents)
        agentsButton.setOnClickListener {
            val intent = Intent(this, AdminAllAgents::class.java)
            startActivity(intent)
        }


    }
}
