package com.example.madd.Admin_Hostel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.madd.Admin_Agent.Admin_agent_update
import com.example.madd.R
import com.google.firebase.database.*

class Admin_Hostel_single : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var hosImgView: ImageView
    private lateinit var hosNameView: TextView
    private lateinit var hosPhoneView: TextView
    private lateinit var hosDescriptionView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_hostel_single)
        // get the extras from the intent
        val extras = intent.extras

        // check if the extras are not null
        if (extras != null) {
            // key of that id
            val key = extras.getString("key")

            // check if the key is not null before retrieving data from the database
            if (key != null) {
                // get the database reference for the specified key
                dbRef = FirebaseDatabase.getInstance().getReference("Hostels").child(key)

                // find the views in the layout file
                hosImgView = findViewById(R.id.Admin_Single_Hos_img)
                hosNameView = findViewById(R.id.Admin_Single_Hos_name)
                hosPhoneView = findViewById(R.id.Admin_Single_Hos_phone)
                hosDescriptionView = findViewById(R.id.Admin_Single_Hos_Description)

                // add a ValueEventListener to retrieve the data for the specified key
                dbRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // retrieve the data for the key
                        val name = snapshot.child("name").getValue(String::class.java)
                        val phone = snapshot.child("phone").getValue(String::class.java)
                        val img = snapshot.child("img").getValue(String::class.java)
                        val description = snapshot.child("description").getValue(String::class.java)

                        // set the retrieved values to the corresponding views
                        Glide.with(this@Admin_Hostel_single)
                            .load(img)
                            .into(hosImgView)

                        hosNameView.text = name
                        hosPhoneView.text = phone
                        hosDescriptionView.text = description
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // handle the error
                        Log.d("Admin_Hostel_single", "Failed to retrieve data: ${error.message}")
                    }
                })

                val hostelssButton = findViewById<Button>(R.id.Hos_Edit)
                hostelssButton.setOnClickListener {
                    val intent = Intent(this, Admin_hostel_update::class.java)
                    intent.putExtra("key", key) // pass the key to the next activity
                    startActivity(intent)
                }
            }
        }
    }
}