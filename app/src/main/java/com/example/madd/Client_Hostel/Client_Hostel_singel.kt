package com.example.madd.Client_Hostel

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
import com.google.firebase.database.DatabaseReference

class Client_Hostel_singel : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference //lateinit used for non-null properties that have no initializer,
    // and they can only be used inside classes, not at the top level.
    private lateinit var HosImgView: ImageView
    private lateinit var HosNameView: TextView
    private lateinit var HosPhoneView: TextView
    private lateinit var HosDescriptionView: TextView

    // buttons


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_hostel_singel)

        HosImgView= findViewById(R.id.Hos_Single_img)
        HosNameView = findViewById(R.id.Hos_single_name)
        HosPhoneView = findViewById(R.id.Hos_single_phone)
        HosDescriptionView = findViewById(R.id.Hos_single_Description)



        // get the extras from the intent
        val extras = intent.extras

        // check if the extras are not null
        if (extras != null) {
            val name = extras.getString("name")
            val email = extras.getString("email")
            val phone = extras.getString("phone")
            val img = extras.getString("img")
            val description = extras.getString("description")

            // key of that id
            val key = extras.getString("key")

            // set the retrieved values to the corresponding views
            Glide.with(this)  //Glide is used to load and display the image of the client/hostel item.
                .load(img)
                .into(HosImgView)

            HosNameView.text = name
            HosPhoneView.text = phone
            HosDescriptionView.text = description
        }


    }

}