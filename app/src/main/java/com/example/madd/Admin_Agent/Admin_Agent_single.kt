package com.example.madd.Admin_Agent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.madd.R
import com.google.firebase.database.*


class Admin_Agent_single : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var agentImgView: ImageView
    private lateinit var agentNameView: TextView
    private lateinit var agentEmailView: TextView
    private lateinit var agentPhoneView: TextView
    private lateinit var agentDescriptionView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_agent_single)

//        val agentsButton = findViewById<Button>(R.id.AdminSingleToHome)
//        agentsButton.setOnClickListener {
//            val intent = Intent(this, AdminAllAgents::class.java)
//            startActivity(intent)
//        }

        // get the extras from the intent
        val extras = intent.extras

        // check if the extras are not null
        if (extras != null) {
            // key of that id
            val key = extras.getString("key")

            // check if the key is not null before retrieving data from the database
            if (key != null) {
                // get the database reference for the specified key
                dbRef = FirebaseDatabase.getInstance().getReference("Agents").child(key)

                // find the views in the layout file
                agentImgView = findViewById(R.id.Admin_Single_agents_img)
                agentNameView = findViewById(R.id.Admin_Single_agents_name)
                agentEmailView = findViewById(R.id.Admin_Single_agents_email)
                agentPhoneView = findViewById(R.id.Admin_Single_agents_phone)
                agentDescriptionView = findViewById(R.id.Admin_Single_agents_Description)

                // add a ValueEventListener to retrieve the data for the specified key
                dbRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // retrieve the data for the key
                        val name = snapshot.child("name").getValue(String::class.java)
                        val email = snapshot.child("email").getValue(String::class.java)
                        val phone = snapshot.child("phone").getValue(String::class.java)
                        val img = snapshot.child("img").getValue(String::class.java)
                        val description = snapshot.child("description").getValue(String::class.java)

                        // set the retrieved values to the corresponding views
                        Glide.with(this@Admin_Agent_single)
                            .load(img)
                            .into(agentImgView)

                        agentNameView.text = name
                        agentEmailView.text = email
                        agentPhoneView.text = phone
                        agentDescriptionView.text = description
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // handle the error
                        Log.d("Admin_Agent_single", "Failed to retrieve data: ${error.message}")
                    }
                })

                val agentsButton = findViewById<Button>(R.id.Agent_Edit)
                agentsButton.setOnClickListener {
                    val intent = Intent(this, Admin_agent_update::class.java)
                    intent.putExtra("key", key) // pass the key to the next activity
                    startActivity(intent)
                }
            }
        }
    }

}