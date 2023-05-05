package com.example.madd.Client_Agent

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.madd.R
import com.google.firebase.database.*

class Client_Agent_single : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var agentImgView: ImageView
    private lateinit var agentNameView: TextView
    private lateinit var agentEmailView: TextView
    private lateinit var agentPhoneView: TextView
    private lateinit var agentDescriptionView: TextView

    // buttons
    private lateinit var agentCallphone: Button
    private lateinit var agentMassage: Button
    private val CALL_PHONE_REQUEST_CODE = 1
    private val SEND_SMS_REQUEST_CODE = 2

    private var phone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_agent_single)

        agentImgView = findViewById(R.id.agent_Single_img)
        agentNameView = findViewById(R.id.agent_single_name)
        agentEmailView = findViewById(R.id.agent_single_email)
        agentPhoneView = findViewById(R.id.agent_single_phone)
        agentDescriptionView = findViewById(R.id.agents_single_Description)

        agentCallphone = findViewById(R.id.CallAgent)
        agentMassage = findViewById(R.id.MassageAgent)

        // get the extras from the intent
        val extras = intent.extras

        // check if the extras are not null
        if (extras != null) {
            val name = extras.getString("name")
            val email = extras.getString("email")
            phone = extras.getString("phone")
            val img = extras.getString("img")
            val description = extras.getString("description")

            // key of that id
            val key = extras.getString("key")

            // set the retrieved values to the corresponding views
            Glide.with(this)
                .load(img)
                .into(agentImgView)

            agentNameView.text = name
            agentEmailView.text = email
            agentPhoneView.text = phone
            agentDescriptionView.text = description
        }

        // set a click listener on the "CallAgent" button
        agentCallphone.setOnClickListener {
            // check if the app has the CALL_PHONE permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
                // if the permission is not granted, request it
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
                    CALL_PHONE_REQUEST_CODE)
            } else {
                // if the permission is already granted, make the phone call
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
                startActivity(intent)
            }
        }

        // set a click listener on the "CallAgent" button
        agentMassage.setOnClickListener {
            // check if the app has the SEND_SMS permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
                // if the permission is not granted, request it
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),
                    SEND_SMS_REQUEST_CODE)
            } else {
                // if the permission is already granted, send the SMS
                val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null))
                startActivity(intent)
            }
        }
    }

    // handle the result of the permission request
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if the permission is granted, make the phone call
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
                startActivity(intent)
            } else {
                // if the permission is not granted, show a message to the user
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == SEND_SMS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if the permission is granted, send the SMS
                val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null))
                startActivity(intent)
            } else {
                // if the permission is not granted, show a message to the user
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

