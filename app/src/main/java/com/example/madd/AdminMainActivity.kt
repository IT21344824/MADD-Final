package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.madd.Admin_Agent.AdminAllAgents
import com.example.madd.Admin_Hostel.AdminAllHostels
import com.example.madd.Feed_Back.Feed_Back_All
import com.example.madd.screens.ProfileMainActivity


class AdminMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val agentsButton = findViewById<Button>(R.id.AdminAgents)
        agentsButton.setOnClickListener {
            val intent = Intent(this, AdminAllAgents::class.java)
            startActivity(intent)
        }

     /*   val feedBackButton = findViewById<Button>(R.id.button4)
        feedBackButton.setOnClickListener {
            val intent = Intent(this, Feed_Back_All::class.java)
            startActivity(intent)

        }*/
        val profileButton= findViewById<Button>(R.id.profileBtn)
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileMainActivity::class.java)
            startActivity(intent)
        }

        val HostalsButton = findViewById<Button>(R.id.Hostals)
        HostalsButton.setOnClickListener {
            val intent = Intent(this, AdminAllHostels::class.java)
            startActivity(intent)
        }



    }
}
